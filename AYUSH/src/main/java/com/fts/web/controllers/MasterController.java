package com.fts.web.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fts.dtos.ComboDTO;
import com.fts.dtos.FileEntryDTO;
import com.fts.dtos.ScreensDTO;
import com.fts.dtos.SectionDTO;
import com.fts.services.AlertService;
import com.fts.services.DesignationInfoService;
import com.fts.services.FileCreationAuthorizationService;
import com.fts.services.FileEntryService;
import com.fts.services.FileTypeService;
import com.fts.services.RoleService;
import com.fts.services.SectionService;
import com.fts.services.UserInfoService;
import com.fts.services.UserSectionService;
import com.fts.workflow.services.WorkflowConfigService;

@RestController
@RequestMapping(value = "/master")
public class MasterController {

	@Autowired
	private DesignationInfoService designationInfoService;
	
	@Autowired
    private RoleService roleService;
	
	@Autowired
    private UserInfoService userInfoService;
	 
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private WorkflowConfigService workflowConfigService;
	
	@Autowired
	private AlertService alertService;
	
	@Autowired
	private FileCreationAuthorizationService fileCreationAuthorizationService;
	
	@Autowired
	private FileEntryService fileEntryService;
	

    @Value("#{pathConfig['issueUploads']}")
    private String issueUploads;
    
    @Autowired
	private FileTypeService fileTypeService;
    
    @Autowired
   	private UserSectionService userSectionService;
    
    

    @RequestMapping(value = "/validateDesignation", method = RequestMethod.POST)
	public String validateDesignation(@RequestParam("value") String value, @RequestParam("id") Long id)
	    {
	        return designationInfoService.validateDesignation(value, id);
	    } 
	 
    @RequestMapping("/screens/{roleId}")
	public List<ScreensDTO> getScreens(@PathVariable("roleId") Long roleId)
	    {
	        return roleService.getScreens(roleId);
	    }
    
    @RequestMapping("/userSections/{userId}")
   	public List<SectionDTO> getUserSections(@PathVariable("userId") Long userId)
   	    {
   	        return userSectionService.getUserSections(userId);
   	    }
    
	 
    @RequestMapping(value = "/validateRoleName", method = RequestMethod.POST)
	public String validateRoleName(@RequestParam("value") String value, @RequestParam("id") Long id)
	{
	    return roleService.validateRoleName(value, id);
	} 
    
   
    
    @RequestMapping(value = "/cancelFileEntry",method = RequestMethod.POST)
	public String cancelFileEntry(@RequestParam("id") Long id)
	{
		return fileEntryService.cancelFileEntry(id);
	}
    
    @RequestMapping(value = "/validateBuildingSection", method = RequestMethod.POST)
   	public String validateBuildingSection(@RequestParam("id") Long id,@RequestParam("value") String value)
   	    { 
    	   String[] values = value.split("&");
    	Long building= Long.parseLong(values[0]);
        String sectionName=  values[1];
       
   	        return sectionService.validateBuildingSection(id,building,sectionName);
   	    } 
    
    @RequestMapping(value = "/validateFWById", method = RequestMethod.POST)
    public String validateFWById(@RequestParam("storeId") Long storeId)
    {
        return workflowConfigService.validateFWById(storeId);
    } 
    
    @RequestMapping(value = "/validateFileType" ,method = RequestMethod.POST) 
	public String validateFileType(@RequestParam("value") String value, @RequestParam("id") Long id)
	{
	    return fileTypeService.validateFileType(value,id);
	}
    
    // to assign the user authentication for the file
    @RequestMapping(value = "/assignFileAuthorization", method = RequestMethod.POST)
    public String assignFileAuthorization(@RequestParam("reqIds") String reqIds,  @RequestParam("reportingManagerRemarks") String reportingManagerRemarks)
    {
        return fileCreationAuthorizationService.assignAuthenticationToFileCreation(reqIds, reportingManagerRemarks);
    }
    // to release the user authentication for the file
    @RequestMapping(value = "/releaseFileAuthoization", method = RequestMethod.POST)
    public String releaseFileAuthoization(@RequestParam("reqIds") String reqIds, @RequestParam("id") String id)
    {
        return fileCreationAuthorizationService.releaseAuthenticationToFileCreation(reqIds,id);
    }
    
    
    @RequestMapping(value = "/saveOpenFileWF", method = RequestMethod.POST)
    public String saveOpenFileWF(@RequestParam("fileId") Long id,@RequestParam("reportingId") Long reportingUserId,@RequestParam("remarks") String remarks, @RequestParam("actionTypeId") Long actionTypeId)
    {
        return fileEntryService.saveOpenFileWF(id,reportingUserId,remarks,actionTypeId);
    }
    
    @RequestMapping(value = "/submitFile", method = RequestMethod.POST)
    public String submitProposal(@RequestParam("id") Long id)
    {
        return fileEntryService.submitFile(id);
    }
    
    @RequestMapping(value = "/approveFile", method = RequestMethod.POST)
    public String approveFile(@RequestParam("fileId") Long id,@RequestParam("reportingId") Long reportingUserId,@RequestParam("remarks") String remarks, @RequestParam("actionTypeId") Long actionTypeId)
    {
        
    	return fileEntryService.approveFile(id,reportingUserId,remarks,actionTypeId);
    }
    
    @RequestMapping(value ="/userReportingmanagers", method = RequestMethod.GET)
    public List<ComboDTO> getUserReportingManagers(HttpServletRequest request, @RequestBody String jsonData)
    {
    	return userInfoService.getActiveUsers();
    }
    
    @RequestMapping(value = "/getFileDetailsByBarcode", method = RequestMethod.GET)
   	public List<FileEntryDTO> getFileDetailsByBarcode(@RequestParam("searchText") String searchText)
   	{
   		return fileEntryService.getFileDetailsByBarcode(searchText);
   	}
    
    @RequestMapping(value = "/getFileDetailsByBarcodeToView", method = RequestMethod.GET)
   	public List<FileEntryDTO> getFileDetailsByBarcodeToView(@RequestParam("searchText") String searchText)
   	{
   		return fileEntryService.getFileDetailsByBarcodeToView(searchText);
   	}
    
    @RequestMapping(value = "/validateFileCode", method = RequestMethod.POST)
    public String validateFileCode(@RequestParam("value") String value, @RequestParam("id") Long id)
    {
        return fileEntryService.validateFileCode(value, id);
    }
   
    @RequestMapping(value = "/uploadFileAttachmentCopy", method = RequestMethod.POST)
   	@ResponseBody
   	    public String uploadFileAttachmentCopy(@RequestBody MultipartFile attachFileName, HttpServletRequest request, HttpServletResponse response) throws Exception{
   			String result = null;
   			String fileId = request.getParameter("id");
   				 if (attachFileName != null)
   			        {
   					   result = fileEntryService.uploadIssueFiles(attachFileName,fileId);
   			         }
   				 else
   			       {
   			    	   result="Invalid File data ";
   			    	   
   			       }
   		    return "{\"success\":\"success\",\"msg\":\"" + result + "\"}";
   		}
    @RequestMapping(value = "/getNotifiAlerts", method = RequestMethod.GET)
   	public String getNotifiAlerts( )
   	{
   		return alertService.getNotificationsforPopUp();
   	} 
    
 }
