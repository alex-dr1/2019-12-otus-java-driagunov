package ru.otus.repository.dbservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.repository.dao.UserDao;
import ru.otus.repository.model.User;
import ru.otus.repository.hibernate.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

@Service
public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.saveUser(user);
        sessionManager.commitSession();
        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }


  @Override
  public Optional<User> getUser(long id) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);
        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

  @Override
  public List<User> getAllUser() {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        return userDao.getAllUsers();
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return List.of();
    }
  }
}
