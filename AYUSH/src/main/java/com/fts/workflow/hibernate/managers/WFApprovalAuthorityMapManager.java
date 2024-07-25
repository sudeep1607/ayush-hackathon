package com.fts.workflow.hibernate.managers;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.fts.ThreadLocalData;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.common.GenericManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Repository
public class WFApprovalAuthorityMapManager extends GenericManager<WFApprovalAuthorityMap, Long> {
	
	private static final Log LOG = LogFactory.getLog(WFApprovalAuthorityMapManager.class);

	public WFApprovalAuthorityMapManager() {
		super(WFApprovalAuthorityMap.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<WFApprovalAuthorityMap> getByWorkFlowConfigId(Long id)
	{
		try {
			String sql=" from WFApprovalAuthorityMap where fileEntry.id = "+id+" order by PRIORITY asc ";
			return find(sql);
		} catch (DataAccessException e) {
			LOG.info(e.getMessage(),e);
			return new ArrayList<WFApprovalAuthorityMap>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WFApprovalAuthorityMap> getApprovalAuthoritiesByWFConfig(FileEntry fe)
	{
		try {
			String sql=" from WFApprovalAuthorityMap where active =1 and fileEntry.id = "+fe.getId()+" order by PRIORITY asc ";
			return find(sql);
		} catch (DataAccessException e) {
			LOG.info(e.getMessage(),e);
			return new ArrayList<WFApprovalAuthorityMap>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WFApprovalAuthorityMap> getRequiredPerson(FileEntry fe) 
	{
		try {
			return find("from WFApprovalAuthorityMap where active =1 and fileEntry.id = "+fe.getId()+" and  approvalAuthority.authorityName = '0'");
		} catch (DataAccessException e) {
			LOG.info(e.getMessage(),e);
			return new ArrayList<WFApprovalAuthorityMap>();
		}
	}

	public long getMaxPriorityRecordByFileId(long fileId) {
		try {
		        String sql = "select count(*) from WFApprovalAuthorityMap where active=1  and fileEntry.id=" +fileId;
		        LOG.info("getMaxPriorityRecordByFileId--------->"+sql);
		        return (Long) getCount(sql);
		} catch (DataAccessException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public WFApprovalAuthorityMap getPendingFileByFileId(long id) {
		try
        {
			String sql = "";
			if(ThreadLocalData.get().getDefaultRoleId()==3)
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+id+"' and pw.approvalAuthority.id="+ThreadLocalData.get().getId()+" and pw.fileStatus.id=10 and pw.fileInTime is not null and pw.fileOutTime is null order by ph.id desc" ;
			else
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+id+"'  and pw.fileStatus.id=10 and pw.fileInTime is not null and pw.fileOutTime is null order by ph.id desc" ; 
			List<WFApprovalAuthorityMap> feList = find(sql);
			if(feList!=null && feList.size()>0)
				return feList.get(0);
			else
				return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}
	
	@SuppressWarnings("unchecked")
	public WFApprovalAuthorityMap getNextPendingFileByFileId(long id,long priority) {
		try
        {
			String sql = "";
			priority =priority+1;
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+id+"'  and pw.priority="+priority+" and pw.fileStatus.id=11  order by ph.id desc" ; 
			List<WFApprovalAuthorityMap> feList = find(sql);
			if(feList!=null && feList.size()>0)
				return feList.get(0);
			else
				return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}
	
	@SuppressWarnings("unchecked")
	public WFApprovalAuthorityMap getPreviousPriorityFileByFileId(long id,long priority) {
		try
        {
			String sql = "";
			priority =priority-1;
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+id+"'  and pw.priority="+priority+" and pw.fileStatus.id=13  order by ph.id desc" ; 
			List<WFApprovalAuthorityMap> feList = find(sql);
			if(feList!=null && feList.size()>0)
				return feList.get(0);
			else
				return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}
	
	@SuppressWarnings("unchecked")
	public WFApprovalAuthorityMap getFileToUpdateInTime(long id) {
		try
        {
			String sql = "";
			if(ThreadLocalData.get().getDefaultRoleId()==3)
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+id+"' and pw.approvalAuthority.id="+ThreadLocalData.get().getId()+" and pw.fileStatus.id=10 and pw.fileInTime is null and pw.fileOutTime is null order by ph.id desc" ;
			else
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+id+"'  and pw.fileStatus.id=10 and pw.fileInTime is null and pw.fileOutTime is null order by ph.id desc " ; 
				
			List<WFApprovalAuthorityMap> wfList = find(sql);
			if(wfList!=null && wfList.size()>0)
				return wfList.get(0);
			else
				return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}

	@SuppressWarnings("unchecked")
	public WFApprovalAuthorityMap getRecordByPriorityNfileId(FileEntry fe, Long priority) {
		try
        {
			String sql = "";
			if(ThreadLocalData.get().getDefaultRoleId()==3)
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+fe.getId()+"' and pw.approvalAuthority.id="+ThreadLocalData.get().getId()+" and pw.priority="+priority+" order by ph.id desc" ;
			else
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+fe.getId()+"'  and pw.priority="+priority+" order by ph.id desc " ; 
				
			LOG.info("getRecordByPriorityNfileId---------->"+sql);
			List<WFApprovalAuthorityMap> wfList = find(sql);
			if(wfList!=null && wfList.size()>0)
				return wfList.get(0);
			else
				return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}

	@SuppressWarnings("unchecked")
	public WFApprovalAuthorityMap getPriorityByFileReadAndActionNotTaken(FileEntry fe)
	{
		try
        {
			String sql = "";
				sql = " select pw from  WFApprovalAuthorityMap pw  join  pw.fileEntry ph  where pw.fileEntry.id = ph.id  and  ph.id='"+fe.getId()+"' and pw.fileInTime is not null and pw.fileOutTime is null and pw.fileStatus.id=10 order by ph.id desc" ;
			List<WFApprovalAuthorityMap> wfList = find(sql);
			if(wfList!=null && wfList.size()>0)
				return wfList.get(0);
			else
				return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WFApprovalAuthorityMap> getFilePendingWithWhomFromWFA(FileEntry fe)
	{
		try
        {
			String sql = " from  WFApprovalAuthorityMap  where fileEntry.id='"+fe.getId()+"'  order by priority asc" ;	
			List<WFApprovalAuthorityMap> wfList = find(sql);
			if(wfList!=null && wfList.size()>0)
				return wfList;
			else
				return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<WFApprovalAuthorityMap> getAllFileWhichAreJustWFGenerated()
	{
		try
        {
			String sql = "";
			if(ThreadLocalData.get().getDefaultRoleId()==3)
				sql = " from  WFApprovalAuthorityMap  where  approvalAuthority.id="+ThreadLocalData.get().getId()+" and fileInTime is null and fileOutTime is null order by priority asc" ;	
			else
				sql = " from  WFApprovalAuthorityMap  where  fileInTime is null and fileOutTime is null order by priority asc" ;
			List<WFApprovalAuthorityMap> wfList = find(sql);
			if(wfList!=null && wfList.size()>0)
				return wfList;
			else
				return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}
	
}
