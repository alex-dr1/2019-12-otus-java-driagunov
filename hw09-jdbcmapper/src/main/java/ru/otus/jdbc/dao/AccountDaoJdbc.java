package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
  private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<Account> dbExecutor;

  public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
  }


  @Override
  public Optional<Account> findById(long id) {
    JdbcMapper<Account> jdbcMapper = new JdbcMapper<>(dbExecutor, getConnection());
    return Optional.of(jdbcMapper.load(id, Account.class));
  }


  @Override
  public long saveAccount(Account account) {
    JdbcMapper<Account> jdbcMapper = new JdbcMapper<>(dbExecutor, getConnection());
    return jdbcMapper.create(account);
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
