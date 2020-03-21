package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.Id;
import ru.otus.jdbc.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Mapper<T> {
  private static Logger logger = LoggerFactory.getLogger(Mapper.class);

  public List<String> getParamsList(T objectData){

    List<Field> fieldList = getFieldsObject(objectData);
    List<String> paramsList = new ArrayList<>();

    for (int i = 0; i < fieldList.size(); i++){
      fieldList.get(i).setAccessible(true);
      try {
        paramsList.add( String.valueOf( fieldList.get(i).get(objectData) ) );
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      fieldList.get(i).setAccessible(false);
    }
    return paramsList;
  }

  public String createSQLInsert(T objectData){
    StringBuilder sql1 = new StringBuilder("INSERT INTO ");
    StringBuilder sql2 = new StringBuilder(") VALUES (");
    String nameClass = objectData.getClass().getSimpleName().toLowerCase();
    sql1.append(nameClass);
    sql1.append("(");
    List<Field> fieldList = getFieldsObject(objectData);

    for (int i = 1; i < fieldList.size(); i++){
      fieldList.get(i).setAccessible(true);
      sql1.append(fieldList.get(i).getName());
      if(i < fieldList.size()-1){
        sql1.append(",");
      }
      sql2.append("?");
      if(i < fieldList.size()-1){
        sql2.append(",");
      }
      fieldList.get(i).setAccessible(false);
    }

    return sql1.append(sql2).append(")").toString();
  }

  public String createSQLSelect(Class<?> aClass) {
    StringBuilder sql1 = new StringBuilder("SELECT ");
    String nameClass = aClass.getSimpleName().toLowerCase();

    List<Field> fieldList = getFields(aClass);
    for (int i = 0; i < fieldList.size(); i++) {
      fieldList.get(i).setAccessible(true);
      sql1.append(fieldList.get(i).getName());
      if (i < fieldList.size() - 1) {
        sql1.append(",");
      }
      fieldList.get(i).setAccessible(false);
    }
    sql1.append(" FROM ")
            .append(nameClass)
            .append(" WHERE ")
            .append(fieldList.get(0).getName())
            .append(" = ?");
    return sql1.toString();
  }

  public String createSQLUpdate(T objectData) {
    StringBuilder sql1 = new StringBuilder("UPDATE ");

    String nameClass = objectData.getClass()
                                  .getSimpleName()
                                  .toLowerCase();

    sql1.append(nameClass);
    sql1.append(" SET ");
    List<Field> fieldList = getFieldsObject(objectData);

    for (int i = 1; i < fieldList.size(); i++){
      fieldList.get(i).setAccessible(true);
      sql1.append(fieldList.get(i).getName());
      sql1.append("=?");
      if(i < fieldList.size()-1){
        sql1.append(",");
      }
      fieldList.get(i).setAccessible(false);
    }

    sql1.append(" WHERE ")
        .append(fieldList.get(0).getName())
        .append("=?");

    return sql1.toString();
  }

  public T createObject(ResultSet resultSet, Class<T> tClass) throws SQLException {
    List<Field> fieldList = getFields(tClass);
    Class<?>[] params = new Class<?>[fieldList.size()];
    Object[] value = new Object[fieldList.size()];

    for (int i = 0; i < fieldList.size(); i++){
      params[i] = fieldList.get(i).getType();
      value[i] = resultSet.getObject(i+1);
    }

    try {
      return tClass.getConstructor(params).newInstance(value);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }

  private List<Field> getFieldsObject(T objectData){
    return getFields(objectData.getClass());
  }

  private List<Field> getFields(Class<?> aClass){
    List<Field> result = new ArrayList<>();
    Field[] fields = aClass.getDeclaredFields();
    boolean isSetId = false;
    int i = 0;
    result.add(0,null);
    for (Field field: fields){
      field.setAccessible(true);
      if(field.isAnnotationPresent(Id.class)){
        result.remove(0);
        result.add(0, field);
        isSetId = true;
      } else {
        i++;
        result.add(i, field);
      }
      field.setAccessible(false);
    }
    if (!isSetId){
      Exception e = new RuntimeException("No key field");
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
    return result;
  }
}
