package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
