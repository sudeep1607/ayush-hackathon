package com.fts.hibernate.managers;

import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.ThreadLocalData;
import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.UserInfo;

@Repository
public class UserManager extends GenericManager<UserInfo, Long>
{
    private static final Log LOG = LogFactory.getLog(UserManager.class);

    public UserManager()
    {
        super(UserInfo.class);
    }

    @SuppressWarnings("unchecked")
    public List<UserInfo> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String[] extraParams)
    {
        String sortColumn = "firstName";
        String sortDirection = "asc";
        if (sortInfo.size() > 0)
        {
            sortColumn = sortInfo.get(0);
            sortDirection = sortInfo.get(1);
        }
        String sql = "from UserInfo  where 1=1 " + prepareSearchQuery(extraParams) + filterString + " order by " + sortColumn + " " + sortDirection;
        return findByFilter(sql, start, limit);
    }
    
   
    public String prepareSearchQuery(String... params)
    {
    	  	return (params != null && params[0] != null && params[0] != "") ? " and (employeeId like '%" + params[0] + "%' or firstName like '%" + params[0] + "%' ) " : "";
    }
    
    
    
    @SuppressWarnings("unchecked")
    public List<UserInfo> authoriseeFilterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String[] extraParams)
    {
        String sortColumn = "firstName";
        String sortDirection = "asc";
        
        if (sortInfo.size() > 0)
        {
            sortColumn = sortInfo.get(0);
            sortDirection = sortInfo.get(1);
        }
        String sql = "from UserInfo  where 1=1  and isAuthoriseeUser = 1 and authoriserId = "+  ThreadLocalData.get().getId()  + " " + filterString + " order by " + sortColumn + " " + sortDirection;
        return findByFilter(sql, start, limit);
    }

    public long getRecordsCount(String filterString)
    {
        String sql = "select count(*) from UserInfo  where 1=1  and active=1" + filterString;
        return (Long) getCount(sql);
    }
    
    public long getAuthoriseeRecordsCount(String filterString)
    {
        String sql = "select count(*) from UserInfo  where 1=1  and  isAuthoriseeUser = 1 and authoriserId ="+  ThreadLocalData.get().getId()  +" " + filterString;
        return (Long) getCount(sql);
    }
    
    
    
    @SuppressWarnings("unchecked")
    public UserInfo getUserByUserName(String loginId)
    {
        try
        {
        	
            List<UserInfo> userInfos = (List<UserInfo>) find("from UserInfo where employeeId = ?", loginId);
            return userInfos.size() > 0 ? userInfos.get(0) : null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
        }
        return null;
    }
    @SuppressWarnings("unchecked")
    public UserInfo getUserByEmplyeeId(String loginId)
    {
        try
        {
        	 String sql = "from UserInfo where 1=1  and employeeId='"+loginId+"'";
        	 List<UserInfo> t = find(sql);
	         return (t.size() > 0)  ? t.get(0) : null;
        }
        catch (Exception e)
        {
        	LOG.info(e.getMessage());
            return null;
        }
    }
    @SuppressWarnings("unchecked")
	public List<UserInfo> getActiveUsers()
    {
        try{
                String sql = "from UserInfo  where active = 1 order by firstName asc ";
                return find(sql);
       }catch(Exception e){
            LOG.info(e.getCause(),e);
               return null;
       }
    }
    
    @SuppressWarnings("unchecked")
	public List<UserInfo> getActiveUsersBySection(long sectionId)
    {
        try{
                String sql = "from UserInfo  where active = 1 and section.id="+sectionId+" order by firstName asc ";
                return find(sql);
       }catch(Exception e){
            LOG.info(e.getCause(),e);
               return null;
       }
    }
    
    @SuppressWarnings("unchecked")
	public List<UserInfo> getActiveUsersBySectionNdesig(long sectionId,long desigId)
    {
        try{
                String sql = "from UserInfo  where active = 1 and section.id="+sectionId+" and designationInfo.id="+desigId+" order by firstName asc ";
                return find(sql);
       }catch(Exception e){
            LOG.info(e.getCause(),e);
               return null;
       }
    }
   
 }

    
   
