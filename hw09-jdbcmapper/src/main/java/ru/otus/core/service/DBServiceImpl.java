package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.JdbcTemplate;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DBServiceImpl<T> implements DBService<T> {
  private static Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

  private final JdbcTemplate jdbcTemplate;

  public DBServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void createObject(T objectData) {
    try (SessionManager sessionManager = jdbcTemplate.getSessionManager()) {
      sessionManager.beginSession();
      try {
        jdbcTemplate.create(objectData);
        sessionManager.commitSession();

        logger.info("created object");
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<T> getObject(long id) {
    return Optional.empty();
  }
}
