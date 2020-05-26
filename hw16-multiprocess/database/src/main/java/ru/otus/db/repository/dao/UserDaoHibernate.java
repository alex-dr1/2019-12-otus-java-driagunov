package ru.otus.db.repository.dao;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.db.repository.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.db.repository.hibernate.sessionmanager.SessionManager;
import ru.otus.db.repository.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.db.repository.model.User;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


public class UserDaoHibernate implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

  private final SessionManagerHibernate sessionManager;

  public UserDaoHibernate(SessionManagerHibernate sessionManager) {
    this.sessionManager = sessionManager;
  }


  @Override
  public Optional<User> findById(long id) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getHibernateSession();
      return Optional.ofNullable(hibernateSession.find(User.class, id));
    } catch (UserDaoException e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public long saveUser(User user) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getHibernateSession();
      if (user.getId() > 0) {
        logger.info(">>>MERGE>>> {}", user.toString());
        hibernateSession.merge(user);
      } else {
        logger.info(">>>PERSIST>>> {}", user.toString());
        hibernateSession.persist(user);
      }
      return user.getId();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public List<User> getAllUsers() {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getHibernateSession();
      CriteriaQuery<User> criteriaQuery = hibernateSession.getCriteriaBuilder().createQuery(User.class);
      Root<User> root = criteriaQuery.from(User.class);
      criteriaQuery.select(root);
      Query<User> query = hibernateSession.createQuery(criteriaQuery);
      return query.getResultList();
    } catch (UserDaoException e) {
      logger.error(e.getMessage(), e);
    }
    return List.of();
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}
