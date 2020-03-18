package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.Id;
import ru.otus.jdbc.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class JdbcMapper<T> {
  private static Logger logger = LoggerFactory.getLogger(JdbcMapper.class);

  private DbExecutor<T> dbExecutor;
  private Connection connection;

  public JdbcMapper(DbExecutor<T> dbExecutor, Connection connection) {
    this.dbExecutor = dbExecutor;
    this.connection = connection;
  }

  long create(T objectData){
    StringBuilder sql1 = new StringBuilder("insert into ");
    StringBuilder sql2 = new StringBuilder(") values (");
    String nameClass = objectData.getClass().getSimpleName().toLowerCase();
    sql1.append(nameClass);
    sql1.append("(");
    List<Field> fieldList = getFields(objectData);
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


  void update(T objectData){};

  //void createOrUpdate(T objectData); // опционально.


  <T> T load(long id, Class<T> clazz){
    return null;
  }

  private List<Field> getFields(T obejctData){
    //TODO проверка на наличие поля id
    List<Field> result = new ArrayList<>();
    Class<?> aClass = obejctData.getClass();
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

}
