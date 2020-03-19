package ru.otus.jdbc.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.DbServiceDemo;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbServiceUserTest {
  DataSource dataSource = new DataSourceH2();
  SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
  DbServiceDemo demo = new DbServiceDemo();

  DbExecutor<User> dbExecutor = new DbExecutor<>();
  UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
  DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

  @BeforeEach
  void setUp() throws SQLException {
    demo.createTableUser(dataSource);
  }

  @Test
  void testUser(){
    User user01 = new User(1, "Alex",38);
    long id0 = dbServiceUser.saveUser(user01);
    Optional<User> user02 = dbServiceUser.getUser(id0);
    assertEquals(user02.get(), user01);
    System.out.println(user02.get());

    User user21 = new User(1, "AlexUpdate",88);
    dbServiceUser.updateUser(user21);
    Optional<User> user22 = dbServiceUser.getUser(1);
    System.out.println(user22.get());
  }
}
