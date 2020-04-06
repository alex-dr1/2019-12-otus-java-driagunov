package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.dbservice.DBServiceUser;
import ru.otus.core.dbservice.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;
import java.util.Optional;

public class DbServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) {

    Class<?>[] annotatedClasses = {User.class, Address.class, Phone.class};
    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", annotatedClasses);

    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDao = new UserDaoHibernate(sessionManager);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);


    User vasia = createUser("Вася Пупкин", "ул. Ленина", "+72390423094", "+5324343433");
    User lusia = createUser("Люся Педалькина", "ул. Мира", "+79890238256", "+743382752930");

    long id1 = dbServiceUser.saveUser(vasia);
    Optional<User> mayBeCreatedVasia = dbServiceUser.getUser(id1);

    long id2 = dbServiceUser.saveUser(lusia);
    Optional<User> mayBeCreateLusia = dbServiceUser.getUser(id2);

    outputUserOptional("Created Vasia", mayBeCreatedVasia);
    outputUserOptional("Created Lusia", mayBeCreateLusia);
  }

  private static User createUser(String name, String address, String phone1, String phone2) {
    User user = new User();
    user.setName(name);
    Address addressUser = new Address(address, user);
    Phone phone1User = new Phone(phone1, user);
    Phone phone2User = new Phone(phone2, user);
    user.setAddress(addressUser);
    user.setPhones(List.of(phone1User, phone2User));
    return user;
  }

  private static void outputUserOptional(String header, Optional<User> mayBeUser) {
    System.out.println("-----------------------------------------------------------");
    System.out.println(header);
    mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
  }
}
