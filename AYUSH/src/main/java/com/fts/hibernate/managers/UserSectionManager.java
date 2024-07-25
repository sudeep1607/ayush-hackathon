package com.fts.hibernate.managers;

import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.Section;
import com.fts.hibernate.models.UserSection;

@Repository
public class UserSectionManager extends GenericManager<UserSection	, Long> {
	
	private static final Log LOG = LogFactory.getLog(UserSectionManager.class);
	public UserSectionManager( ) {
		super(UserSection.class);
	}
	
	 @SuppressWarnings("unchecked")
	    public List<UserSection> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
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
	            String sql = "from UserSection where 1=1 " + filterString + " order by " + sortColumn + " " + sortDirection;
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
	        String sql = "select count(*) from UserSection where 1=1 " + filterString;
	        return (Long) getCount(sql);
	    }
	    
	    
	    @SuppressWarnings("unchecked")
	    public List<UserSection> getMappedSectionsByUserId(long userId)
	    {
	        try
	        {
	            String sql = " from UserSection where user.id="+userId;
	            List<UserSection> usList = find(sql);
	            return usList!=null ? (usList):null;
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
	    }

		@SuppressWarnings("unchecked")
		public List<Section> getUserWiseSections(Long userId) 
		{
			try
	        {
	            String sql = " select s from UserSection us join us.section s  where  s.id=us.section.id and us.user.id="+userId;
	            List<Section> usList = find(sql);
	            return usList!=null ? (usList):null;
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
		}

}
