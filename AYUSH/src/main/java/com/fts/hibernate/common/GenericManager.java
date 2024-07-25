package com.fts.hibernate.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings(
{
        "unchecked", "rawtypes"
})
public class GenericManager<T, PK extends Serializable>
{
    @Autowired
    @Qualifier("hibernateDao")
    protected IDao hibernateDao;

    protected Class<T> entityClassName;

    public GenericManager(Class<T> type)
    {
        super();
        entityClassName = type;
    }

    public void setDao(IDao dao)
    {
        hibernateDao = dao;
    }

    public IDao getDao()
    {
        return hibernateDao;
    }

    @Transactional
    public void delete(T entity)
    {
        hibernateDao.delete(entity);
    }

    @Transactional
    public void deleteAll(Collection<? extends BaseEntity> entities) throws DataAccessException
    {
        hibernateDao.deleteAll(entities);
    }

    public T load(PK pkid)
    {
        T result = null;
        try
        {
            result = (T) hibernateDao.load(entityClassName, pkid);
        }
        catch (ObjectRetrievalFailureException orfe)
        {

        }
        // Result should never be null here due to semantics of the load()
        // method on the DAO.
        return result;
    }

    @Transactional
    public void saveOrUpdate(T entity)
    {
        hibernateDao.saveOrUpdate(entity);
    }

    @Transactional
    public void saveOrUpdateAll(Collection<? extends Object> entities)
    {
        hibernateDao.saveOrUpdateAll(entities);
    }

    @Transactional
    public void uncheckedsaveOrUpdate(Object entity)
    {
        hibernateDao.saveOrUpdate(entity);
    }

    public T get(PK pkid)
    {
        T result = null;
        try
        {
            result = (T) hibernateDao.get(entityClassName, pkid);
        }
        catch (ObjectRetrievalFailureException orfe)
        {

        }
        // Result should never be null here due to semantics of the load()
        // method on the DAO.
        return result;
    }

    public void flush()
    {
        hibernateDao.flush();
    }
    public void clear()
    {
        hibernateDao.clear();
    }
    public void evict(T entity)
    {
        hibernateDao.evict(entity);
    }
    @Transactional
    public void merge(T entity)
    {
        hibernateDao.merge(entity);
    }

    protected T first(List<T> entities)
    {
        return DAOUtils.first(entities);
    }

    protected T unique(List<T> entries, Object... params) throws DataAccessException
    {
        return DAOUtils.unique(entries, getClass(), params);
    }

    public Object getCount(String sql)
    {
        return hibernateDao.getCount(sql);
    }

    public Object getSum(String sql)
    {
        return hibernateDao.getCount(sql);
    }

    public Object getMaxValue(String sql)
    {
        return hibernateDao.getMaxValue(sql);
    }
    
    public long getMaxId(String sql)
    {
        return hibernateDao.getMaxId(sql);
    }

    public List findByNativeSql(String sql)
    {
        return hibernateDao.findByNativeSql(sql, entityClassName);
    }

    public List findByNativeSql(String sql, Class className)
    {
        return hibernateDao.findByNativeSql(sql, className);
    }

    public List findByNativeSql(String sql, String columnName)
    {
        return hibernateDao.findByNativeSql(sql, columnName);
    }

    public List findBySql(String sql)
    {
        return hibernateDao.findBySql(sql);
    }

    public List findByStartAndEndIndex(String sql, int startRow, int endRow) throws DataAccessException
    {
        return hibernateDao.findByStartAndEndIndex(sql, startRow, endRow);
    }

    public List findByNativeSqlByStartAndLimit(String sql, String start, String limit) throws DataAccessException
    {
        return hibernateDao.findByNativeSqlByStartAndLimit(sql, entityClassName, start, limit);
    }

    public Object getColumnValue(String sql)
    {
        return hibernateDao.getColumnValue(sql);
    }

    public List find(String queryString, Object... values) throws DataAccessException
    {
        return hibernateDao.find(queryString, values);
    }

    public List<T> loadAll()
    {
        List<T> result = new ArrayList<T>(new LinkedHashSet<T>(hibernateDao.loadAll(entityClassName)));
        return result;
    }

    public List findByFilter(String sql, String start, String limit)
    {
        return hibernateDao.findByFilter(sql, start, limit);
    }

    public void executeSqlQuery(String sql)
    {
        hibernateDao.executeSqlQuery(sql);
    }

    public void openSessionAndExecuteQuery(String sql)
    {
        hibernateDao.openSessionAndExecuteQuery(sql);
    }

    public int executeNativeQuery(String sql)
    {
        return hibernateDao.executeNativeQuery(sql);
    }
    public Object getCountByNativeSQL(String sql)
    {
        return hibernateDao.getCountByNativeSQL(sql);
    }

}
