package ru.otus.db.repository.dao;


import ru.otus.db.repository.hibernate.sessionmanager.SessionManager;
import ru.otus.db.repository.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    long saveUser(User user);

    List<User> getAllUsers();

    SessionManager getSessionManager();
}