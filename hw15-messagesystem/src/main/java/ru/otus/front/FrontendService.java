package ru.otus.front;

import ru.otus.db.repository.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
    void getUserId(long userId, Consumer<User> dataConsumer);

    void getUserAll(Consumer<List<User>> listUserConsumer);

    void addUser(User user, Consumer<Long> userIdConsumer);

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

