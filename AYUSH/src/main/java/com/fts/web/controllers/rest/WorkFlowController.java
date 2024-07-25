package com.fts.web.controllers.rest;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fts.workflow.dtos.AuthorityDTO;
import com.fts.workflow.dtos.StatusDTO;
import com.fts.workflow.dtos.WorkflowAuthorityMapDTO;
import com.fts.workflow.services.WorkflowConfigService;

@RestController
@RequestMapping(value ="/workflow")
public class WorkFlowController {

	@SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(WorkFlowController.class);
	@Autowired
	private WorkflowConfigService workflowConfigService;
	
	@RequestMapping(value = "/validateWorkFlowNames", method = RequestMethod.POST)
    public String validateWorkFlowNames(@RequestParam("value") String value, @RequestParam("id") Long id){
    	return workflowConfigService.validateWorkFlowNames(value, id);
    }
	
	
	@RequestMapping("/getAuthorityStatusMap/{workflowAuthorityId}")
    public List<StatusDTO> getAuthorityStatusMap(@PathVariable("workflowAuthorityId") Long workflowAuthorityId)
    {
        return workflowConfigService.getAuthorityStatusMap(workflowAuthorityId);
    }
	
	
	@RequestMapping("/getworkflowAuthorityMap/{workflowConfigId}")
    public List<AuthorityDTO> getworkflowAuthorityMap(@PathVariable("workflowConfigId") Long workflowConfigId)
    {
        return workflowConfigService.getworkflowAuthorityMap(workflowConfigId);
    }
	
	@RequestMapping("/getWorkflowMappedAuthorities/{workflowConfigId}")
    public List<WorkflowAuthorityMapDTO> getWorkflowMappedAuthorities(@PathVariable("workflowConfigId") Long workflowConfigId)
    {
        return workflowConfigService.getWorkflowMappedAuthorities(workflowConfigId);
    }
	
   
	
}
