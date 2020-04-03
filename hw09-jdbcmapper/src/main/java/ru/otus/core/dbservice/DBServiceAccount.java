package ru.otus.core.dbservice;

import ru.otus.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

  long saveAccount(Account account);

  void updateAccount(Account account);

  Optional<Account> getAccount(long id);

}
