package com.fts.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.dtos.EnployeeInfoDTO;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.UserManager;
import com.fts.hibernate.models.UserInfo;
@Service
public class FileCreationAuthenticationReleaseService implements GridComponent {
	    @Autowired
	    private HQLQueryManager hqlQueryManager;
	    @Autowired
	    private UserManager userManager;
	  
	  @Override
	  public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
			String... extraParams) throws Exception {
		   GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
	        List<EnployeeInfoDTO> records = mapValues(hqlQueryManager.getAllAssignedAuthenticationsUsers());
	        gridPaginationDTO.setRecords(records);
	        gridPaginationDTO.setSuccess(true);
	        gridPaginationDTO.setTotal(Long.parseLong(String.valueOf(records.size())));
	        return gridPaginationDTO;
	}


	private List<EnployeeInfoDTO> mapValues(List<EnployeeInfoDTO> allJobAssignedReportMangersUsers) {
		List<EnployeeInfoDTO> enployeeInfoDTOs =new ArrayList<EnployeeInfoDTO>();
		for(EnployeeInfoDTO infoDTO:allJobAssignedReportMangersUsers)
		{
			EnployeeInfoDTO infoDTO2 = new EnployeeInfoDTO();
			infoDTO2.setId(infoDTO.getId());
			infoDTO2.setAssigningRemarks(infoDTO.getAssigningRemarks());
			//infoDTO2.setEmployeeName(infoDTO.getEmployeeName()); 
			infoDTO2.setEmployeeId(infoDTO.getEmployeeId());
			UserInfo userEmployee = userManager.getUserByEmplyeeId(infoDTO.getEmployeeId());
			if(userEmployee!=null)
			{
				infoDTO2.setEmployeeName(userEmployee.getLastName()!=null?userEmployee.getFirstName().toUpperCase()+" "+userEmployee.getLastName().toUpperCase():userEmployee.getFirstName().toUpperCase()); 
			}
			infoDTO2.setDesignationName(infoDTO.getDesignationName());
			infoDTO2.setSectionName(infoDTO.getSectionName());
			enployeeInfoDTOs.add(infoDTO2);
		}
	
		return enployeeInfoDTOs;
	}

}

