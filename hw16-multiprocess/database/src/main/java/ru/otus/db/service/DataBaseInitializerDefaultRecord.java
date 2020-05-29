package ru.otus.db.service;

import ru.otus.db.DBServiceUser;
import ru.otus.db.repository.model.User;

public class DataBaseInitializerDefaultRecord {

  private final DBServiceUser dbServiceUser;
  private final UserFactory userFactory;

  public DataBaseInitializerDefaultRecord(DBServiceUser dbServiceUser, UserFactory userFactory) {
    this.dbServiceUser = dbServiceUser;
    this.userFactory = userFactory;
  }

  public void addInitRecord(){
    User vasia = userFactory.createUser("Вася Пупкин", "ул. Ленина", new String[]{"+72390423094", "+5324343433"});
    User lusia = userFactory.createUser("Люся Педалькина", "пр. Мира", new String[]{"+79890238256", "+743382752930"});

    dbServiceUser.saveUser(vasia);
    dbServiceUser.saveUser(lusia);
  }
}
