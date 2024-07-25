package com.fts.hibernate.managers;

import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.Constants;
import com.fts.ThreadLocalData;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.common.GenericManager;

	@Repository
	public class FileDashBoardManager extends GenericManager<FileEntry, Long> {
		private static final Log LOG = LogFactory.getLog( FileDashBoardManager.class);
		
		public  FileDashBoardManager( ) {
			super(FileEntry.class);
		}
		
		@SuppressWarnings("unchecked")
		public List<FileEntry> getFileStatusForDashboardBysectionId(Vector<String> sortInfo, String filterString, String start,String limit, String[] extraParams,long secId,String Status)
		{
			 try
		        {
				  String sql=" ";
				  String condition = "";
				  if(ThreadLocalData.get().getDefaultRoleId() == 3)
				 	  condition =" and  fe.fileInitiator.id = "+ThreadLocalData.get().getId()+" ";
				if(Status.equals("PENDING") || Status=="PENDING")
				{
				  sql = "  from  FileEntry fe  where fe.active=1 and fe.section.id='"+secId+"'  and fe.fileStatus.id='"+Constants.FileStatus.PENDING+"' "+condition+" order by fe.id desc" ;
				}
				else if(Status.equals("APPROVED") || Status=="APPROVED")
				{
					  sql = "  from  FileEntry fe  where fe.active=1 and fe.section.id='"+secId+"'  and fe.fileStatus.id='"+Constants.FileStatus.APPROVED+"' "+condition+" order by fe.id desc" ;
				}
				else if(Status.equals("CLOSED") || Status=="CLOSED")
				{
					  sql = "  from  FileEntry fe  where fe.active=1 and fe.section.id='"+secId+"'  and fe.fileStatus.id='"+Constants.FileStatus.CLOSED+"' "+condition+" order by fe.id desc" ;
				}
				else if(Status.equals("REJECT") || Status=="REJECT")
				{
					  sql = "  from  FileEntry fe  where fe.active=1 and fe.section.id='"+secId+"'  and fe.fileStatus.id='"+Constants.FileStatus.REJECT+"' "+condition+" order by fe.id desc" ;
				}
		            return findByFilter(sql, start, limit);
		        }
		        catch (Exception e)
		        {
		            LOG.info(e.getCause(),e);
		            return null;
		        }
		}
		
		public long getFileStatusForDashboardBysectionIdCount(String filterString, String[] extraParams,long secId,String Status)
	    {

			 try
			   {
						 String sql=" ";	
						  String condition = "";
						  if(ThreadLocalData.get().getDefaultRoleId() == 3)
						 	  condition =" and  fe.fileInitiator.id = "+ThreadLocalData.get().getId()+" ";
						if(Status.equals("PENDING") || Status=="PENDING")
						{
						  sql = "  select count(fe) from  FileEntry fe  where fe.active=1 and    fe.section.id='"+secId+"'    and fe.fileStatus.id='"+Constants.FileStatus.PENDING+"' "+condition+" order by fe.id desc" ;
						}
						else if(Status.equals("APPROVED") || Status=="APPROVED")
						{
							  sql = "   select count(fe) from  FileEntry fe  where fe.active=1 and fe.section.id='"+secId+"'  and fe.fileStatus.id='"+Constants.FileStatus.APPROVED+"' "+condition+" order by fe.id desc" ;
						}
						else if(Status.equals("CLOSED") || Status=="CLOSED")
						{
							  sql = "  select count(fe) from  FileEntry fe  where fe.active=1 and fe.section.id='"+secId+"'  and fe.fileStatus.id='"+Constants.FileStatus.CLOSED+"' "+condition+" order by fe.id desc" ;
						}
						else if(Status.equals("REJECT") || Status=="REJECT")
						{
							  sql = "  select count(fe) from  FileEntry fe  where fe.active=1 and fe.section.id='"+secId+"'  and fe.fileStatus.id='"+Constants.FileStatus.REJECT+"' "+condition+" order by fe.id desc" ;
						}
							return (Long) getCount(sql);
				}
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return 0;
	        }
	    }
		

}
