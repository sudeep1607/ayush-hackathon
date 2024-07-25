package com.fts.services;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.ThreadLocalData;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.dtos.EnployeeInfoDTO;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.FileCreationAuthenticationManager;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.SectionManager;
import com.fts.hibernate.managers.UserManager;
import com.fts.hibernate.models.FileCreationAuthentication;
import com.fts.hibernate.models.UserInfo;
import com.fts.utils.DateUtils;

	@Service
	public  class FileCreationAuthorizationService implements GridComponent {
		
		   private static final Log LOG = LogFactory.getLog(FileCreationAuthorizationService.class);
		  
		    @Autowired
		    private HQLQueryManager hqlQueryManager;
		    
		    @SuppressWarnings("unused")
			@Autowired 
		    private SectionManager sectionManager;
		    
		    @Autowired 
		    private FileCreationAuthenticationManager fileCreationAuthenticationManager;
		   
		    @Autowired 
		    private UserManager userManger;
		  
		  @Override
		  public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
				String... extraParams) throws Exception {
			   GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
			   long sectionId=0;
		   	String value=" ";
			 String type=" ";
			 if(extraParams[0].contains(","))
					{
						String[] str = extraParams[0].split(",");
						if(str[0]!=null && str[1]!=null &&  (str[1].equals("Section") || str[1] == "Section"))
						{
							sectionId= Long.parseLong(str[0]);
							value = String.valueOf(sectionId);
							type=str[1];
						}
						else
						{
							sectionId= Long.parseLong(str[0]);
							value = String.valueOf(sectionId);
							type=str[1];
						}
					}
				 else 
				 {
					 value="ALL";
				 }
				 List<EnployeeInfoDTO> records = hqlQueryManager.getAllUserInfoDataForAssigningFileAuthentication(extraParams[0]=="ALL"? "ALL":extraParams[0] ,value,type,limit);
		        gridPaginationDTO.setRecords(records);
		        gridPaginationDTO.setSuccess(true);
		        gridPaginationDTO.setTotal(Long.parseLong(String.valueOf(records.size())));
		        return gridPaginationDTO;
		}
		 //method to assignAuthenticationToFileCreation ---@vivek
		  public String assignAuthenticationToFileCreation(String reqIds,
		  		String reportingRemarks) {
		  	 
		  	StringBuffer responseMsg	=	new StringBuffer();
		  	try {
		  		String [] reqids	=	reqIds.trim().split(",");
		  		if(reqids.length>0){
		  			for(int i=0;i<reqids.length;i++){
		  			responseMsg.append(assignReportMangerToSelectedEmployees(reqids[i],reportingRemarks));
		  			}
		  		}
		  	} catch (Exception e) {
		  		LOG.info(e.getCause(),e);
		  		return null;
		  	}
		  	return responseMsg.toString();
		  }

		  //method to releaseAuthenticationToFileCreation ---@vivek

		  public String releaseAuthenticationToFileCreation(String reqIds,String id) {
		  	 
		  	StringBuffer responseMsg	=	new StringBuffer();
		  	try {
		  		String [] reqids   =	reqIds.trim().split(",");
		  		String [] ids      =      String.valueOf(id).trim().split(",");
		  		if(reqids.length>0){
		  			
		  			for(int i=0;i<reqids.length;i++){
		  				responseMsg.append(unAssignReportingManager(reqids[i],ids[i]));
		  			}
		  		}
		  	} catch (Exception e) {
		  		LOG.info(e.getCause(),e);
		  		return null;
		  	}
		  	return responseMsg.toString();
		  }

		  public String assignReportMangerToSelectedEmployees(String  eid, String remarks)
		  			{
		  			try 
		  			{
		  				String employeeId	=	eid;
		  					if(employeeId!=null)
		  					{
		  						FileCreationAuthentication fileCreationAuthentication = new FileCreationAuthentication();
		  						fileCreationAuthentication.setAssignRemarks(remarks);
		  						fileCreationAuthentication.setActive(1);
		  						fileCreationAuthentication.setIsFileCreationAuthentication(1);
		  						fileCreationAuthentication.setEmployeeId(fileCreationAuthenticationManager.getUserByEmployeeId(eid));
		  						UserInfo user = fileCreationAuthenticationManager.getUserByEmployeeId(eid);
		  						if(user!=null)
		  						{
		  						 UserInfo userInfo= userManger.get(user.getId());
		  						  userInfo.setIsCreateFile(1);
		  					    	user.setIsCreateFile(1);
		  					    	userManger.saveOrUpdate(userInfo);
		  						}
		  						fileCreationAuthentication.setCreatedBy(ThreadLocalData.get());
		  						fileCreationAuthentication.setCreatedon(DateUtils.getCurrentSystemTimestamp());
		  						fileCreationAuthenticationManager.saveOrUpdate(fileCreationAuthentication);
		  						return "assigned";
		  					
		  		          	}else
		  		          	{
		  		          		return "invalidemployeeId";
		  		          	}
		  			}
		  			catch (Exception e) 
		  			{
		  				LOG.info(e.getMessage(),e);
		  			 return "failure";
		  			}
		  			
		     }

		  //to un asign/release the assigned reportingManagers
		  public String unAssignReportingManager(String  eid, String id)
		  			{
		  	
		  			try 
		  			{
		  				String employeeId	=	eid;
		  				Long rprtId = Long.parseLong(id);
		  				if(employeeId==null){
		  					return "Invalid employeeId";
		  				}
		  				if(employeeId!=null && rprtId!=null )
		  				{
		  					String result = hqlQueryManager.releaseFileAuthentiction(eid,rprtId);
		  					
		  					if( result!=null && (result.equalsIgnoreCase("unassigned") || result=="unassigned"))
		  					{
		  						UserInfo user = fileCreationAuthenticationManager.getUserByEmployeeId(eid);
		  						if(user!=null)
		  						{
		  						 UserInfo userInfo= userManger.get(user.getId());
		  						  userInfo.setIsCreateFile(0);
		  						  user.setIsCreateFile(0);
		  						  userManger.saveOrUpdate(userInfo);
		  						}
		  					return "unassigned";
		  					}
		  					else
		  					{
		  						return "unable to update";
		  					}
		  				}
		  				else
		  				{
		  					return "failure";
		  				}
		  				
		  			} 
		  			catch (Exception e) 
		  			{
		  				LOG.info(e.getMessage(),e);
		  			 return "failure";
		  			}
		  			
		     }


	}


