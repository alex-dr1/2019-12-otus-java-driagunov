package ru.otus.db.service;

import ru.otus.db.repository.model.User;

public interface UserFactory {

  User createUser(String pName, String pAddress, String[] pTelephones);

  User createUser(User userForm);
}
