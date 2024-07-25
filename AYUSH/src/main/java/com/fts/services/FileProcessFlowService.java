package com.fts.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.dtos.FileProcessDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.utils.DateUtils;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class FileProcessFlowService implements GridComponent, ServiceComponent, ComboComponent{
	
	private static final Log LOG  = LogFactory.getLog(FileProcessFlowService.class);
	
	@Autowired
	private FileEntryManager fileEntryManager;
	@Autowired
	private WFApprovalAuthorityMapManager wFApprovalAuthorityMapManager;
	@Override
	public List<?> getData4Combo(String... extraParams) {
		return fileEntryManager.loadAll();
	}
	
    @Override
    @Transactional
	public String insert(String jsonData, String... extraParams) throws Exception {
	    try
        {
	    	return "";
        }  
        catch (Exception e)
        {
            LOG.info(""+e.getStackTrace(),e);
            return "Failure";
        }
	}
	
    
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,String... extraParams) throws Exception 
	{
		 
		GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
		List<WFApprovalAuthorityMap> wfaList =null;
		List<FileProcessDTO> fpDtoList = new ArrayList<FileProcessDTO>();
		if(extraParams[0]!=null)
		{
			wfaList = wFApprovalAuthorityMapManager.getByWorkFlowConfigId(Long.parseLong(extraParams[0]));
			 gridPaginationDTO.setTotal(wFApprovalAuthorityMapManager.getMaxPriorityRecordByFileId(Long.parseLong(extraParams[0])));
		}
		for(WFApprovalAuthorityMap wfa : wfaList)
		{
			FileProcessDTO fpDto = new FileProcessDTO();
				
				fpDto.setId(wfa.getId());
				if(wfa.getActionTypes()!=null)
					fpDto.setFileActions(wfa.getActionTypes());
				else
					fpDto.setFileActions(wfa.getFileStatus().getFileTypeName());
				
				fpDto.setFileStatus(wfa.getFileStatus().getFileTypeName());
				fpDto.setPriority(wfa.getPriority());
				fpDto.setFileInTime(wfa.getFileInTime());
				fpDto.setFileOutTime(wfa.getFileOutTime());
				fpDto.setFileEntryId(wfa.getFileEntry().getId());
				fpDto.setApprovalUserName(wfa.getApprovalAuthority().getFirstName()+ " "+wfa.getApprovalAuthority().getLastName());
				String duration ="";
				if(wfa.getFileInTime()!=null && wfa.getFileOutTime()!=null)
					duration = DateUtils.daysHoursSecsBetweenTwoTimeStamps(wfa.getFileInTime(),wfa.getFileOutTime());
				
				fpDto.setDuration(duration);
			fpDtoList.add(fpDto);
		}
        gridPaginationDTO.setRecords(fpDtoList);
       
        return gridPaginationDTO;
	}
}

