package ru.otus.db.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.db.DBServiceUser;
import ru.otus.db.repository.model.User;
import ru.otus.Serializers;
import ru.otus.message.SocketMsg;
import ru.otus.requesthandlers.RequestHandler;

import java.util.Optional;

public class AddUserRequestHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddUserRequestHandler.class);
    private final DBServiceUser dbServiceUser;

    public AddUserRequestHandler(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public Optional<SocketMsg> handle(SocketMsg socketMsg) {
        User user = Serializers.deserialize(socketMsg.getPayload(), User.class);
        logger.info("========= Request add User: {} ==========", user);
        long userId = dbServiceUser.saveUser(user);
        return Optional.empty();
    }
}
