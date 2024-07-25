package com.fts.services;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.Constants;
import com.fts.ThreadLocalData;
import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.dtos.ComboDTO;
import com.fts.dtos.FileEntryDTO;
import com.fts.dtos.FileProcessDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.FileTypeManager;
import com.fts.hibernate.managers.UserSectionManager;
import com.fts.hibernate.models.UserInfo;
import com.fts.hibernate.models.UserSection;
import com.fts.utils.DateUtils;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class FileStatusReportService implements GridComponent,ComboComponent{
	private static final Log LOG = LogFactory.getLog(FileStatusReportService.class);
	@Autowired
	private FileEntryManager fileEntryManager;
	@Autowired
	private WFApprovalAuthorityMapManager wFApprovalAuthorityMapManager;
	@Autowired
	private FileTypeManager fileTypeManager;
	@Autowired
	private UserSectionManager userSectionManager;
	
	
	@Override
    public List<?> getData4Combo(String... extraParams)
    {
        List<ComboDTO> comboDTOs = new ArrayList<ComboDTO>();
        List<UserInfo> userInfos =null;
        if(extraParams[0]!=null)
        	userInfos =  fileEntryManager.getActiveFileInitiatorsBysection(Long.parseLong(extraParams[0]));
        else
        	userInfos =  fileEntryManager.getActiveFileInitiators();
        
        if(userInfos!=null){
        for (UserInfo obj : userInfos)
        {
            ComboDTO comboDTO = new ComboDTO();
            comboDTO.setId(obj.getId());
            String lastName = obj.getLastName() != null ? obj.getLastName() : "";
            comboDTO.setName(obj.getFirstName()+" "+lastName);
            comboDTO.setRefId(obj.getDesignationInfo() != null ? obj.getDesignationInfo().getDesignationName() : null);
            comboDTO.setSectionId(obj.getSection().getId());
            comboDTOs.add(comboDTO);
        }
        }
        return comboDTOs;
    }
	
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,String... extraParams) throws Exception 
	{
		GridPaginationDTO gridDto = new GridPaginationDTO();
		try {
			List<FileEntry> fileEntryList=null;
			String secions="";
			List<UserSection> usrSecList = userSectionManager.getMappedSectionsByUserId(ThreadLocalData.get().getId());
			if(usrSecList!=null && usrSecList.size()>0)
			{
				for(UserSection usrSec :usrSecList)
				{
					secions+=usrSec.getSection().getId()+",";
				}
				if (secions.endsWith(",")) 
				{
					secions = secions.substring(0, secions.length() - 1);
				}
			}
			
			if(extraParams[0]!=null)
			{
				if(extraParams[0].contains(","))
				{
					String[] param	=	extraParams[0].split(",");
					long sectionId	=Long.parseLong(param[0]);
					long initiatorId	=Long.parseLong(param[1]);
					long fileStatusId	=Long.parseLong(param[2]);
					String fromDate	=	param[3];
					String toDate	=	param[4];
					fileEntryList = fileEntryManager.getAllActiveFilesByFilters(sortInfo,filterString,start,limit,extraParams,sectionId,initiatorId,fileStatusId,fromDate,toDate);
					gridDto.setTotal(fileEntryManager.getAllActiveFilesByFiltersCount(filterString,extraParams,sectionId,initiatorId,fileStatusId,fromDate,toDate));
				}
			}
			else
			{
				
				fileEntryList = fileEntryManager.getAllActiveFiles(sortInfo,filterString,start,limit,extraParams,secions);
				gridDto.setTotal(fileEntryManager.getAllActiveFilesCount(filterString,secions,extraParams));
			}
			gridDto.setRecords(manipulateValues(fileEntryList));
			return gridDto;
		} catch (Exception e) {
			LOG.info(""+e.getStackTrace());
			return null;
		}
	}
	
	public List<FileEntryDTO> manipulateValues(List<FileEntry> fileEntryList)
	{
		List<FileEntryDTO> feDtoList =new ArrayList<FileEntryDTO>(); 
		for(FileEntry fe:fileEntryList)
		{
			FileEntryDTO feDto = new FileEntryDTO();
			feDto.setId(fe.getId());
			feDto.setFileId(fe.getId());
			feDto.setFileNo(fe.getFileNo());
			feDto.setFileCreatedDate(fe.getFileCreatedDate());
			feDto.setSection(fe.getSection().getId());
			feDto.setFileName(fe.getFileName());
			feDto.setFileSubject(fe.getFileSubject());
			feDto.setFileDetails(fe.getFileDetails());
			feDto.setFileComments(fe.getFileComments());
			feDto.setFilePriority(fe.getFilePriority());
			feDto.setFileType(fe.getFileType());
			feDto.setFileBarcode(fe.getFileBarcode());
			feDto.setSectionName(fe.getSection().getSectionName());
			feDto.setDesignation(fe.getFileInitiator().getDesignationInfo().getId());
			feDto.setFileStatus(fe.getFileStatus().getFileTypeName());
			feDto.setFileInitiatorName(fe.getFileInitiator().getFirstName()+" "+fe.getFileInitiator().getLastName());
			feDto.setAttachFilePath(fe.getAttachFilePath());
			
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
