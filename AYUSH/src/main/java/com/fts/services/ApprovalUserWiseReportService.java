package com.fts.services;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.ThreadLocalData;
import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.dtos.ComboDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.UserSectionManager;
import com.fts.hibernate.models.UserInfo;
import com.fts.hibernate.models.UserSection;

@Service
public class ApprovalUserWiseReportService implements GridComponent,ComboComponent{
	private static final Log LOG = LogFactory.getLog(ApprovalUserWiseReportService.class);
	@Autowired
	private FileEntryManager fileEntryManager;
	@Autowired
	private FileStatusReportService fileStatusReportService;
	@Autowired
	private UserSectionManager userSectionManager;
	
	@Override
    public List<?> getData4Combo(String... extraParams)
    {
        List<ComboDTO> comboDTOs = new ArrayList<ComboDTO>();
        List<UserInfo> appUserInfos =null;
        appUserInfos =  fileEntryManager.getActiveFileApprovalUsers();
       
        if(appUserInfos!=null){
        for (UserInfo obj : appUserInfos)
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
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,String... extraParams) throws Exception {
		GridPaginationDTO gridDto = new GridPaginationDTO();
		LOG.info("9999------>"+extraParams[0]);
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
				long appUserId	=Long.parseLong(param[0]);
				String fromDate	=	param[1];
				String toDate	=	param[2];
				fileEntryList = fileEntryManager.getAllActiveFilesForApprovalUserWiseReport(sortInfo,filterString,start,limit,extraParams,appUserId,fromDate,toDate);
				gridDto.setTotal(fileEntryManager.getAllActiveFilesForApprovalUserWiseReportCount(filterString,extraParams,appUserId,fromDate,toDate));
			}
		}
		else
		{
			
			fileEntryList = fileEntryManager.getAllActiveFiles(sortInfo,filterString,start,limit,extraParams,secions);
			gridDto.setTotal(fileEntryManager.getAllActiveFilesCount(filterString,secions,extraParams));
		}
		
		gridDto.setRecords(fileStatusReportService.manipulateValues(fileEntryList));
		/*List<FileEntryDTO> feDtoList =new ArrayList<FileEntryDTO>(); 
		for(FileEntry fe:fileEntryList)
		{
			FileEntryDTO feDto = new FileEntryDTO();
			feDto.setId(fe.getId());
			feDto.setFileBarcode(fe.getFileBarcode());
			feDto.setSectionName(fe.getSection().getSectionName());
			feDto.setFileStatus(fe.getFileStatus().getFileTypeName());
			feDtoList.add(feDto);
		}
		gridDto.setRecords(feDtoList);*/
		return gridDto;
	}
}
