package ru.otus.dao;

import ru.otus.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);
    Optional<User> findRandomUser();
    Optional<User> findByLogin(String login);

    long saveUser(User user);
    Map<Long, User> getAllUsers();
}