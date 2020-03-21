package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceAccountImpl implements DBServiceAccount {
  private static Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

  private final AccountDao accountDao;

  public DbServiceAccountImpl(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public long saveAccount(Account account) {
    try (SessionManager sessionManager = accountDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = accountDao.saveAccount(account);
        sessionManager.commitSession();

        logger.info("created account: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public void updateAccount(Account account) {
    try (SessionManager sessionManager = accountDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        accountDao.updateAccount(account);
        sessionManager.commitSession();

        logger.info("update account: {}", account.toString());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<Account> getAccount(long id) {
    try (SessionManager sessionManager = accountDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<Account> accountOptional = accountDao.findById(id);

        logger.info("account: {}", accountOptional.orElse(null));
        return accountOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

}
