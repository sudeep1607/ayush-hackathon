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
import com.fts.hibernate.models.UserSection;

@Service
public class UserWiseReportService implements GridComponent,ComboComponent{
	private static final Log LOG = LogFactory.getLog(UserWiseReportService.class);
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
					long initiatorId	=Long.parseLong(param[0]);
					String fromDate	=	param[1];
					String toDate	=	param[2];
					fileEntryList = fileEntryManager.getAllActiveFilesForUserWiseReport(sortInfo,filterString,start,limit,extraParams,initiatorId,fromDate,toDate);
					gridDto.setTotal(fileEntryManager.getAllActiveFilesForUserWiseReportCount(filterString,extraParams,initiatorId,fromDate,toDate));
				}
			}
			else
			{
				
				fileEntryList = fileEntryManager.getAllActiveFiles(sortInfo,filterString,start,limit,extraParams,secions);
				gridDto.setTotal(fileEntryManager.getAllActiveFilesCount(filterString,secions,extraParams));
			}
			
			gridDto.setRecords(fileStatusReportService.manipulateValues(fileEntryList));
			return gridDto;
		} catch (Exception e) {
			LOG.info(""+e.getStackTrace(),e);
			return null;
		}
	}
}
