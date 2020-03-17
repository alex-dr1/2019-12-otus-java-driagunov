package ru.otus.core.dao;

import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao extends GenericDao<Account> {
  Optional<Account> findById(long id);

  long saveUser(Account object);

  SessionManager getSessionManager();
}
