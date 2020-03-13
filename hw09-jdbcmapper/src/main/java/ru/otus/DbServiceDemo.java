package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.h2.DataSourceH2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DbServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) throws Exception {
    DataSource dataSource = new DataSourceH2();
    DbServiceDemo demo = new DbServiceDemo();

    demo.createTable(dataSource,
            "create table user(id long(20) auto_increment, name varchar(255), age int(3))");

    demo.createTable(dataSource,
            "create table account(no long(20) auto_increment, type varchar(255), rest number)");

 /*   SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor<User> dbExecutor = new DbExecutor<>();
    UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);

    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
    long id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
    Optional<User> user = dbServiceUser.getUser(id);

    System.out.println(user);
    user.ifPresentOrElse(
        crUser -> logger.info("created user, name:{}", crUser.getName()),
        () -> logger.info("user was not created")
    );
*/
  }

  private void createTable(DataSource dataSource, String sqlCreate) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement(sqlCreate)) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }
}
