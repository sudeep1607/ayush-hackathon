package com.fts.hibernate.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.Organization;

@Repository
public class OrganizationManager  extends GenericManager<Organization	, Long> {

	private static final Log LOG = LogFactory.getLog(OrganizationManager.class);
  public OrganizationManager() {
		// TODO Auto-generated constructor stub
		super(Organization.class);
  }
	
	 @SuppressWarnings("unchecked")
	    public List<Organization> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
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
	            String sql = "from Organization where 1=1 "+prepareSearchQuery(extraParams)+ filterString + " order by " + sortColumn + " " + sortDirection;
	            return findByFilter(sql, start, limit);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
	    }
	 public String prepareSearchQuery(String... params)
	    {
	    	    	return (params != null && params[0] != null && params[0] != "") ? " and (organizationName like '%" + params[0] + "%') " : "";
	    }

	    
	    public long getRecordsCount(String filterString, String... extraParams)
	    {
	        String sql = "select count(*) from  Organization  where 1=1 " + filterString;
	        return (Long) getCount(sql);
	    }
	    
	    @SuppressWarnings("unchecked")
	    public List<Organization> getAllActiveOrganizations()
	    {
	        try
	        {
	            return find("from Organization where active = 1 order by id desc");
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(), e);
	            return new ArrayList<Organization>();
	        }
	    }

}

