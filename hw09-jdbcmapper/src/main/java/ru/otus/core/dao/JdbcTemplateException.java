package ru.otus.core.dao;

public class JdbcTemplateException extends RuntimeException {
  public JdbcTemplateException(Exception ex) {
    super(ex);
  }
}
