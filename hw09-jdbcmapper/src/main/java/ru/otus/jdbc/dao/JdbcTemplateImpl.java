package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.JdbcTemplate;
import ru.otus.core.dao.JdbcTemplateException;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.util.List;

public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

  private static Logger logger = LoggerFactory.getLogger(JdbcTemplateImpl.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<T> dbExecutor;

  public JdbcTemplateImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
  }

  @Override
  public void create(T objectData) {
    try {
      dbExecutor.insertRecord(sessionManager.getCurrentSession().getConnection(), "insert into user (id, name, age) values (?,?,?)", List.of("12", "Alex", "38"));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new JdbcTemplateException(e);
    }
  }

  @Override
  public void update(T objectData) {

  }

  @Override
  public void createOrUpdate(T objectData) {

  }

  @Override
  public <T> T load(long id, Class<T> clazz) {
    return null;
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
