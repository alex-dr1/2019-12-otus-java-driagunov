package ru.otus.front.fontetdservice;

import ru.otus.requesthandlers.RequestHandler;
import ru.otus.db.repository.model.User;
import ru.otus.message.SocketMsgType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
    void getUserAll(Consumer<List<User>> listUserConsumer);

    void addUser(User user, Consumer<Long> userIdConsumer);

    void addHandler(SocketMsgType type, RequestHandler requestHandler);

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceSocketMsgId, Class<T> tClass);
}

