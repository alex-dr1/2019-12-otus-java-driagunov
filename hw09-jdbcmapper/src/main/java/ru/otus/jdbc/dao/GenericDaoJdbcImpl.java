package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.GenericDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

public class GenericDaoJdbcImpl<T> implements GenericDao<T> {
  private static Logger logger = LoggerFactory.getLogger(GenericDaoJdbcImpl.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<T> dbExecutor;

  public GenericDaoJdbcImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
  }

  @Override
  public Optional<T> findById(long id) {
    try {
      return dbExecutor.selectRecord(getConnection(), "select id, name, age from user where id  = ?", id, resultSet -> {
        try {
          if (resultSet.next()) {
            return (T) new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("age"));
          }
        } catch (SQLException e) {
          logger.error(e.getMessage(), e);
        }
        return null;
      });
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public long saveUser(T object) {
    try {
      return dbExecutor.insertRecord(getConnection(), "insert into user(name, age) values ('ALEX', 38)", null);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
