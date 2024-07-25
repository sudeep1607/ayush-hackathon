package com.fts.workflow.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.Constants;
import com.fts.ThreadLocalData;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.dtos.ComboDTO;
import com.fts.dtos.WorkflowConfigDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.FileTypeManager;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.UserManager;
import com.fts.hibernate.models.UserInfo;
import com.fts.services.CommonService;
import com.fts.utils.DateUtils;
import com.fts.workflow.dtos.AuthorityDTO;
import com.fts.workflow.dtos.StatusDTO;
import com.fts.workflow.dtos.WorkflowAuthorityMapDTO;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class WorkflowConfigService implements GridComponent,ServiceComponent{

    private static final Log LOG = LogFactory.getLog(WorkflowConfigService.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private HQLQueryManager hqlQueryManager;
	
	@Autowired
	private WFApprovalAuthorityMapManager wfApprovalAuthorityMapManager;
	@Autowired
	private FileTypeManager fileTypeManager;
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private FileEntryManager fileEntryManager;
	
	@Override
	public String insert(String jsonData, String... extraParams) throws Exception 
	{
		String response  = "Failure";
	 	try {
	 		WorkflowConfigDTO wrfDto = new ObjectMapper().readValue(jsonData, WorkflowConfigDTO.class);
	 		FileEntry fe = fileEntryManager.get(wrfDto.getFileId());
	 		if(wrfDto != null && wrfDto.getApprovalUsers().get(0).getId() != null &&  wrfDto.getApprovalUsers().get(0).getId() > 0)
			{
				response = updateApprovalAuthorities(fe,true,wrfDto);
			}else 
			{
				response = 	updateApprovalAuthorities(fe,false,wrfDto);
			}
	 		
	 		return response;
		} catch (Exception e) {
			LOG.info(e.getMessage(),e);
			 return response;
		}
		
	}

	public String updateApprovalAuthorities(FileEntry fe,boolean objPersistStatus,WorkflowConfigDTO wf)
	{
		try {
			List<WFApprovalAuthorityMap> existingWFAuthoList = null;
			List<WFApprovalAuthorityMap> newWFAuthoList = new ArrayList<>();
			//String[] authorityObj = wf.getAuthorityIds().split(",");
			UserInfo userInfo;
			WFApprovalAuthorityMap  wfApprovalAuthorityMap;
			//if(objPersistStatus)
			
			existingWFAuthoList =  wfApprovalAuthorityMapManager.getByWorkFlowConfigId(fe.getId());
			if(existingWFAuthoList!=null)
			{
				for (WFApprovalAuthorityMap WFA : existingWFAuthoList) 
				{
					WFA.setActive(0);
					wfApprovalAuthorityMapManager.delete(WFA);
				}
			}
			
			for(ComboDTO appUser : wf.getApprovalUsers())
			{
				userInfo =userManager.get(appUser.getAppUser());
				if(userInfo!=null)
				{
					boolean flag =true;
					if(flag== true)
					{
						wfApprovalAuthorityMap = new WFApprovalAuthorityMap();
						wfApprovalAuthorityMap.setFileEntry(fe);
						wfApprovalAuthorityMap.setApprovalAuthority(userInfo);
						wfApprovalAuthorityMap.setCreatedon(DateUtils.getCurrentSystemTimestamp());
						wfApprovalAuthorityMap.setCreatedBy(ThreadLocalData.get());
						wfApprovalAuthorityMap.setPriority(appUser.getPriority());
						wfApprovalAuthorityMap.setActionTypes(appUser.getFileTypeCode());
						if(wfApprovalAuthorityMap.getPriority()==1)
							wfApprovalAuthorityMap.setFileStatus(fileTypeManager.get(Constants.FileStatus.PENDING));
						else
							wfApprovalAuthorityMap.setFileStatus(fileTypeManager.get(Constants.FileStatus.WAITINGAPPROVAL));
						wfApprovalAuthorityMap.setActive(1);
						newWFAuthoList.add(wfApprovalAuthorityMap);
					}
				}
			}
			
			
			if(newWFAuthoList.size() >0)
				wfApprovalAuthorityMapManager.saveOrUpdateAll(newWFAuthoList);
			
			return "Success";
		} catch (Exception e) {
			LOG.info(e.getMessage(),e);
			return "Failure";
		}
	}
	
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
			String... extraParams) throws Exception {
		GridPaginationDTO gridPaginationDTO  = new GridPaginationDTO();
		List<WFApprovalAuthorityMap> records =null;
		if(extraParams[0]!=null)
					records = fileEntryManager.getWorkflowByFileId(sortInfo, filterString, start, limit, extraParams);
		
		
		List<WorkflowConfigDTO> wfListDto = new ArrayList<WorkflowConfigDTO>();
		for(WFApprovalAuthorityMap wf : records)
		{
			WorkflowConfigDTO wfDto = new WorkflowConfigDTO();
			wfDto.setId(wf.getId());
			wfDto.setFileEntry(wf.getFileEntry().getId());
			wfDto.setUserName(wf.getApprovalAuthority().getFirstName());
			wfDto.setAppUser(wf.getApprovalAuthority().getId()+"");
			wfDto.setSection(wf.getApprovalAuthority().getSection().getId());
			wfDto.setDesignation(wf.getApprovalAuthority().getDesignationInfo().getId());
			wfDto.setSectionName(wf.getApprovalAuthority().getSection().getSectionName());
			wfDto.setDesignationName(wf.getApprovalAuthority().getDesignationInfo().getDesignationName());
			wfDto.setFileTypeCode(wf.getActionTypes());
			wfDto.setPriority(wf.getPriority());
			wfDto.setEditMode(0);
			wfListDto.add(wfDto);
		}
		gridPaginationDTO.setRecords(wfListDto);
		gridPaginationDTO.setSuccess(true);
		return gridPaginationDTO;
	}
	public String validateWorkFlowNames(String value, Long id)
    {
        try
        {
            if (commonService.isFieldValueExits(id, value, "name", "WorkflowConfig"))
            {
                return "{\"success\":true,\"message\":\"name Already Exists.\"}";
            }
            else
            {
                return "{\"success\":true,\"message\":true}";
            }
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return "{\"success\":false,\"message\":\"Remote Exception\"}";
        }
    }
	
	//will return mapped and unmapped authorities with priority details 
	public List<AuthorityDTO>  getworkflowAuthorityMap(Long workflowConfigId)
	{
		  try
	        {
			  List<AuthorityDTO> authorityDTOs = hqlQueryManager.getworkflowAuthorityMap(workflowConfigId);
			  return authorityDTOs;
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(), e);
	            return null;
	        }
	}
	
	//will return only mapped authorites
	public List<WorkflowAuthorityMapDTO>  getWorkflowMappedAuthorities(Long workflowConfigId)
	{
		  try
	        {
			  List<WorkflowAuthorityMapDTO> authorityDTOs = hqlQueryManager.getWorkflowMappedAuthorities(workflowConfigId);
	            return authorityDTOs;
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(), e);
	            return null;
	        }
	}
	
	
	public List<StatusDTO>  getAuthorityStatusMap(Long workflowAuthorityId)
	{
		  try
	        {
	            List<StatusDTO> statusDTOs =null;
	            return statusDTOs;
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(), e);
	            return null;
	        }
		 
	}

	 public String validateFWById(Long storeId)
	    {
	        try
	        {
	            if (commonService.isWFExitsInWFConfig(storeId))
	            {
	               // return "{\"success\":true,\"message\":\"Work Flow Already Exists.\"}";
	            	return "success";
	            }
	            else
	            {
	               // return "{\"success\":true,\"message\":true}";
	            	return "fail";
	            }
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(), e);
	            return "{\"success\":false,\"message\":\"Remote Exception\"}";
	        }
	    }
}
