package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceAccount;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceAccountImpl;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) throws Exception {
    DataSource dataSource = new DataSourceH2();
    DbServiceDemo demo = new DbServiceDemo();

    demo.createTableUser(dataSource);
    demo.createTableAccount(dataSource);

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

    DbExecutor<User> dbExecutor = new DbExecutor<>();
    UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

    long id = dbServiceUser.saveUser(new User(0, "Alex",38));
    Optional<User> user = dbServiceUser.getUser(id);

     user.ifPresentOrElse(
        crUser -> logger.info("created object:{}", user.get()),
        () -> logger.info("object was not created")
    );

    DbExecutor<Account> dbExecutor2 = new DbExecutor<>();
    AccountDao accountDao = new AccountDaoJdbc(sessionManager, dbExecutor2);
    DBServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);

    long id2 = dbServiceAccount.saveAccount(new Account(0, "AccountAlex",new BigDecimal("4643643438")));
    Optional<Account> account = dbServiceAccount.getAccount(id2);

    account.ifPresentOrElse(
            crAccount -> logger.info("created object:{}", account.get()),
            () -> logger.info("object was not created")
    );

  }

  private void createTableUser(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table user(id long not null auto_increment, name varchar(255), age int(3))")) {
      pst.executeUpdate();
    }
    System.out.println("table user created ");
  }

  private void createTableAccount(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table account(no long not null auto_increment, type varchar(255), rest number)")) {
      pst.executeUpdate();
    }
    System.out.println("table account created");
  }
}
