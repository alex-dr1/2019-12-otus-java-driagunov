package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.mapper.Mapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
  private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<Account> dbExecutor;
  private final Mapper<Account> mapper;

  public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor, Mapper<Account> mapper) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
    this.mapper = mapper;
  }

  @Override
  public Optional<Account> findById(long id) {
    String sql = mapper.createSQLSelect(Account.class);
    logger.info("SQL: {}", sql);
    try {
      return dbExecutor.selectRecord(getConnection(), sql, id, resultSet -> {
        try {
          if (resultSet.next()) {
            return mapper.createObject(resultSet, Account.class);
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
  public long saveAccount(Account account) {
    String sql = mapper.createSQLInsert(account);
    List<String> paramsList = mapper.getParamsList(account);
    List<String> paramsListForInsert = new ArrayList<>(paramsList.subList(1, paramsList.size()));

    logger.info("SQL: {}", sql);
    logger.info("params: {}", paramsListForInsert);

    try {
      return dbExecutor.insertRecord(getConnection(), sql, paramsListForInsert);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public void updateAccount(Account account) {
    String sql = mapper.createSQLUpdate(account);
    List<String> paramsList = mapper.getParamsList(account);
    List<String> paramsListForUpdate = new ArrayList<>(paramsList.subList(1, paramsList.size()));
    paramsListForUpdate.add(paramsList.get(0));

    logger.info("SQL: {}", sql);
    logger.info("params: {}", paramsListForUpdate);



    try {
      dbExecutor.updateRecord(getConnection(), sql , paramsListForUpdate);
    } catch (SQLException e) {
      e.printStackTrace();
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
