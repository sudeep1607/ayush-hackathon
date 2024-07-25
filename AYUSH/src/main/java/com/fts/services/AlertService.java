package com.fts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.dtos.AlertMonthDTO;
import com.fts.dtos.FileProcessDTO;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class AlertService implements GridComponent{
	
	private static final Log LOG  = LogFactory.getLog(AlertService.class);
	
	@Autowired
    private HQLQueryManager hQLQueryManager;
	@Autowired
    private WFApprovalAuthorityMapManager wFApprovalAuthorityMapManager;
	
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
			String... extraParams) throws Exception {
		 
		GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
		List<AlertMonthDTO> alertDTOs = hQLQueryManager.getAlertForMonthRecord(sortInfo, filterString, start, limit, extraParams);
		LOG.info("alertDTOs.size() :"+alertDTOs.size());
        gridPaginationDTO.setRecords(alertDTOs);
        gridPaginationDTO.setTotal(hQLQueryManager.getAlertForMonthRecordsCount(filterString, extraParams));
        return gridPaginationDTO;
	}


	public String getNotificationsforPopUp() 
	{
		String fileDetails="";
		int i=1;
		List<WFApprovalAuthorityMap> feWFAList = wFApprovalAuthorityMapManager.getAllFileWhichAreJustWFGenerated();
		List<FileProcessDTO> fpList=new ArrayList<FileProcessDTO>();
		for(WFApprovalAuthorityMap wfa:feWFAList)
		{
			FileProcessDTO  fpDto = new FileProcessDTO();
			fpDto.setId(wfa.getId());
			fpDto.setFileEntryId(wfa.getFileEntry().getId());
			fpDto.setFileName(wfa.getFileEntry().getFileName());
			
			fileDetails+= +i+"). <b>"+wfa.getFileEntry().getFileCode()+",</b> <span style='color:green;'> "+wfa.getFileEntry().getFileName()+"</span> <br/>";
			i++;		
			fpList.add(fpDto);
		}
		return fileDetails;
	}
}
