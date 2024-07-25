package com.fts.workflow.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.ThreadLocalData;
import com.fts.components.ServiceComponent;
import com.fts.utils.DateUtils;
import com.fts.workflow.dtos.WorkflowAuthorityMapDTO;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class WorkflowStatusConfigService implements ServiceComponent{
	
	private static final Log LOG = LogFactory.getLog(WorkflowStatusConfigService.class);
	
	@Autowired
	private WFApprovalAuthorityMapManager wfApprovalAuthorityMapManager;
	
	@Override
	public String insert(String jsonData, String... extraParams) throws Exception {
		
		String response  = "Failure";
	 	try {
	 		WorkflowAuthorityMapDTO workflowAuthorityMapDTO = new ObjectMapper().readValue(jsonData, WorkflowAuthorityMapDTO.class);
			 if(workflowAuthorityMapDTO != null && workflowAuthorityMapDTO.getId() != null &&  workflowAuthorityMapDTO.getId() > 0)
			{
				 WFApprovalAuthorityMap wfApprovalAuthorityMapObj = wfApprovalAuthorityMapManager.load(workflowAuthorityMapDTO.getId() );
				 wfApprovalAuthorityMapObj.setModifyon(DateUtils.getCurrentSystemTimestamp());
				 wfApprovalAuthorityMapObj.setModifiedBy(ThreadLocalData.get());
				 wfApprovalAuthorityMapManager.saveOrUpdate(wfApprovalAuthorityMapObj);
				 response = "Success";
			} else
			{
				 return response;
			} 
			return response;
		} catch (Exception e) {
			LOG.info(e.getMessage(),e);
			 return response;
		}
	}
	
}
