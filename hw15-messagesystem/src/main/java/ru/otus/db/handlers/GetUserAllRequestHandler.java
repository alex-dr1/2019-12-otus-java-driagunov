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
import java.util.ArrayList;
import java.util.Optional;

public class GetUserAllRequestHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetUserAllRequestHandler.class);
    private final DBServiceUser dbServiceUser;

    public GetUserAllRequestHandler(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        String data = Serializers.deserialize(msg.getPayload(), String.class);
        logger.info("Request: {}", data);
        ArrayList<User> listAllUser = (ArrayList<User>) dbServiceUser.getAllUser();
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.USER_ALL.getValue(), Serializers.serialize(listAllUser)));
    }
}
