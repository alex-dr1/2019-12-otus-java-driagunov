package ru.otus.front.fontetdservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.requesthandlers.RequestHandler;
import ru.otus.db.repository.model.User;
import ru.otus.Serializers;
import ru.otus.message.SocketMsg;
import ru.otus.message.SocketMsgType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class FrontendServiceImpl implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);
    private static final int MESSAGE_QUEUE_SIZE = 128;

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    private final BlockingQueue<SocketMsg> socketMsgQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_SIZE);
    private final Map<String, RequestHandler> handlers = new HashMap<>();

    private final String name;
    private final String host;
    private final int port;

    private final ExecutorService msClientSocket = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("msClientSocket");
        return thread;
    });


    public FrontendServiceImpl(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
        start();
    }

    public void addHandler(SocketMsgType type, RequestHandler requestHandler) {
        this.handlers.put(type.getValue(), requestHandler);
    }

    private void start() {
        msClientSocket.submit(this::go);
    }

    private void go() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                try (Socket clientSocket = new Socket(host, port)) {
                    DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
                    DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());

                    sendSocketMsg(dOut);
                    receiveSocketMSg(dIn);
                }
            } catch (Exception ex) {
                System.out.print("-");
                pause(9L);
            }
        }
    }

    private void sendSocketMsg(DataOutputStream dOut) throws IOException {
        SocketMsg socketMsgOut = produceWatchDogSocketMsg();
        if(!socketMsgQueue.isEmpty()){
            socketMsgOut = socketMsgQueue.poll();
        }
//		logger.info("Send: {}", socketMsgOut);
        byte[] byteMsgOut = Serializers.serialize(socketMsgOut);
        dOut.writeInt(byteMsgOut.length);
        dOut.write(byteMsgOut);
    }

    private void receiveSocketMSg(DataInputStream dIn) throws IOException {
        int length = dIn.readInt();
        if(length>0) {
            byte[] byteMsgIn = new byte[length];
            dIn.readFully(byteMsgIn, 0, byteMsgIn.length);
            SocketMsg socketMsgIn = Serializers.deserialize(byteMsgIn, SocketMsg.class);

            RequestHandler requestHandler = handlers.get(socketMsgIn.getType());
            requestHandler.handle(socketMsgIn).ifPresent(socketMsgQueue::offer);
            System.out.print("+");
        }
    }

    private SocketMsg produceWatchDogSocketMsg() {
        return new SocketMsg(name, null, null, SocketMsgType.WATCH_DOG.getValue(), null);
    }

    @Override
    public void getUserAll(Consumer<List<User>> listUserConsumer) {
        createAndSendSocketMsg(listUserConsumer, "Return all users", SocketMsgType.GET_ALL_USER);
    }

    @Override
    public void addUser(User user, Consumer<Long> userIdConsumer) {
        createAndSendSocketMsg(userIdConsumer, user, SocketMsgType.NEW_USER);
    }

    @Override
    public <T> Optional<Consumer<T>> takeConsumer(UUID sourceSocketMsgId, Class<T> tClass) {
        Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceSocketMsgId);
        if (consumer == null) {
            logger.warn("consumer not found for:{}", sourceSocketMsgId);
            return Optional.empty();
        }
        return Optional.of(consumer);
    }

    private <V,T> void createAndSendSocketMsg(Consumer<V> consumer, T data, SocketMsgType socketMsgType){
        SocketMsg outSocketMsg = produceMessage(name, data, socketMsgType);
        if(consumer != null){
            consumerMap.put(outSocketMsg.getId(), consumer);
        }
        boolean result = socketMsgQueue.offer(outSocketMsg);
        while (!result){
            result = socketMsgQueue.offer(outSocketMsg);
        }
        logger.info("socketMsgQueue = {}", socketMsgQueue.size());
    }

    private <T> SocketMsg produceMessage(String databaseServiceClientName, T data, SocketMsgType socketMsgType) {
        byte[] sendData = Serializers.serialize(data);
        return new SocketMsg(name, null, null, socketMsgType.getValue(), sendData);
    }

    private void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
