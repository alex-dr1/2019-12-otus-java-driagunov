package ru.otus.service;

import ru.otus.repository.model.User;

public interface UserFactory {

  User createUser(String pName, String pAddress, String[] pTelephones);
}
