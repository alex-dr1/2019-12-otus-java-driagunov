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

public class JdbcMapper<T> {
  private static Logger logger = LoggerFactory.getLogger(JdbcMapper.class);

  private DbExecutor<T> dbExecutor;
  private Connection connection;

  public JdbcMapper(DbExecutor<T> dbExecutor, Connection connection) {
    this.dbExecutor = dbExecutor;
    this.connection = connection;
  }

  public long create(T objectData){
    StringBuilder sql1 = new StringBuilder("insert into ");
    StringBuilder sql2 = new StringBuilder(") values (");
    String nameClass = objectData.getClass().getSimpleName().toLowerCase();
    sql1.append(nameClass);
    sql1.append("(");
    List<Field> fieldList = getFieldsObject(objectData);
    List<String> paramsList = new ArrayList<>();
    for (int i = 1; i < fieldList.size(); i++){
      fieldList.get(i).setAccessible(true);
      sql1.append(fieldList.get(i).getName());
      if(i < fieldList.size()-1){
        sql1.append(",");
      }
      try {
        sql2.append("?");
        if(i < fieldList.size()-1){
          sql2.append(",");
        }
        paramsList.add( String.valueOf( fieldList.get(i).get(objectData) ) );
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      fieldList.get(i).setAccessible(false);
    }
    sql1.append(sql2).append(")");

    try {
      return dbExecutor.insertRecord(connection, sql1.toString(), paramsList);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  public void update(T object){
    try {
      dbExecutor.updateRecord(connection, "UPDATE user SET name='UPDATEname', age=88 WHERE id = 1", new ArrayList<>());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public T load(long id, Class<T> tClass){
    String sql1 = selectBuilder(tClass);
    Optional<T> optionalT = Optional.empty();
    try {
      optionalT = dbExecutor.selectRecord(connection, sql1, id, resultSet -> {
        try {
          if (resultSet.next()) {
            return createObject(resultSet, tClass);//new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("age"));
          }
        } catch (SQLException e) {
          logger.error(e.getMessage(), e);
        }
        return null;
      });
    } catch (Exception e) {
      logger.error(e.getMessage(), e);

    }
    return optionalT.orElse(null);
  }

  private T createObject(ResultSet resultSet, Class<T> tClass) throws SQLException {
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
    List<Field> result = new ArrayList<>();
    return getFields(objectData.getClass());
  }

  private List<Field> getFields(Class<?> aClass){
    //TODO проверка на наличие поля id
    List<Field> result = new ArrayList<>();
    Field[] fields = aClass.getDeclaredFields();
    int i = 0;
    result.add(0,null);
    for (Field field: fields){
      field.setAccessible(true);
      if(field.isAnnotationPresent(Id.class)){
        result.remove(0);
        result.add(0, field);
      } else {
        i++;
        result.add(i, field);
      }
      field.setAccessible(false);
    }
    return result;
  }

  private String selectBuilder(Class<?> aClass) {
    StringBuilder sql1 = new StringBuilder("select ");
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
    sql1.append(" from ")
            .append(nameClass)
            .append(" where ")
            .append(fieldList.get(0).getName())
            .append(" = ?");
    return sql1.toString();
  }
}
