package com.fts.hibernate.managers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.FileCreationAuthentication;
import com.fts.hibernate.models.UserInfo;
@Repository
public class FileCreationAuthenticationManager extends GenericManager<FileCreationAuthentication, Long> { 
		
	private static final Log LOG = LogFactory.getLog(FileCreationAuthenticationManager.class);
		public  FileCreationAuthenticationManager() {
			super(FileCreationAuthentication.class);
		}
		 @SuppressWarnings("unchecked")
		    public UserInfo getUserByEmployeeId(String loginId)
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


}
