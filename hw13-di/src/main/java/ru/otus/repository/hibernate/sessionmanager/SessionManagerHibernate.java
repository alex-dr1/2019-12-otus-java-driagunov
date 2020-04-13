package ru.otus.repository.hibernate.sessionmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.repository.hibernate.HibernateUtils;
import ru.otus.repository.model.Address;
import ru.otus.repository.model.Phone;
import ru.otus.repository.model.User;

@Service
public class SessionManagerHibernate implements SessionManager {

  private DatabaseSessionHibernate databaseSession;
  private final SessionFactory sessionFactory;

  public SessionManagerHibernate(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void beginSession() {
    try {
      databaseSession = new DatabaseSessionHibernate(sessionFactory.openSession());
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public void commitSession() {
    checkSessionAndTransaction();
    try {
      databaseSession.getTransaction().commit();
      databaseSession.getHibernateSession().close();
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public void rollbackSession() {
    checkSessionAndTransaction();
    try {
      databaseSession.getTransaction().rollback();
      databaseSession.getHibernateSession().close();
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public void close() {
    if (databaseSession == null) {
      return;
    }
    Session session = databaseSession.getHibernateSession();
    if (session == null || !session.isConnected()) {
      return;
    }

    Transaction transaction = databaseSession.getTransaction();
    if (transaction == null || !transaction.isActive()) {
      return;
    }

    try {
      databaseSession.close();
      databaseSession = null;
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public DatabaseSessionHibernate getCurrentSession() {
    checkSessionAndTransaction();
    return databaseSession;
  }

  private void checkSessionAndTransaction() {
    if (databaseSession == null) {
      throw new SessionManagerException("DatabaseSession not opened ");
    }
    Session session = databaseSession.getHibernateSession();
    if (session == null || !session.isConnected()) {
      throw new SessionManagerException("Session not opened ");
    }

    Transaction transaction = databaseSession.getTransaction();
    if (transaction == null || !transaction.isActive()) {
      throw new SessionManagerException("Transaction not opened ");
    }
  }
}
