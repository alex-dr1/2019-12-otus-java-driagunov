package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceException;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserCacheImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserCacheImpl.class);

  private final UserDao userDao;
  private HwCache<Long, User> cache = new MyCache<>();
  private HwListener<Long, User> listener;

  public DbServiceUserCacheImpl(UserDao userDao) {
    this.userDao = userDao;

    listener = new HwListener<Long, User>() {
      @Override
      public void notify(Long key, User value, String action) {
        logger.info("{}==>>> key:{}, value:{}", action, key, value);
      }
    };

  }

  @Override
  public long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.saveUser(user);
        sessionManager.commitSession();

        cache.addListener(listener);
        cache.put(userId, user);
        cache.removeListener(listener);

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
    cache.addListener(listener);
    User userCache = cache.get(id);
    if (userCache == null){
      Optional<User> userDB = getUserDB(id);
      userDB.ifPresent(user -> cache.put(id, user));
      logger.info("getUserDB and putUserCache");
      cache.removeListener(listener);
      return userDB;
    }else{
      logger.info("getUserCache");
      cache.removeListener(listener);
      return Optional.of(userCache);
    }
  }

  private Optional<User> getUserDB(long id){
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);

        logger.info("getUser: {}", userOptional.orElse(null));
        return userOptional;
      } catch (DbServiceException e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }
}
