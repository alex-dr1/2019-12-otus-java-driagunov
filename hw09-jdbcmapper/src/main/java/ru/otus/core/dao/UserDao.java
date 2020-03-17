package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
  Optional<User> findById(long id);

  long saveUser(User user);

  SessionManager getSessionManager();
}
