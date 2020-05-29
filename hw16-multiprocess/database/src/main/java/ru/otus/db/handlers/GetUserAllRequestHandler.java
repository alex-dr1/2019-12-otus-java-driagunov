package ru.otus.db.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.db.DBServiceUser;
import ru.otus.db.repository.model.User;
import ru.otus.Serializers;
import ru.otus.message.SocketMsg;
import ru.otus.message.SocketMsgType;
import ru.otus.requesthandlers.RequestHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class GetUserAllRequestHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetUserAllRequestHandler.class);
    private final DBServiceUser dbServiceUser;
    private final String nameClient;

    public GetUserAllRequestHandler(DBServiceUser dbServiceUser, String nameClient) {
        this.dbServiceUser = dbServiceUser;
        this.nameClient = nameClient;
    }

    @Override
    public Optional<SocketMsg> handle(SocketMsg socketMsg) {
        String data = Serializers.deserialize(socketMsg.getPayload(), String.class);
        logger.info("========= Request: {} ========", data);
        ArrayList<User> listAllUser = (ArrayList<User>) dbServiceUser.getAllUser();
        return Optional.of(new SocketMsg(nameClient,
                socketMsg.getClientFrom(),
                socketMsg.getId(),
                SocketMsgType.ALL_USER.getValue(),
                Serializers.serialize(listAllUser)));
    }
}
