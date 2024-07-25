package com.fts.hibernate.managers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.IDao;

/**
 * @author rabindranath.s
 */

@Repository
public class CommonManager 
{
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(CommonManager.class);

    @Autowired
    @Qualifier("hibernateDao")
    protected IDao hibernateDao;
    
    public Object getCount(String sql)
    {
        return hibernateDao.getCount(sql);
    }
    
    public boolean isFieldValueExits(Long id, String value, String field, String entityName)
    {
        String sql = "select count(*) from " + entityName + " where " + field + "='" + value + "'";
        if (id != null && id.longValue() > 0)
        {
            sql += " and id != " + id;
        }

        long count = (Long) getCount(sql);
        return (count > 0) ? true : false;
    }
    public boolean isFieldValueExitsbystore(Long id, String value, String field, String entityName,Long storeId)
    {
        String sql = "select count(*) from " + entityName + " where " + field + "='" + value + "' and store_id ="+storeId+"";
        if (id != null && id.longValue() > 0)
        {
            sql += " and id != " + id;
        }
        long count = (Long) getCount(sql);
        return (count > 0) ? true : false;
    }
    
    public boolean isFieldValueExitsTwice(Long sectionId, Long building, String sectionName, String field, String entityName)
    {
        String sql = "select count(*) from " + entityName + " where " + field + "='" + building + "' and sectionName = '"+sectionName+"' ";
        
         if (sectionId != null && sectionId.longValue() > 0  )
        {
           sql += " and id != " + sectionId;
        }

        long count = (Long) getCount(sql);
        return (count > 0) ? true : false;
    }

	public boolean isWFExists(Long storeId) {
		 String sql = "select count(*) from WorkflowConfig where workflowCode.id="+storeId;
        long count = (Long) getCount(sql);
        return (count > 0) ? true : false;
	}
    
    
}
