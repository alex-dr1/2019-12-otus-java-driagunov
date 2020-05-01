package ru.otus.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.db.repository.model.User;
import ru.otus.messagesystem.Message;
import ru.otus.messagesystem.MessageType;
import ru.otus.messagesystem.MsClient;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class FrontendServiceImpl implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void getUserId(long userId, Consumer<User> dataConsumer) {
        createAndSendMessage(dataConsumer, userId, MessageType.USER_ID);
    }

    @Override
    public void getUserAll(Consumer<List<User>> listUserConsumer) {
        createAndSendMessage(listUserConsumer, "Return all users", MessageType.USER_ALL);
    }

    @Override
    public void addUser(User user, Consumer<Long> userIdConsumer) {
        createAndSendMessage(userIdConsumer, user, MessageType.USER_ADD);
    }

    @Override
    public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
        Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
        if (consumer == null) {
            logger.warn("consumer not found for:{}", sourceMessageId);
            return Optional.empty();
        }
        return Optional.of(consumer);
    }

    private <V,T> void createAndSendMessage(Consumer<V> consumer, T data, MessageType messageType){
        Message outMsg = msClient.produceMessage(databaseServiceClientName, data, messageType);
        consumerMap.put(outMsg.getId(), consumer);
        msClient.sendMessage(outMsg);
    }
}
