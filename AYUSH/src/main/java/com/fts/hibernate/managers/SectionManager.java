package com.fts.hibernate.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.Section;

@Repository
public class SectionManager extends GenericManager<Section	, Long> {
	
	private static final Log LOG = LogFactory.getLog(SectionManager.class);
	public SectionManager( ) {
		super(Section.class);
	}
	
	 @SuppressWarnings("unchecked")
	    public List<Section> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
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
	            String sql = "from Section  where 1=1" +prepareSearchQuery(extraParams)+ filterString + " order by " + sortColumn + " " + sortDirection;
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
	    	    	return (params != null && params[0] != null && params[0] != "") ? " and (sectionName like '%" + params[0] + "%') " : "";
	    }
	    
	    public long getRecordsCount(String filterString, String... extraParams)
	    {
	        String sql = "select count(*) from Section  where 1=1 " + filterString;
	        return (Long) getCount(sql);
	    }
	    public List<?> getActiveSections()
        {
            try{
                    String sql = "from Section  where active = 1";
                    return find(sql);
           }catch(Exception e){
                LOG.info(e.getCause(),e);
                   return null;
           }
        }

		 @SuppressWarnings("unchecked")
		    public List<Section> getAllActiveSections()
		    {
		        try
		        {
		            return find("from Section where active = 1 order by sectionName asc");
		        }
		        catch (Exception e)
		        {
		            LOG.info(e.getCause(), e);
		            return new ArrayList<Section>();
		        }
		    }

		@SuppressWarnings("unchecked")
		public Section getSectionBySecName(String secName) {
			try
	        {
	            String sql=" from Section where active = 1 and sectionName='"+secName+"' order by id desc ";
				List<Section> secList = find(sql);
	            return secList!=null ? (secList.size()>0?secList.get(0):null):null;
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(), e);
	            return null;
	        }
		}

}
