/**
 * 
 */
package com.fts.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

 
@Component
public class HibernateSessionService
{
    @Autowired
    public SessionFactory sessionFactory;
    
    public Session session;

    public void openSession()
    {
        session = sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(this.sessionFactory, new SessionHolder(session));
    }

    /**
     * This method is used to release session
     */
    public void destroySession()
    {
        TransactionSynchronizationManager.unbindResource(this.sessionFactory);
        SessionFactoryUtils.closeSession(session);
    }

    
    public Session getSession()
    {
        return session;
    }

    public void setSession(Session session)
    {
        this.session = session;
    }

}
