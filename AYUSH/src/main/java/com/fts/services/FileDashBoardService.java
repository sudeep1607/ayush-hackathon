package com.fts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.Constants;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.dtos.FileEntryDTO;
import com.fts.dtos.FileProcessDTO;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.FileDashBoardManager;
import com.fts.hibernate.managers.FileTypeManager;
import com.fts.utils.DateUtils;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class FileDashBoardService implements GridComponent {
	
	private static final Log LOG = LogFactory.getLog(FileDashBoardService.class);
	
	@Autowired
	private FileDashBoardManager fileDashBoardManager;
	
	
	@Autowired
	private WFApprovalAuthorityMapManager wFApprovalAuthorityMapManager;
	
	@Autowired
	private FileTypeManager fileTypeManager;
	
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,String... extraParams) throws Exception 
	{
		GridPaginationDTO gridDto = new GridPaginationDTO();
		try {
			List<FileEntry> fileEntryList=null;
			if(extraParams[0]!=null)
			{
				if(extraParams[0].contains(","))
				{
					String[] param	=	extraParams[0].split(",");
					long sectionId	=   Long.parseLong(param[1]);
					String status=    param[0];
					fileEntryList = fileDashBoardManager.getFileStatusForDashboardBysectionId(sortInfo,filterString,start,limit,extraParams,sectionId,status);
					gridDto.setTotal(fileDashBoardManager.getFileStatusForDashboardBysectionIdCount(filterString,extraParams,sectionId,status));
				}
			}
			gridDto.setRecords(setValuesForRecords(fileEntryList));
			return gridDto;
		} catch (Exception e) {
			LOG.info("  exception while getting the data from the filedashboard service----------> "+e.getMessage());
			return null;
		}
	}
	public List<FileEntryDTO> setValuesForRecords(List<FileEntry> fileEntryList)
	{
		List<FileEntryDTO> feDtoList =new ArrayList<FileEntryDTO>(); 
		for(FileEntry fe:fileEntryList)
		{
			FileEntryDTO feDto = new FileEntryDTO();
			feDto.setId(fe.getId());
			feDto.setFileNo(fe.getFileNo());
			feDto.setFileCreatedDate(fe.getFileCreatedDate());
			feDto.setFileName(fe.getFileName());
			feDto.setFileSubject(fe.getFileSubject());
	/*		feDto.setFileDetails(fe.getFileDetails());
			feDto.setFileComments(fe.getFileComments());
			feDto.setFilePriority(fe.getFilePriority());
			feDto.setFileType(fe.getFileType());
			feDto.setFileBarcode(fe.getFileBarcode());*/
			feDto.setSectionName(fe.getSection().getSectionName());
			feDto.setDesignation(fe.getFileInitiator().getDesignationInfo().getId());
			feDto.setFileStatus(fe.getFileStatus().getFileTypeName());
			feDto.setFileInitiatorName(fe.getFileInitiator().getFirstName()+" "+fe.getFileInitiator().getLastName());
			List<WFApprovalAuthorityMap> wfaList = wFApprovalAuthorityMapManager.getFilePendingWithWhomFromWFA(fe);
			
			if(wfaList!=null)
			{
				List<FileProcessDTO> fpList = new ArrayList<FileProcessDTO>(); 
				for(WFApprovalAuthorityMap wfa:wfaList)
				{
					FileProcessDTO fpDto = new FileProcessDTO();
						fpDto.setId(wfa.getId());
						fpDto.setFileEntryId(wfa.getFileEntry().getId());
						fpDto.setPriority(wfa.getPriority());
						fpDto.setApprovalUserName(wfa.getApprovalAuthority().getFirstName()+ "  " +wfa.getApprovalAuthority().getLastName());
						fpDto.setFileInTime(wfa.getFileInTime());
						fpDto.setFileOutTime(wfa.getFileOutTime());
						
						String duration ="";
						if(wfa.getFileInTime()!=null && wfa.getFileOutTime()!=null)
							duration = DateUtils.daysHoursSecsBetweenTwoTimeStamps(wfa.getFileInTime(),wfa.getFileOutTime());
						
						fpDto.setDuration(duration);
						
						if(wfa.getFileStatus().getId()==Constants.FileStatus.APPROVE)
							fpDto.setFileStatus(fileTypeManager.get(Constants.FileStatus.APPROVED).getFileTypeName());
						if(wfa.getFileStatus().getId()==Constants.FileStatus.CLOSE)
							fpDto.setFileStatus(fileTypeManager.get(Constants.FileStatus.CLOSED).getFileTypeName());
						else
							fpDto.setFileStatus(wfa.getFileStatus().getFileTypeName());
					fpList.add(fpDto);
				}
				feDto.setFileProcess(fpList);
			}
			
			feDtoList.add(feDto);
		}
		return feDtoList;
	}

}
