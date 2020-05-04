package ru.otus.db.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.common.Serializers;
import ru.otus.db.DBServiceUser;
import ru.otus.db.repository.model.User;
import ru.otus.messagesystem.Message;
import ru.otus.messagesystem.MessageType;
import ru.otus.messagesystem.RequestHandler;

import java.util.Optional;

public class AddUserRequestHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddUserRequestHandler.class);
    private final DBServiceUser dbServiceUser;

    public AddUserRequestHandler(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = Serializers.deserialize(msg.getPayload(), User.class);
        logger.info("Request add User: {}", user);
        long userId = dbServiceUser.saveUser(user);
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.USER_ADD.getValue(), Serializers.serialize(userId)));
    }
}
