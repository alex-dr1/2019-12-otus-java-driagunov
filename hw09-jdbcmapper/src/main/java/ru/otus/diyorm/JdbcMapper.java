package ru.otus.diyorm;

import ru.otus.core.dao.JdbcTemplate;
import ru.otus.core.model.User;
import ru.otus.core.service.DBService;
import ru.otus.core.service.DBServiceImpl;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.dao.JdbcTemplateImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class JdbcMapper<T> {
  private final DataSource dataSource;
  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<T> dbExecutor;

  public JdbcMapper(DataSource dataSource) {
    this.dataSource = dataSource;
    this.sessionManager = new SessionManagerJdbc(dataSource);
    this.dbExecutor = new DbExecutor<>();

  }

  public void create(T objectData){
    System.out.println(objectData);
    JdbcTemplate<T> jdbcTemplate = new JdbcTemplateImpl<>(sessionManager, dbExecutor);
    DBService<T> dbService = new DBServiceImpl<T>(jdbcTemplate);

    dbService.createObject(objectData);
  }

  public <T> T load(long id, Class<T> clazz) {
    try {
      Constructor<?>[] constructors = clazz.getConstructors();
      for (Constructor<?> constructor : constructors) {
        System.out.println(Arrays.toString(constructor.getParameterTypes()));
      }
      Class<?>[] params = {int.class, String.class};
      return (T) clazz.getConstructor(params).newInstance(2, "default2");
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }
}
