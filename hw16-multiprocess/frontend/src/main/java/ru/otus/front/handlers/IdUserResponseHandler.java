package ru.otus.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.requesthandlers.RequestHandler;
import ru.otus.front.fontetdservice.FrontendService;
import ru.otus.Serializers;
import ru.otus.message.SocketMsg;

import java.util.Optional;
import java.util.UUID;

public class IdUserResponseHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(IdUserResponseHandler.class);

    private final FrontendService frontendService;

    public IdUserResponseHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Optional<SocketMsg> handle(SocketMsg msg) {
        try {
            long userId = Serializers.deserialize(msg.getPayload(), Long.class);
            UUID sourceMessageId = msg.getSourceSocketMsgId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
            frontendService.takeConsumer(sourceMessageId, Long.class).ifPresent(consumer -> consumer.accept(userId));
        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
        return Optional.empty();
    }
}
