package com.fts.hibernate.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.DesignationInfo;
@Repository
public class DesignationInfoManager extends GenericManager<DesignationInfo	, Long> {

	private static final Log LOG = LogFactory.getLog(DesignationInfoManager.class);
	public DesignationInfoManager( ) {
		super(DesignationInfo.class);
	}

	 @SuppressWarnings("unchecked")
	    public List<DesignationInfo> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
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
	            String sql = "from DesignationInfo  where 1=1"+prepareSearchQuery(extraParams) + filterString + " order by " + sortColumn + " " + sortDirection;
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
	    	    	return (params != null && params[0] != null && params[0] != "") ? " and ( designationName like '%" + params[0] + "%') " : "";
	    }
	    
	    public long getRecordsCount(String filterString, String... extraParams)
	    {
	        String sql = "select count(*) from DesignationInfo  where 1=1 " + filterString;
	        return (Long) getCount(sql);
	    }
	    
	    @SuppressWarnings("unchecked")
	    public List<DesignationInfo> getAllActiveDesignationInfo()
        {
            try
            {
                return find("from DesignationInfo where active = 1 order by designationName asc ");
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
                return new ArrayList<DesignationInfo>();
            }
        }
	    @SuppressWarnings("unchecked")
		 public DesignationInfo getDesignationByName(String disgName)
				
		    {
				try{
		            	 String sql = "from DesignationInfo  where active=1 and designationName ='"+disgName+"'";
		            	 List<DesignationInfo> t = find(sql);
		 	             return (t.size() > 0)  ? t.get(0) : null;
				}catch(Exception e){
					 LOG.info(e.getCause(),e);
			            return null;
				}
		    }
	    
	    public List<?> getActiveDesignations()
        {
            try{
                    String sql = "from DesignationInfo  where active = 1 order by designationName asc ";
                    return find(sql);
           }catch(Exception e){
                LOG.info(e.getCause(),e);
                   return null;
           }
        }
	    
}
