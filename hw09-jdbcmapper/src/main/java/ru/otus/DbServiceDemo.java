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

  public void createTableUser(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table user(id long not null auto_increment, name varchar(255), age int(3))")) {
      pst.executeUpdate();
    }
  }

  public void createTableAccount(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table account(no long not null auto_increment, type varchar(255), rest number)")) {
      pst.executeUpdate();
    }
  }
}
