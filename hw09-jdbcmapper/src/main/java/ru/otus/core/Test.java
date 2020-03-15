package ru.otus.core;

import ru.otus.core.model.User;
import ru.otus.jdbc.DbExecutor;

public class Test {
  Class<?> aClass = User.class;
  private final DbExecutor<?> dbExecutor;

  public Test() {
    this.dbExecutor = new DbExecutor<>();
    System.out.println(aClass.getTypeName());
  }

  // Показать тип T
  public void showType() {
    System.out.println("Тип T: " + dbExecutor.getClass());
  }
}
