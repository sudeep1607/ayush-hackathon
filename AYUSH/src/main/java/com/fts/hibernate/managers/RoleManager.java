package com.fts.hibernate.managers;

import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.Role;

@Repository
public class RoleManager extends GenericManager<Role, Long>
{

    private static final Log LOG = LogFactory.getLog(RoleManager.class);
    public RoleManager()
    {
        super(Role.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<Role> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
    {
        try
        {
            String sortColumn = "id";
            String sortDirection = "DESC";
            if (sortInfo.size() > 0)
            {
                sortColumn = sortInfo.get(0);
                sortDirection = sortInfo.get(1);
            }
            String sql = "from Role  where 1=1 " + filterString + " order by " + sortColumn + " " + sortDirection;
            return findByFilter(sql, start, limit);
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }
    
    public long getRecordsCount(String filterString, String... extraParams)
    {
        String sql = "select count(*) from Role  where 1=1 " + filterString;
        return (Long) getCount(sql);
    }
    public long getMaxId()
    {
        try
        {
        	 String sql = " select max(id) from Role";
        	 return  getMaxId(sql);
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return 0;
        }
    }
}
