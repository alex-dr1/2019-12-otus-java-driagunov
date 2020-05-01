package ru.otus.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.common.Serializers;
import ru.otus.front.FrontendService;
import ru.otus.messagesystem.Message;
import ru.otus.messagesystem.RequestHandler;
import java.util.Optional;
import java.util.UUID;

@Service(value = "addUserResponse")
public class AddUserResponseHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddUserResponseHandler.class);

    private final FrontendService frontendService;

    public AddUserResponseHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        try {
            long userId = Serializers.deserialize(msg.getPayload(), Long.class);
            UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
            frontendService.takeConsumer(sourceMessageId, Long.class).ifPresent(consumer -> consumer.accept(userId));
        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
        return Optional.empty();
    }
}
