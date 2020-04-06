package ru.otus.core.dbservice;

import ru.otus.core.model.User;

import java.util.Map;
import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

  Map<Long, User> getAllUser();

}
