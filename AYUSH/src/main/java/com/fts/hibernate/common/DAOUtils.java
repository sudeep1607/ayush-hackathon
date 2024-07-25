package com.fts.hibernate.common;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

@SuppressWarnings(
{
    "rawtypes"
})
public class DAOUtils
{

    /**
     * Convenience method to get the first result from a list.
     * 
     * @param entities The List from which to get the result.
     * @return The first object in the List, or null if the List is null or empty.
     */
    public static <T> T first(List<T> entities)
    {
        return entities == null || entities.isEmpty() ? null : entities.get(0);
    }

    /**
     * Convenience method to get a unique result from a list, using 'load' semantics.
     * 
     * @param entities The List from which to get the result.
     * @param clazz The class of the query.
     * @param param The query parameters to be logged if the object is not found or not unique.
     * @return The first object in the List, or null if the List is null or empty.
     * @throws DataAccessException if the object is not found or not unique.
     */

    public static <T> T unique(List<T> entries, Class clazz, Object... params) throws DataAccessException
    {
        T first = first(entries);
        //ObjectNotFoundException.throwIfNull(first, clazz, params);
        //NonUniqueResultException.throwIfNonUnique(entries, clazz, params);
        return first;
    }

    /**
     * Exception to be thrown when a unique result is expected, but more than one result is returned.
     * 
     * @see org.hibernate.NonUniqueResultException
     */
    public static class NonUniqueResultException extends DataAccessException
    {
        private static final long serialVersionUID = -1736097512018981207L;

        private NonUniqueResultException(String msg)
        {
            super(msg);
        }

        protected static void throwIfNonUnique(Collection collection, Class clazz, Object params[])
        {
            if (collection.size() > 1)
            {
                StringBuilder msg = new StringBuilder();
                msg.append("Non-unique result in query for ").append(clazz.getName()).append(" with ");
                appendArray(msg, params);
                msg.append(". ").append(collection.size()).append(" results returned.");
                throw new NonUniqueResultException(msg.toString());
            }
        }
    }

    /**
     * Exception to be thrown when a unique result is expected, but the object is not returned.
     * 
     * @see org.hibernate.ObjectNotFoundException
     */
    public static class ObjectNotFoundException extends DataAccessException
    {
        private static final long serialVersionUID = -7248454778331586070L;

        private ObjectNotFoundException(String msg)
        {
            super(msg);
        }

        public static void throwIfNull(Object obj, Class clazz, Object[] params)
        {
            if (obj == null)
            {
                StringBuilder msg = new StringBuilder();
                msg.append("Object not found in query for ").append(clazz.getName()).append(" with ");
                appendArray(msg, params);
                msg.append(".");
                //throw new ObjectNotFoundException(msg.toString());
            }
        }
    }

    /**
     * Appends the contents of an array to a StringBuilder.
     * 
     * @param buffer
     * @param array
     */
    private static void appendArray(StringBuilder buffer, Object[] array)
    {
        buffer.append('[');
        for (int i = 0; i < array.length; i++)
        {
            if (i > 0)
            {
                buffer.append(", ");
            }
            buffer.append(array[i]);
        }
        buffer.append(']');
    }

    public static String prepareInStatement(String sql, int length)
    {
        if (length > 0)
        {
            sql += "(";
            for (int i = 0; i < length; i++)
            {
                if (i > 0)
                {
                    sql += ",";
                }
                sql += "?";
            }
            sql += ")";
        }
        return sql;
    }

    public static String prepareInExeStatement(String sql, String[] arr)
    {
        if (arr.length > 0)
        {
            sql += "(";
            for (int i = 0; i < arr.length; i++)
            {
                if (i > 0)
                {
                    sql += ",";
                }
                sql += "'" + arr[i] + "'";
            }
            sql += ")";
        }
        return sql;
    }
}
