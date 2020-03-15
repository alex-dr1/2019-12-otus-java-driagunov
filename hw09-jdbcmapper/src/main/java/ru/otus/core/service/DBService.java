package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.Optional;

public interface DBService<T> {
  void createObject(T objectData);

  Optional<T> getObject(long id);
}
