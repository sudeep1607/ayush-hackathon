package com.fts.hibernate.managers;

import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.RoleScreen;

@Repository
public class RoleScreenManager extends GenericManager<RoleScreen	, Long> {
	
	private static final Log LOG = LogFactory.getLog(RoleScreenManager.class);
	public RoleScreenManager( ) {
		super(RoleScreen.class);
	}
	
	 @SuppressWarnings("unchecked")
	    public List<RoleScreen> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
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
	            String sql = "from RoleScreen where 1=1 " + filterString + " order by " + sortColumn + " " + sortDirection;
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
	        String sql = "select count(*) from RoleScreen where 1=1 " + filterString;
	        return (Long) getCount(sql);
	    }

}
