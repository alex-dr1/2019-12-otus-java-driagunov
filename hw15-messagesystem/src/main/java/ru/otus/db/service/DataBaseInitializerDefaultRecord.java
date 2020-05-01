package ru.otus.db.service;


import org.springframework.stereotype.Service;
import ru.otus.db.DBServiceUser;
import ru.otus.db.repository.model.User;
import ru.otus.db.service.UserFactory;


import javax.annotation.PostConstruct;


@Service()
public class DataBaseInitializerDefaultRecord {

  private final DBServiceUser dbServiceUser;
  private final UserFactory userFactory;

  public DataBaseInitializerDefaultRecord(DBServiceUser dbServiceUser, UserFactory userFactory) {
    this.dbServiceUser = dbServiceUser;
    this.userFactory = userFactory;
  }

  @PostConstruct
  void addInitRecord(){
    User vasia = userFactory.createUser("Вася Пупкин", "ул. Ленина", new String[]{"+72390423094", "+5324343433"});
    User lusia = userFactory.createUser("Люся Педалькина", "пр. Мира", new String[]{"+79890238256", "+743382752930"});

    dbServiceUser.saveUser(vasia);
    dbServiceUser.saveUser(lusia);
  }
}
