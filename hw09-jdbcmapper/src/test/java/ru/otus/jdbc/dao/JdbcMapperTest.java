package ru.otus.jdbc.dao;

import org.junit.jupiter.api.Test;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcMapperTest {

  @Test
  void getFields() {
    User user1 = new User(0,"Alex", 38);
    Account account1 = new Account(0, "accountUser", new BigDecimal("653242"));
    JdbcMapper<User> jdbcMapperUser = new JdbcMapper(null, null);
    JdbcMapper<Account> jdbcMapperAccount = new JdbcMapper(null, null);

    jdbcMapperUser.create(user1);
    jdbcMapperAccount.create(account1);

  }
}