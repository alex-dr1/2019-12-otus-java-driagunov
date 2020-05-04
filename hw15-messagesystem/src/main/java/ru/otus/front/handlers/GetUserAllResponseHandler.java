package ru.otus.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.common.Serializers;

import ru.otus.db.repository.model.User;
import ru.otus.front.FrontendService;
import ru.otus.messagesystem.Message;
import ru.otus.messagesystem.RequestHandler;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class GetUserAllResponseHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetUserAllResponseHandler.class);

    private final FrontendService frontendService;

    public GetUserAllResponseHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        try {
            ArrayList<User> listUserAll = Serializers.deserialize(msg.getPayload(), ArrayList.class);
            UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));

            frontendService.takeConsumer(sourceMessageId, ArrayList.class).ifPresent(consumer -> consumer.accept(listUserAll));

        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
        return Optional.empty();
    }
}
