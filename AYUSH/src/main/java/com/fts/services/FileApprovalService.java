package com.fts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.dtos.FileEntryDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class FileApprovalService implements ServiceComponent,GridComponent{

	@Autowired
	private FileEntryManager fileEntryManager;
	@Autowired
	private WFApprovalAuthorityMapManager wFApprovalAuthorityMapManager;
	
	
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,String... extraParams) throws Exception {
		GridPaginationDTO gridDto = new GridPaginationDTO();
		List<FileEntry> fileEntryList = fileEntryManager.getAllApprovedFileByUser(sortInfo,filterString,start,limit,extraParams);
		List<FileEntryDTO> feDtoList =new ArrayList<FileEntryDTO>(); 
		for(FileEntry fe:fileEntryList)
		{
			FileEntryDTO feDto = new FileEntryDTO();
			feDto.setId(fe.getId());
			feDto.setFileBarcode(fe.getFileBarcode());
			feDto.setSectionName(fe.getSection().getSectionName());
			feDto.setFileStatus(fe.getFileStatus().getFileTypeName());
			
			feDto.setFileId(fe.getId());
			feDto.setFileBarcode(fe.getFileBarcode());
			feDto.setSection(fe.getSection().getId());
			feDto.setFileName(fe.getFileName());
			feDto.setFileCreatedDate(fe.getFileCreatedDate());
			feDto.setFileSubject(fe.getFileSubject());
			feDto.setFileDetails(fe.getFileDetails());
			feDto.setFilePriority(fe.getFilePriority());
			feDto.setFileType(fe.getFileType());
			feDto.setFileInitiator(fe.getFileInitiator().getId());
			feDto.setInitiatorName(fe.getFileInitiator().getFirstName());
			
			feDto.setPriority(getPriorityWhoFileReadAndActionNotDone(fe));
			feDto.setPendingWith(getPendingWithName(fe));
			
			feDtoList.add(feDto);
		}
		gridDto.setRecords(feDtoList);
		return gridDto;
	}
	
	public String getPendingWithName(FileEntry fe)
	{
		String pendingWithUser="";
		List<WFApprovalAuthorityMap> wfaList = wFApprovalAuthorityMapManager.getFilePendingWithWhomFromWFA(fe);
		if(wfaList!=null)
		{
			for(WFApprovalAuthorityMap wfa :wfaList)
			{
				/*if(wfa.getFileInTime()!=null && wfa.getFileOutTime()!=null && wfa.getFileStatus().getId()==10)
					pendingWithUser = wfa.getApprovalAuthority().getFirstName()+" "+wfa.getApprovalAuthority().getLastName();
				else if(wfa.getFileInTime()!=null && wfa.getFileStatus().getId()==10)
					pendingWithUser = wfa.getApprovalAuthority().getFirstName()+" "+wfa.getApprovalAuthority().getLastName();
				else */
				if(wfa.getFileStatus().getId()==10)
					pendingWithUser = wfa.getApprovalAuthority().getFirstName()+" "+wfa.getApprovalAuthority().getLastName();
				else
					pendingWithUser="";
			}
		}
		return pendingWithUser;
	}
	public long getPriorityWhoFileReadAndActionNotDone(FileEntry fe)
	{
		long priority=0;
		WFApprovalAuthorityMap wfa = wFApprovalAuthorityMapManager.getPriorityByFileReadAndActionNotTaken(fe);
		if(wfa!=null)
			priority = wfa.getPriority();
		return priority;
	}

	@Override
	public String insert(String jsonData, String... extraParams) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
