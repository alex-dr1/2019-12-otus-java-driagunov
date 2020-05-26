package ru.otus.msserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.Serializers;
import ru.otus.message.SocketMsg;
import ru.otus.message.SocketMsgType;
import ru.otus.messagesystem.Message;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageType;
import ru.otus.producerqueue.ProducerQueueSocketMsg;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MsServerImpl implements MsServer {
    private static final Logger logger = LoggerFactory.getLogger(MsServerImpl.class);

    private final int port;
    private final String name;
    private final String partnerName;
    private final MessageSystem messageSystem;
    private final AtomicReference<MapQueueSocketMsg> sharedQueueSocketMsg = new AtomicReference<>(new MapQueueSocketMsg());
    private final AtomicBoolean runFlag = new AtomicBoolean(true);
    private final Map<String, ProducerQueueSocketMsg> producerQueueMap = new HashMap<>();
    private final ExecutorService msgServer = Executors.newSingleThreadExecutor(Thread::new);
    private final Set<String> clients = new HashSet<>();
    private final ProducerNextClient producerNextClient = new ProducerNextClient();
    
    private MapQueueSocketMsg localQueueSocketMsg = new MapQueueSocketMsg();

    public MsServerImpl(String name, String partnerName, MessageSystem messageSystem, int port) {
        this.name = name;
        this.partnerName = partnerName;
        this.messageSystem = messageSystem;
        this.port = port;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean sendMessage(Message message) {
        logger.info("{} -> sendMessage: {}", name, message);
        boolean result = messageSystem.newMessage(message);
        if (!result) {
            logger.error("the last message was rejected: {}", message);
        }
        return result;
    }

    @Override
    public <T> Message produceMessage(SocketMsg socketMsg) {
        return new Message(name, partnerName, MessageType.SIMPLE_DATA.getValue(), Serializers.serialize(socketMsg));
    }

    @Override
    public void addProducerQueue(String socketMsgType, ProducerQueueSocketMsg producerQueueSocketMsg) {
        producerQueueMap.put(socketMsgType, producerQueueSocketMsg);
    }

    @Override
    public void removeProducerQueue(String socketMsgType) {
        producerQueueMap.remove(socketMsgType);
    }

    @Override
    public void addMapQueueSocketMsg(Message msg) {
        if (msg.getFrom() == null && msg.getTo() == null){
            sharedQueueSocketMsg.getAndUpdate(this::insertStopMsg);
        } else {
            sharedQueueSocketMsg.getAndUpdate((queueOutPost) -> {
                logger.info("{} -> addMapQueueSocketMsg({})", this.getName(), msg);
                SocketMsg socketMsg = Serializers.deserialize(msg.getPayload(), SocketMsg.class);
                String socketMsgType = socketMsg.getType();
                ProducerQueueSocketMsg producerQueueSocketMsg = getProducerQueueSocketMsg(socketMsgType);
                return producerQueueSocketMsg.addQueueOutPost(queueOutPost, socketMsg).orElse(queueOutPost);
            });
        }
    }

    private MapQueueSocketMsg insertStopMsg(MapQueueSocketMsg queueOutPost) {
        queueOutPost.add("__stop__", new SocketMsg(null, null, null, null, null));
        return queueOutPost;
    }

    private ProducerQueueSocketMsg getProducerQueueSocketMsg(String socketMsgType) {
        return producerQueueMap.get(socketMsgType);
    }


     @Override
    public void start() {
        msgServer.submit(this::processServer);
    }
    
    private MapQueueSocketMsg clearSharedQueue(MapQueueSocketMsg queueOutPost){
        queueOutPost.clear();
        return queueOutPost;
    }

    private void processServer(){
        
        while (runFlag.get() || !localQueueSocketMsg.isEmpty()) {
            if (localQueueSocketMsg.isEmpty()){
                localQueueSocketMsg = sharedQueueSocketMsg.getAndUpdate(this::clearSharedQueue);
            }

            checkMessageOnStop();
            checkMessageOnWhoFirst();
            checkMessageOnToAll();
            if(!localQueueSocketMsg.isEmpty())logger.info("{} After check socket message\n{}", name, localQueueSocketMsg.toString());
            waitClientAndHandle();
        }

        logger.info("Client {} finished", name);
        msgServer.shutdown();
    }

    private void checkMessageOnWhoFirst() {
        SocketMsg socketMsg = localQueueSocketMsg.getSocketMsg("__WhoFirst__");
        do {
            if(socketMsg != null) {
                String nextClient = producerNextClient.getNextClient();
                localQueueSocketMsg.add(nextClient, socketMsg);
            }
            socketMsg = localQueueSocketMsg.getSocketMsg("__WhoFirst__");
        }while (socketMsg != null);
    }

    private void waitClientAndHandle() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
//            logger.info("{} waiting for client connection on Port: {}", name, port);
            try (Socket clientSocket = serverSocket.accept()) {
                clientHandler(clientSocket);
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
//        logger.info("Connection closed");
    }

    private void clientHandler(Socket clientSocket) {
        try (DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
             DataInputStream dIn = new DataInputStream(clientSocket.getInputStream())
        ) {
            int length = dIn.readInt();
            if(length>0) {
                byte[] msgReceive = new byte[length];
                dIn.readFully(msgReceive, 0, msgReceive.length);
                SocketMsg socketMsgReceive = Serializers.deserialize(msgReceive, SocketMsg.class);
                String client = socketMsgReceive.getClientFrom();

                addClients(client);

                if(!socketMsgReceive.getType().equals(SocketMsgType.WATCH_DOG.getValue())){
                    // Send to MessageSystem
                    sendMessage(produceMessage(socketMsgReceive));
                }

                SocketMsg socketMsgSend = produceSocketMsg(client, socketMsgReceive.getClientFrom());

                byte[] msgSend = Serializers.serialize(socketMsgSend);
                dOut.writeInt(msgSend.length);
                dOut.write(msgSend);
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private void addClients(String client) {
        if (!clients.contains(client)){
            clients.add(client);
            producerNextClient.setClients(clients);
        }
    }

    private SocketMsg produceSocketMsg(String client, String to){
        SocketMsg result = localQueueSocketMsg.getSocketMsg(client);
        if (result == null){
            return new SocketMsg("MS_" + name,
                    to,
                    null,
                    SocketMsgType.WATCH_DOG.getValue(),
                    null);
        }
        logger.info("{} send to {} socket message: {}", name, client, result);
        return result;
    }

    private void checkMessageOnToAll() {
        SocketMsg socketMsg = localQueueSocketMsg.getSocketMsg("__toAll__");
        if(socketMsg != null ) {
            for (String client : clients) {
                localQueueSocketMsg.add(client, socketMsg);
            }
        }
    }

    private void checkMessageOnStop() {
        if(localQueueSocketMsg.getSocketMsg("__stop__") != null ){
            runFlag.set(false);
        }
    }

    class ProducerNextClient {
        private Iterator<String> iterator;
        private String retClient;

        public void setClients(Set<String> clients) {
            this.iterator = clients.iterator();
        }

        private void next() {
            if (iterator.hasNext())
            {
                retClient = iterator.next();
                if (!iterator.hasNext())
                    iterator = clients.iterator();
            }
        }

        public String getNextClient(){
            next();
            return retClient;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsServerImpl msClient = (MsServerImpl) o;
        return Objects.equals(name, msClient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
