package ru.otus.repository.dbservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.repository.dao.UserDao;
import ru.otus.repository.model.Address;
import ru.otus.repository.model.Phone;
import ru.otus.repository.model.User;
import ru.otus.repository.hibernate.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

@Repository
public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
    dataBaseInitializer();
  }

  @Override
  public long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.saveUser(user);
        sessionManager.commitSession();
        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }


  @Override
  public Optional<User> getUser(long id) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);
        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

  @Override
  public List<User> getAllUser() {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        return userDao.getAllUsers();
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return List.of();
    }
  }

  private void dataBaseInitializer() {
    User vasia = createUser("Вася Пупкин", "ул. Ленина", "+72390423094", "+5324343433");
    User lusia = createUser("Люся Педалькина", "пр. Мира", "+79890238256", "+743382752930");

    this.saveUser(vasia);
    this.saveUser(lusia);
  }

  private User createUser(String name, String address, String phone1, String phone2) {
    User user = new User();
    user.setName(name);
    Address addressUser = new Address(address, user);
    Phone phone1User = new Phone(phone1, user);
    Phone phone2User = new Phone(phone2, user);
    user.setAddress(addressUser);
    user.setPhones(List.of(phone1User, phone2User));
    return user;
  }
}
