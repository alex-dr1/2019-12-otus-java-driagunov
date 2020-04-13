package ru.otus.repository.dao;


import ru.otus.repository.model.User;
import ru.otus.repository.hibernate.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    long saveUser(User user);

    List<User> getAllUsers();

    SessionManager getSessionManager();
}