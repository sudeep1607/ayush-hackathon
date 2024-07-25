package com.fts.hibernate.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
@SuppressWarnings("rawtypes")
public interface IDao
{

    void delete(Object entity) throws DataAccessException;

    
    Object load(Class type, Serializable classId) throws DataAccessException;

    Object get(Class type, Serializable classId);

    void saveOrUpdate(Object entity) throws DataAccessException;

    void merge(Object entity) throws DataAccessException;

    void refresh(Object entity) throws DataAccessException;

    void flush();

    void clear();
    void evict(Object entity);

    public Object getCount(String sql);

    public Object getMaxValue(String sql);
    
    public long getMaxId(String sql);

    public List findByNativeSql(String sql, Class entityClassName);

    public List findByNativeSql(String sql, String cloumnName);

    public List findByStartAndEndIndex(String sql, int startIndex, int endIndex) throws DataAccessException;

    public Object getColumnValue(String sql);

    public List findByNativeSqlByStartAndLimit(String sql, Class entityClassName, String start, String limit);

    public void deleteAll(Collection<? extends BaseEntity> entities) throws DataAccessException;

    public List find(String query, Object... values) throws DataAccessException;

    public List loadAll(Class type) throws DataAccessException;

    public void saveOrUpdateAll(Collection<? extends Object> entities) throws DataAccessException;

    public List findByFilter(String sql, String start, String limit);

    public void executeSqlQuery(String sql);
    
    public void openSessionAndExecuteQuery(String sql);

    public List findBySql(String sql);
    
    public int executeNativeQuery(String sql);
    public Object getCountByNativeSQL(String sql);

}
