package ru.otus.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.requesthandlers.RequestHandler;
import ru.otus.db.repository.model.User;
import ru.otus.front.fontetdservice.FrontendService;
import ru.otus.Serializers;
import ru.otus.message.SocketMsg;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class AllUserResponseHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(AllUserResponseHandler.class);

    private final FrontendService frontendService;

    public AllUserResponseHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Optional<SocketMsg> handle(SocketMsg msg) {
        try {
            logger.info("===PayLOAD: {} ", msg.getPayload().length);
            ArrayList<User> listUserAll = Serializers.deserialize(msg.getPayload(), ArrayList.class);
            UUID sourceMessageId = msg.getSourceSocketMsgId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));

            frontendService.takeConsumer(sourceMessageId, ArrayList.class).ifPresent(consumer -> consumer.accept(listUserAll));

        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
        return Optional.empty();
    }
}
