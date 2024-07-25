package com.fts.filemanagement.managers;


import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.fts.ThreadLocalData;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.UserInfo;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Repository
public class FileEntryManager extends GenericManager<FileEntry, Long> {
	
	private static final Log LOG = LogFactory.getLog( FileEntryManager.class);
	public  FileEntryManager( ) {
		super(FileEntry.class);
	}
	
	 @SuppressWarnings("unchecked")
	    public List<FileEntry> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
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
	            String sql = "";
	            if(ThreadLocalData.get().getDefaultRoleId()==3)
	            	sql = "from FileEntry  where 1=1 " +filterString + " and createdBy.id="+ThreadLocalData.get().getId()+" order by " + sortColumn + " " + sortDirection;
	            else
	            	sql = "from FileEntry  where 1=1 " +filterString + " order by " + sortColumn + " " + sortDirection;
	            return findByFilter(sql, start, limit);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
	    }
	    
	 
	 @SuppressWarnings("unchecked")
	    public List<WFApprovalAuthorityMap> getWorkflowByFileId(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
	    {
	        try
	        {
	            String sql = "";
	            sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+extraParams[0]+"'   order by ph.id desc " ;
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
	        String sql = "select count(*) from FileEntry where active=1  " +filterString;
	        return (Long) getCount(sql);
	    }

		@SuppressWarnings("unchecked")
		public List<FileEntry> getAllApprovedFileByUser(Vector<String> sortInfo, String filterString, String start,
				String limit, String[] extraParams)
		{
			 try
		        {
				 	String sql = "";
				 	if(ThreadLocalData.get().getDefaultRoleId()==3)
				 		sql = " select pw.fileEntry from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and pw.approvalAuthority.id="+ThreadLocalData.get().getId()+" and ph.active=1 and pw.fileInTime is not null "+filterString+" order by ph.id desc" ;
				 	else
				 		sql = " select pw.fileEntry from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and ph.active=1 and pw.fileInTime is not null "+filterString+" order by ph.id desc" ; 
		            return findByFilter(sql, start, limit);
		        }
		        catch (Exception e)
		        {
		            LOG.info(e.getCause(),e);
		            return null;
		        }
		}

		@SuppressWarnings("unchecked")
		public List<WFApprovalAuthorityMap> getFileDetailsByBarcode(String searchText) {
			try
	        {
				String sql = "";
				if(ThreadLocalData.get().getDefaultRoleId()==3)
					sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.fileBarcode='"+searchText+"' and pw.approvalAuthority.id="+ThreadLocalData.get().getId()+" and pw.fileStatus.id=10 and pw.fileInTime is null and pw.fileOutTime is null order by ph.id desc" ;
				else
					sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.fileBarcode='"+searchText+"'  and pw.fileStatus.id=10 and pw.fileInTime is null and pw.fileOutTime is null order by ph.id desc " ; 
					
	            return find(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
		}
		
		@SuppressWarnings("unchecked")
		public List<FileEntry> getFileDetailsByBarcodeToView(String searchText) {
			try
	        {
				String sql = "";
					sql = " from  FileEntry fe  where fe.active=1  and  fe.fileBarcode='"+searchText+"'   order by fe.id desc " ; 
	            return find(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
		}
		
		
		@SuppressWarnings("unchecked")
		public List<FileEntry> getAllActiveFiles(Vector<String> sortInfo, String filterString, String start,String limit, String[] extraParams,String secs)
		{
			 try
		        {
				 	String sql = "";
				 	if(ThreadLocalData.get().getDefaultRoleId()==3)
				 	{
				 		sql = "  from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" and fe.fileInitiator.section.id in("+secs+") order by fe.id desc" ;
				 	}
				 	else 
				 		sql = " from  FileEntry fe  where fe.active=1  "+filterString+" and fe.fileInitiator.section.id in("+secs+") order by fe.id desc" ; 
		            return findByFilter(sql, start, limit);
		        }
		        catch (Exception e)
		        {
		            LOG.info(e.getCause(),e);
		            return null;
		        }
		}
		
		
		@SuppressWarnings("unchecked")
		public List<FileEntry> getAllActiveFilesByFilters(Vector<String> sortInfo, String filterString, String start,String limit, String[] extraParams,long secId,long initiatorId,long fileStatusId,String fromDate,String toDate)
		{
			 try
		        {
				 	String sql = "";
				 	String condition="";
				 	if(secId>0)
				 	{
				 		if(initiatorId>0)
				 		{
				 			if(fileStatusId>0)
						 	{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.section.id="+secId+" and fe.fileInitiator.id="+initiatorId+" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.section.id="+secId+" and fe.fileInitiator.id="+initiatorId+" and fe.fileStatus.id="+fileStatusId+" ";
						 	}
				 			else
				 			{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.section.id="+secId+" and fe.fileInitiator.id="+initiatorId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.section.id="+secId+" and fe.fileInitiator.id="+initiatorId+"  ";
						 	}
				 		}
				 		else
				 		{
				 			if(fileStatusId>0)
						 	{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.section.id="+secId+" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.section.id="+secId+" and fe.fileStatus.id="+fileStatusId+" ";
						 	}
				 			else
				 			{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.section.id="+secId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.section.id="+secId+" ";
						 	}
				 		}
				 	}
				 	else
				 	{
				 		if(initiatorId>0)
				 		{
				 			if(fileStatusId>0)
						 	{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.fileInitiator.id="+initiatorId+" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.fileInitiator.id="+initiatorId+" and fe.fileStatus.id="+fileStatusId+" ";
						 	}
				 			else
				 			{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.fileInitiator.id="+initiatorId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.fileInitiator.id="+initiatorId+"  ";
						 	}
				 		}
				 		else
				 		{
				 			if(fileStatusId>0)
						 	{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.fileStatus.id="+fileStatusId+" ";
						 	}
				 			else
				 			{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" ";
						 	}
				 		}
				 	}
				    
				 	if(ThreadLocalData.get().getDefaultRoleId()==3) 
				 		sql = "  from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" "+condition+" order by fe.id desc" ;
				 	else
				 		sql = "  from  FileEntry fe  where fe.active=1  "+filterString+" "+condition+" order by fe.id desc" ; 
				 	
		            return findByFilter(sql, start, limit);
		        }
		        catch (Exception e)
		        {
		            LOG.info(e.getCause(),e);
		            return null;
		        }
		}
		
		public long getAllActiveFilesByFiltersCount(String filterString, String[] extraParams,long secId,long initiatorId,long fileStatusId,String fromDate,String toDate)
	    {
			String sql = "";
		 	String condition="";
		 	try
		 	{
				 	if(secId>0)
				 	{
				 		if(initiatorId>0)
				 		{
				 			if(fileStatusId>0)
						 	{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.section.id="+secId+" and fe.fileInitiator.id="+initiatorId+" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.section.id="+secId+" and fe.fileInitiator.id="+initiatorId+" and fe.fileStatus.id="+fileStatusId+" ";
						 	}
				 			else
				 			{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.section.id="+secId+" and fe.fileInitiator.id="+initiatorId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.section.id="+secId+" and fe.fileInitiator.id="+initiatorId+"  ";
						 	}
				 		}
				 		else
				 		{
				 			if(fileStatusId>0)
						 	{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.section.id="+secId+" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.section.id="+secId+" and fe.fileStatus.id="+fileStatusId+" ";
						 	}
				 			else
				 			{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.section.id="+secId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.section.id="+secId+" ";
						 	}
				 		}
				 	}
				 	else
				 	{
				 		if(initiatorId>0)
				 		{
				 			if(fileStatusId>0)
						 	{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.fileInitiator.id="+initiatorId+" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.fileInitiator.id="+initiatorId+" and fe.fileStatus.id="+fileStatusId+" ";
						 	}
				 			else
				 			{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.fileInitiator.id="+initiatorId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.fileInitiator.id="+initiatorId+"  ";
						 	}
				 		}
				 		else
				 		{
				 			if(fileStatusId>0)
						 	{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" and fe.fileStatus.id="+fileStatusId+" ";
						 	}
				 			else
				 			{
						 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
						 			condition=" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
						 		else
						 			condition=" ";
						 	}
				 		}
				 	}
			        
				 	if(ThreadLocalData.get().getDefaultRoleId()==3)
				 		sql = "  select count(fe) from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" "+condition+" order by fe.id desc" ;
				 	else 
				 		sql = " select count(fe) from  FileEntry fe  where fe.active=1  "+filterString+" "+condition+" order by fe.id desc" ; 
					return (Long) getCount(sql);
			}
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		
		@SuppressWarnings("unchecked")
		public List<FileEntry> getAllActiveFilesForSectionWiseReport(Vector<String> sortInfo, String filterString, String start,String limit, String[] extraParams,long secId,String fromDate,String toDate)
		{
			 try
		        {
				 	String sql = "";
				 	String condition="";
				 	if(secId>0)
				 	{
				 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
				 			condition=" and fe.section.id="+secId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
				 		else
				 			condition=" and fe.section.id="+secId+" ";
				 	}
		 			else
		 			{
				 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
				 			condition=" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
				 		else
				 			condition=" ";
				 	}
				 	if(ThreadLocalData.get().getDefaultRoleId()==3)
				 		sql = "  from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" "+condition+" order by fe.id desc" ;
				 	else 
				 		sql = "  from  FileEntry fe  where fe.active=1  "+filterString+" "+condition+" order by fe.id desc" ; 
				 	
		            return findByFilter(sql, start, limit);
		        }
		        catch (Exception e)
		        {
		            LOG.info(e.getCause(),e);
		            return null;
		        }
		}
		
		public long getAllActiveFilesForSectionWiseReportCount(String filterString, String[] extraParams,long secId,String fromDate,String toDate)
	    {
	        try
	        {
			 	String sql = "";
			 	String condition="";
			 	if(secId>0)
			 	{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and fe.section.id="+secId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" and fe.section.id="+secId+" ";
			 	}
	 			else
	 			{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" ";
			 	}
			 	
			 	if(ThreadLocalData.get().getDefaultRoleId()==3)
			 		sql = "  select count(fe) from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" "+condition+" order by fe.id desc" ;
			 	else 
			 		sql = " select count(fe) from  FileEntry fe  where fe.active=1  "+filterString+" "+condition+" order by fe.id desc" ; 
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		
		@SuppressWarnings("unchecked")
		public List<FileEntry> getAllActiveFilesForUserWiseReport(Vector<String> sortInfo, String filterString, String start,String limit, String[] extraParams,long initiatorId,String fromDate,String toDate)
		{
			 try
		        {
				 	String sql = "";
				 	String condition="";
				 	if(initiatorId>0)
				 	{
				 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
				 			condition=" and fe.fileInitiator.id="+initiatorId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
				 		else
				 			condition=" and fe.fileInitiator.id="+initiatorId+" ";
				 	}
		 			else
		 			{
				 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
				 			condition=" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
				 		else
				 			condition=" ";
				 	}
				 	
				 	if(ThreadLocalData.get().getDefaultRoleId()==3)
				 		sql = "  from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" "+condition+" order by fe.id desc" ;
				 	else 
				 		sql = "  from  FileEntry fe  where fe.active=1  "+filterString+" "+condition+" order by fe.id desc" ; 
				 	
		            return findByFilter(sql, start, limit);
		        }
		        catch (Exception e)
		        {
		            LOG.info(e.getCause(),e);
		            return null;
		        }
		}
		
		public long getAllActiveFilesForUserWiseReportCount(String filterString, String[] extraParams,long initiatorId,String fromDate,String toDate)
	    {
	        try
	        {
			 	String sql = "";
			 	String condition="";
			 	if(initiatorId>0)
			 	{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and fe.fileInitiator.id="+initiatorId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" and fe.fileInitiator.id="+initiatorId+" ";
			 	}
	 			else
	 			{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" ";
			 	}
			 	
			 	if(ThreadLocalData.get().getDefaultRoleId()==3)
			 		sql = "  select count(fe) from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" "+condition+" order by fe.id desc" ;
			 	else 
			 		sql = " select count(fe) from  FileEntry fe  where fe.active=1  "+filterString+" "+condition+" order by fe.id desc" ; 
			 	
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		
		@SuppressWarnings("unchecked")
		public List<FileEntry> getAllActiveFilesForStatusWiseReport(Vector<String> sortInfo, String filterString, String start,String limit, String[] extraParams,long fileStatusId,String fromDate,String toDate)
		{
			 try
		        {
				 	String sql = "";
				 	String condition="";
				 	if(fileStatusId>0)
				 	{
				 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
				 			condition=" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
				 		else
				 			condition=" and fe.fileStatus.id="+fileStatusId+" ";
				 	}
		 			else
		 			{
				 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
				 			condition=" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
				 		else
				 			condition=" ";
				 	}
				 	if(ThreadLocalData.get().getDefaultRoleId()==3)
				 		sql = "  from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" "+condition+" order by fe.id desc" ;
				 	else 
				 		sql = "  from  FileEntry fe  where fe.active=1  "+filterString+" "+condition+" order by fe.id desc" ; 
				 	
		            return findByFilter(sql, start, limit);
		        }
		        catch (Exception e)
		        {
		            LOG.info(e.getCause(),e);
		            return null;
		        }
		}
		
		public long getAllActiveFilesForStatusWiseReportCount(String filterString, String[] extraParams,long fileStatusId,String fromDate,String toDate)
	    {
	        try
	        {
			 	String sql = "";
			 	String condition="";
			 	if(fileStatusId>0)
			 	{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and fe.fileStatus.id="+fileStatusId+" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" and fe.fileStatus.id="+fileStatusId+" ";
			 	}
	 			else
	 			{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and fe.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" ";
			 	}
			 	
			 	if(ThreadLocalData.get().getDefaultRoleId()==3)
			 		sql = "  select count(fe) from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" "+condition+" order by fe.id desc" ;
			 	else 
			 		sql = " select count(fe) from  FileEntry fe  where fe.active=1  "+filterString+" "+condition+" order by fe.id desc" ; 
			 	
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		
		
		public long getAllActiveFilesCount(String filterString,String secs, String... extraParams)
	    {
	        try
	        {
			 	String sql = "";
			 	if(ThreadLocalData.get().getDefaultRoleId()==3)
			 		sql = "  select count(fe) from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" "+filterString+" and fe.fileInitiator.section.id in("+secs+") order by fe.id desc" ;
			 	else 
			 		sql = " select count(fe) from  FileEntry fe  where fe.active=1  "+filterString+" and fe.fileInitiator.section.id in("+secs+") order by fe.id desc" ; 
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }

		@SuppressWarnings("unchecked")
		public List<FileEntry> getAllActiveFilesForApprovalUserWiseReport(Vector<String> sortInfo, String filterString, String start,String limit, String[] extraParams,long appUserId,String fromDate,String toDate)
		{
			try
	        {
				String sql = "";
			 	String condition="";
			 	if(appUserId>0)
			 	{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and pw.approvalAuthority.id="+appUserId+" and ph.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" and pw.approvalAuthority.id="+appUserId+" ";
			 	}
	 			else
	 			{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and ph.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" ";
			 	}
				
				if(ThreadLocalData.get().getDefaultRoleId()==3) 
					sql = " select pw.fileEntry from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  "+condition+"  order by ph.id desc" ;
				else
					sql = " select pw.fileEntry from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id "+condition+"   order by ph.id desc " ; 
					
	            return find(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
		}
		
		
		public long getAllActiveFilesForApprovalUserWiseReportCount(String filterString, String[] extraParams,long appUserId,String fromDate,String toDate)
	    {
	        try
	        {
	        	String sql = "";
			 	String condition="";
			 	if(appUserId>0)
			 	{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and pw.approvalAuthority.id="+appUserId+" and ph.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" and pw.approvalAuthority.id="+appUserId+" ";
			 	}
	 			else
	 			{
			 		if(!fromDate.equalsIgnoreCase("null") && !toDate.equalsIgnoreCase("null"))
			 			condition=" and ph.fileCreatedDate between '"+fromDate+"' and  '"+toDate+"' ";
			 		else
			 			condition=" ";
			 	}
			 	
			 	if(ThreadLocalData.get().getDefaultRoleId()==3) 
			 		sql = " select count(pw.fileEntry) from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  "+condition+"  order by ph.id desc" ;
				else
					sql = " select count(pw.fileEntry) from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id "+condition+"   order by ph.id desc " ; 
					
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		
		
		@SuppressWarnings("unchecked")
		public List<UserInfo> getActiveFileInitiators() {
			 try{
	                String sql = " select distinct ui from  FileEntry fe join fe.fileInitiator ui   where   fe.fileInitiator.id=ui.id and ui.active=1 order by ui.firstName asc" ;
	                return find(sql);
	       }catch(Exception e){
	            LOG.info(e.getCause(),e);
	               return null;
	       }
		}
		
		@SuppressWarnings("unchecked")
		public List<UserInfo> getActiveFileInitiatorsBysection(long secId) {
			 try{
	                String sql = " select distinct ui from  FileEntry fe join fe.fileInitiator ui   where   fe.fileInitiator.id=ui.id and ui.active=1 and ui.section.id="+secId+" order by ui.firstName asc" ;
	                return find(sql);
	       }catch(Exception e){
	            LOG.info(e.getCause(),e);
	               return null;
	       }
		}
		@SuppressWarnings("unchecked")
		public List<UserInfo> getActiveFileApprovalUsers() {
			 try{
	                String sql = " select distinct ui from  WFApprovalAuthorityMap wfa join wfa.approvalAuthority ui   where   wfa.approvalAuthority.id=ui.id and ui.active=1  order by ui.firstName asc" ;
	                return find(sql);
	       }catch(Exception e){
	            LOG.info(e.getCause(),e);
	               return null;
	       }
		}

		@SuppressWarnings("unchecked")
		public FileEntry getAllFiles() 
		{
			try
	        {
	            String	sql = " from FileEntry  where active=1 order by id desc";
	            List<FileEntry> feList = find(sql);
	            return (feList!=null?(feList.size()>0 ?(feList.get(0)):null):null);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
		}

		public long getMaxFileNo() 
		{
			try {
				String sql =" select IFNULL(MAX(CAST(SUBSTRING(fe.fileNo,LOCATE('/', fe.fileNo) + 1)AS UNSIGNED)),0) from FileEntry as fe where 1=1 ";
				
				return  Long.valueOf(getColumnValue(sql).toString());
			} catch (DataAccessException e) {
				LOG.info("Exception while getting max File No"+ e.getStackTrace());
				return 0;
			}
		}
		
		
		public long getAllApprovedFiles(long sectionId)
	    {
	        try
	        {
			 	String sql = "";
			    String condition = "";
			 	if(ThreadLocalData.get().getDefaultRoleId() == 3)
			 	  condition =" and  fe.fileInitiator.id = "+ThreadLocalData.get().getId()+" ";
			 		
			 	sql = " select count(*) from  FileEntry fe  where fe.active=1  and  fe.fileStatus.id = 12 and fe.section.id='"+ sectionId +"' "+condition+" " ; 
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		public long getAllPendingFiles(long sectionId)
		{
	        try
	        {
			 	String sql = "";
			    String condition = "";
					if(ThreadLocalData.get().getDefaultRoleId() == 3)
				 	 condition =" and  fe.fileInitiator.id = "+ThreadLocalData.get().getId()+" ";
			 		sql = " select count(*) from  FileEntry fe  where fe.active=1  and  fe.fileStatus.id = 10 and fe.section.id='"+ sectionId +"' "+condition+" " ; 
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		public long getAllRejectedFiles(long sectionId)
		{
	        try
	        {
			 	String sql = "";
			 	String condition = "";
			 	if(ThreadLocalData.get().getDefaultRoleId() == 3)
			 	  condition =" and  fe.fileInitiator.id = "+ThreadLocalData.get().getId()+" ";
			 		sql = " select count(*) from  FileEntry fe  where fe.active=1  and  fe.fileStatus.id = 8 and fe.section.id='"+ sectionId +"' "+condition+"" ; 
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		public long getAllClosedFiles(long sectionId)
		{
	        try
	        {
			 	String sql = "";
			 	String condition = "";
			 	if(ThreadLocalData.get().getDefaultRoleId() == 3)
			 	  condition =" and  fe.fileInitiator.id = "+ThreadLocalData.get().getId()+" ";
			 		sql = " select count(*) from  FileEntry fe  where fe.active=1  and  fe.fileStatus.id = 14 and fe.section.id='"+ sectionId +"' "+condition+"" ; 
			 	return (Long) getCount(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		
		
		@SuppressWarnings("unchecked")
		public List<FileEntry> getAllActiveSelectedFileForProcessFlowView(String selFileIds)
		{
		 try
	        {
			 	String sql = "";
			 	if(ThreadLocalData.get().getDefaultRoleId()==3)
			 		sql = "  from  FileEntry fe  where fe.active=1  and fe.fileInitiator.id="+ThreadLocalData.get().getId()+" and fe.id in ("+selFileIds+")  order by fe.id asc" ;
			 	else 
			 		sql = "  from  FileEntry fe  where fe.active=1  and fe.id in ("+selFileIds+") order by fe.id asc" ; 
	            return find(sql);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
		}
		
		
}
