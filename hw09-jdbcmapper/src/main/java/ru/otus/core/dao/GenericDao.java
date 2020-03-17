package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface GenericDao<T> {
  Optional<T> findById(long id);

  long saveUser(T object);

  SessionManager getSessionManager();
}
