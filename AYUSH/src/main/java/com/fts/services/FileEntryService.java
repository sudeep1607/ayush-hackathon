package com.fts.services;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fts.Constants;
import com.fts.ThreadLocalData;
import com.fts.components.AppContext;
import com.fts.components.ComboComponent;
import com.fts.components.CustomComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.MailTemplateService;
import com.fts.components.ServiceComponent;
import com.fts.dtos.ComboDTO;
import com.fts.dtos.FileEntryDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.FileTypeManager;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.SectionManager;
import com.fts.hibernate.managers.UserManager;
import com.fts.hibernate.models.UserInfo;
import com.fts.utils.DateUtils;
import com.fts.utils.FileUtils;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class FileEntryService implements GridComponent, ServiceComponent, ComboComponent,CustomComboComponent{
	
	private static final Log LOG  = LogFactory.getLog(FileEntryService.class);
	
	@Autowired
	private FileEntryManager fileEntryManager;
	
	@Autowired
	private SectionManager sectionManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private FileTypeManager fileTypeManager;
	@Autowired
	private WFApprovalAuthorityMapManager wFApprovalAuthorityMapManager;
	@Autowired
	private BarCodeService barCodeService;
	@Autowired
	private HQLQueryManager hQLQueryManager;
	@Autowired
	private FileStatusReportService fileStatusReportService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private Properties pathConfig;
	@Override
	public List<?> getData4Combo(String... extraParams) {
		return fileEntryManager.loadAll();
	}
	
    @Override
    @Transactional
	public String insert(String jsonData, String... extraParams) throws Exception {
	    try
        {
	    	FileEntryDTO fileEntrydto  = new ObjectMapper().readValue(jsonData, FileEntryDTO.class);
	    	//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    	if(fileEntrydto != null && fileEntrydto.getId() != null &&  fileEntrydto.getId() > 0)
            {
            	FileEntry fileEntry2 = fileEntryManager.get(fileEntrydto.getId());
                if(fileEntry2!=null)
                {
                	fileEntry2.setModifyon(DateUtils.getCurrentSystemTimestamp());
                	fileEntry2.setModifiedBy(ThreadLocalData.get());
                	
                	fileEntry2.setFileBarcode(fileEntrydto.getFileBarcode());
                	fileEntry2.setFileComments(fileEntrydto.getFileComments());
                	fileEntry2.setFileCreatedDate(fileEntrydto.getFileCreatedDate());
                	fileEntry2.setFileDetails(fileEntrydto.getFileDetails());
                	fileEntry2.setFileName(fileEntrydto.getFileName());
                	fileEntry2.setFileNo(fileEntrydto.getFileNo());
                	fileEntry2.setFilePriority(fileEntrydto.getFilePriority());
                	fileEntry2.setFileSubject(fileEntrydto.getFileSubject());
                	fileEntry2.setFileType(fileEntrydto.getFileType());
                	fileEntry2.setSection(sectionManager.get(fileEntrydto.getSection()));
                	fileEntry2.setFileInitiator(userManager.get(fileEntrydto.getFileInitiator()));
                	fileEntry2.setFileStatus(fileTypeManager.get(Constants.FileStatus.DRAFT));
                	fileEntry2.setFileCode(fileEntrydto.getFileCode());
                	fileEntryManager.saveOrUpdate(fileEntry2);
                }else
                {
                     return "Failure";
                }
            }else
            {
            	FileEntry fileEntry = new FileEntry();
            	fileEntry.setActive(1);    
            	fileEntry.setCreatedon(DateUtils.getCurrentSystemTimestamp());
            	fileEntry.setCreatedBy(ThreadLocalData.get());  
            	fileEntry.setFileComments(fileEntrydto.getFileComments());
            	fileEntry.setFileCreatedDate(fileEntrydto.getFileCreatedDate());
            	fileEntry.setFileDetails(fileEntrydto.getFileDetails());
            	fileEntry.setFileName(fileEntrydto.getFileName());
            	fileEntry.setFilePriority(fileEntrydto.getFilePriority());
            	fileEntry.setFileSubject(fileEntrydto.getFileSubject());
            	fileEntry.setFileType(fileEntrydto.getFileType());
            	fileEntry.setSection(sectionManager.get(fileEntrydto.getSection()));
            	fileEntry.setFileInitiator(userManager.get(fileEntrydto.getFileInitiator()));
            	fileEntry.setFileStatus(fileTypeManager.get(Constants.FileStatus.DRAFT));
            	
            	String fno = generateUniqueFileNo(fileEntrydto);
            	fileEntry.setFileNo(fno);
            	String[] temp = fno.split("/");
            	String str = fileEntrydto.getFileName().substring(0, 3).toUpperCase()+sectionManager.get(fileEntrydto.getSection()).getSectionName().substring(0, 2).toUpperCase()+temp[1];
            	LOG.info("XXXXXXXXXXX----------->"+str);
            	String brCode = barCodeService.genrateBarCode(str);
            	fileEntry.setFileBarcode(brCode);
            	fileEntry.setFileCode(fileEntrydto.getFileCode());
            	fileEntryManager.saveOrUpdate(fileEntry);
            	
            	LOG.info("THE  FILEENTRY ID IS==="+fileEntry.getId());
            }
	    	
            return "Success";
        }  
        catch (Exception e)
        {
            LOG.info("3333333-+"+e.getStackTrace(),e);
            return "Failure";
        }
	}
	
    public String generateUniqueFileNo(FileEntryDTO fileEntrydto)
    {
    	
    	try{
    			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    			//SimpleDateFormat fileDateFormat = new SimpleDateFormat("dd-MMM-yy");
    	        String fileNumber = "";
				int num =1;
				 DecimalFormat df = new DecimalFormat("00000");
				//long maxFileNo = fileEntryManager.getMaxFileNo();
				 long maxFileNo = hQLQueryManager.getMaxFileNo();
				LOG.info("999999------->"+maxFileNo);
				if(maxFileNo>0)
				{
					fileNumber = "FTS/"+String.valueOf(df.format(maxFileNo+1));
				}
				else
				{
					fileNumber = "FTS/"+String.valueOf(df.format(num));
				}
    			return fileNumber;
    	}catch(Exception ex){
    			LOG.info("exception while generating sib no-----"+ex.getCause(),ex);
    			 return null;
    		}
    }
    
    
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
			String... extraParams) throws Exception {
		 
		GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
		List<FileEntry> fileEntrys = fileEntryManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams);
		List<FileEntryDTO> fileEntryDTOs = new ArrayList<FileEntryDTO>();
		for(FileEntry fileEntry : fileEntrys)
		{
			FileEntryDTO fileEntryDTO = new FileEntryDTO();
			long wfCount =0;
			fileEntryDTO.setId(fileEntry.getId());
			fileEntryDTO.setFileBarcode(fileEntry.getFileBarcode());
			fileEntryDTO.setFileComments(fileEntry.getFileComments());
			fileEntryDTO.setFileCreatedDate(fileEntry.getFileCreatedDate());
			fileEntryDTO.setFileDetails(fileEntry.getFileDetails());
			fileEntryDTO.setFileName(fileEntry.getFileName());
			fileEntryDTO.setFileNo(fileEntry.getFileNo());
			fileEntryDTO.setFilePriority(fileEntry.getFilePriority());
			fileEntryDTO.setFileSubject(fileEntry.getFileSubject());
			fileEntryDTO.setFileType(fileEntry.getFileType());
			wfCount = wFApprovalAuthorityMapManager.getMaxPriorityRecordByFileId(fileEntry.getId());
			if(wfCount>0)
				fileEntryDTO.setIsWorkFlowCreated(1);
			else
				fileEntryDTO.setIsWorkFlowCreated(0);
			
			fileEntryDTO.setSectionName(fileEntry.getSection().getSectionName());
			fileEntryDTO.setSection(fileEntry.getSection().getId());
			fileEntryDTO.setInitiatorName(fileEntry.getFileInitiator()!=null ?(fileEntry.getFileInitiator().getFirstName()+" "+fileEntry.getFileInitiator().getLastName()):"");
			fileEntryDTO.setFileInitiator(fileEntry.getFileInitiator().getId());
			fileEntryDTO.setDesignation(fileEntry.getFileInitiator()!=null ?(fileEntry.getFileInitiator().getDesignationInfo().getId()):0);
			fileEntryDTO.setFileStatus(fileEntry.getFileStatus()!=null ? fileEntry.getFileStatus().getFileTypeName() : "");
			fileEntryDTO.setAttachFilePath(fileEntry.getAttachFilePath());
			fileEntryDTO.setFileCode(fileEntry.getFileCode());
			fileEntryDTOs.add(fileEntryDTO);
		}
        gridPaginationDTO.setRecords(fileEntryDTOs);
        gridPaginationDTO.setTotal(fileEntryManager.getRecordsCount(filterString, extraParams));
        return gridPaginationDTO;
	}

	
	public String saveOpenFileWF(long fileId,long reportingUserId,String remarks,long actionTypeId)
	{
			try 
			{
				WFApprovalAuthorityMap wf = new WFApprovalAuthorityMap();
				long count=0;
				count = wFApprovalAuthorityMapManager.getMaxPriorityRecordByFileId(fileId);
				wf.setPriority(count+1);
				wf.setActive(1);
				if(reportingUserId!=0)
				wf.setApprovalAuthority(userManager.get(reportingUserId));
				wf.setCreatedon(DateUtils.getCurrentSystemTimestamp());
            	wf.setCreatedBy(ThreadLocalData.get());  
				wf.setFileEntry(fileEntryManager.get(fileId));
				wf.setFileStatus(fileTypeManager.get(Constants.FileStatus.PENDING));
				wf.setRemarks(remarks);
				wFApprovalAuthorityMapManager.saveOrUpdate(wf);
				return "Success";
		} catch (Exception e) {
			LOG.info("Exception while submitting the file :"+e.getMessage(),e);
			return "Unable to process the request";
		}
	}
	
	public String submitFile(long fileId)
	{
			try 
			{
				FileEntry fe = fileEntryManager.get(fileId);
				fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.SUBMITTED));
				fileEntryManager.saveOrUpdate(fe);
				
				List<WFApprovalAuthorityMap> wfaList = wFApprovalAuthorityMapManager.getApprovalAuthoritiesByWFConfig(fe);
				sendMailForPasswordChange(fe, wfaList);
				
				return "Success";
		} catch (Exception e) {
			LOG.info("Exception while submitting the file :"+e.getMessage(),e);
			return "Unable to process the request";
		}
	}
	 public void sendMailForPasswordChange(FileEntry fe, List<WFApprovalAuthorityMap> wfaList)
	    {
			String mailSubject = fe.getFileCode()+" File is Assigned to You";
	        StringBuilder mailContent = new StringBuilder();
	        mailContent.append("Dear Sir/Madam,<br><br>");
	        mailContent.append(" The "+fe.getFileName()+" is Assigned for forther process. To Check the file details please login to FTS web application <br><br/> ");
	        mailContent.append(fe.getFileInitiator().getFirstName()+ " "+fe.getFileInitiator().getLastName()+"<br>");
	        mailContent.append(fe.getFileInitiator().getDesignationInfo().getDesignationName());
	        String fromAddress = fe.getFileInitiator().getEmailId() ;
	        String[] toAdd = new String[wfaList.size()];
	        for(int i=0;i<wfaList.size();i++)
	        {
	        	toAdd[i]=wfaList.get(i).getApprovalAuthority().getEmailId();
	        }
	        MailTemplateService mailTemplateService = AppContext.getApplicationContext().getBean(MailTemplateService.class);
	        mailTemplateService.setValues(fromAddress, toAdd, new String[] {}, mailSubject, mailContent.toString(), null, null);
	        mailTemplateService.start();
	    }
	public String approveFile(long fileId,long reportingUserId,String remarks,long actionTypeId)
	{
			try 
			{
				long count=0;
				WFApprovalAuthorityMap wf = wFApprovalAuthorityMapManager.getPendingFileByFileId(fileId);
				WFApprovalAuthorityMap nextPriorityFile = null;
				WFApprovalAuthorityMap previousPriorityFile = null;
				WFApprovalAuthorityMap newWf =null;
				FileEntry fe=null;
				if(wf!=null)
				{
					nextPriorityFile = wFApprovalAuthorityMapManager.getNextPendingFileByFileId(fileId,wf.getPriority());
					previousPriorityFile = wFApprovalAuthorityMapManager.getPreviousPriorityFileByFileId(fileId,wf.getPriority());
					
					if(wf.getFileEntry().getFileType().equals("SECURE"))
					{
						
							wf.setFileStatus(fileTypeManager.get(actionTypeId));
							wf.setModifyon(DateUtils.getCurrentSystemTimestamp());
		                	wf.setModifiedBy(ThreadLocalData.get());
		                	wf.setRemarks(remarks);
		                	wf.setFileStatus(fileTypeManager.get(actionTypeId));
		                	wf.setFileOutTime(DateUtils.getCurrentSystemTimestamp());
							wFApprovalAuthorityMapManager.saveOrUpdate(wf);
							
							if(actionTypeId==13)
							{
								if(nextPriorityFile!=null)
								{
									nextPriorityFile.setFileStatus(fileTypeManager.get(Constants.FileStatus.PENDING));
									wFApprovalAuthorityMapManager.saveOrUpdate(nextPriorityFile);
									
									wf.setFileStatus(fileTypeManager.get(Constants.FileStatus.APPROVED));
									wFApprovalAuthorityMapManager.saveOrUpdate(wf);
									
									fe = fileEntryManager.get(fileId);
									fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.UNDERPROCESS));
									fileEntryManager.saveOrUpdate(fe);
								}
								else
								{
									fe = fileEntryManager.get(fileId);
									fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.CLOSED));
									fileEntryManager.saveOrUpdate(fe);
									
									wf.setFileStatus(fileTypeManager.get(Constants.FileStatus.CLOSED));
									wFApprovalAuthorityMapManager.saveOrUpdate(wf);
								}
							}
							else if(actionTypeId ==7)
							{
								if(previousPriorityFile!=null)
								{
									 newWf = new WFApprovalAuthorityMap();
										newWf.setActive(1);
										newWf.setCreatedon(DateUtils.getCurrentSystemTimestamp());
										newWf.setCreatedBy(ThreadLocalData.get());  
										newWf.setFileEntry(fileEntryManager.get(fileId));
										newWf.setPriority(previousPriorityFile.getPriority());
										newWf.setApprovalAuthority(previousPriorityFile.getApprovalAuthority());
										newWf.setFileStatus(fileTypeManager.get(Constants.FileStatus.PENDING));
									wFApprovalAuthorityMapManager.saveOrUpdate(newWf);
								}
								else{
									fe = fileEntryManager.get(fileId);
									fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.REVERT));
									fileEntryManager.saveOrUpdate(fe);
								}
							}
							else if(actionTypeId ==8)
							{
								fe = fileEntryManager.get(fileId);
								fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.REJECTED));
								fileEntryManager.saveOrUpdate(fe);
								
								wf.setFileStatus(fileTypeManager.get(Constants.FileStatus.REJECTED));
								wFApprovalAuthorityMapManager.saveOrUpdate(wf);
							}
							else if(actionTypeId==9)
		                	{
		                		fe = fileEntryManager.get(fileId);
								fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.CLOSED));
								fileEntryManager.saveOrUpdate(fe);
								
								wf.setFileStatus(fileTypeManager.get(Constants.FileStatus.CLOSED));
								wFApprovalAuthorityMapManager.saveOrUpdate(wf);
		                	}
							else
								return "This Action is not implemented";
					}
					else
					{
						if(actionTypeId==3 || actionTypeId==4)
						{
							wf.setRemarks(remarks);
							wf.setFileStatus(fileTypeManager.get(actionTypeId));
							wf.setFileOutTime(DateUtils.getCurrentSystemTimestamp());
							wFApprovalAuthorityMapManager.saveOrUpdate(wf);
							
							newWf = new WFApprovalAuthorityMap();
								count = wFApprovalAuthorityMapManager.getMaxPriorityRecordByFileId(fileId);
								newWf.setPriority(count+1);
								newWf.setActive(1);
								if(reportingUserId!=0)
									newWf.setApprovalAuthority(userManager.get(reportingUserId));
								newWf.setCreatedon(DateUtils.getCurrentSystemTimestamp());
								newWf.setCreatedBy(ThreadLocalData.get());  
								newWf.setFileEntry(fileEntryManager.get(fileId));
								newWf.setFileStatus(fileTypeManager.get(Constants.FileStatus.PENDING));
							wFApprovalAuthorityMapManager.saveOrUpdate(newWf);
							
							fe = fileEntryManager.get(fileId);
							fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.UNDERPROCESS));
							fileEntryManager.saveOrUpdate(fe);
						}
						else
						{
							wf.setFileStatus(fileTypeManager.get(actionTypeId));
							wf.setModifyon(DateUtils.getCurrentSystemTimestamp());
		                	wf.setModifiedBy(ThreadLocalData.get());
		                	wf.setRemarks(remarks);

		                	if(actionTypeId ==7)
							{
								if(previousPriorityFile!=null)
								{
									newWf = new WFApprovalAuthorityMap();
										newWf.setActive(1);
										newWf.setCreatedon(DateUtils.getCurrentSystemTimestamp());
										newWf.setCreatedBy(ThreadLocalData.get());  
										newWf.setFileEntry(fileEntryManager.get(fileId));
										newWf.setPriority(previousPriorityFile.getPriority());
										newWf.setApprovalAuthority(previousPriorityFile.getApprovalAuthority());
										newWf.setFileStatus(fileTypeManager.get(Constants.FileStatus.PENDING));
									wFApprovalAuthorityMapManager.saveOrUpdate(newWf);
								}
								else{
									fe = fileEntryManager.get(fileId);
									fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.REVERT));
									fileEntryManager.saveOrUpdate(fe);
								}
								
								fe = fileEntryManager.get(fileId);
								fe.setFileStatus(fileTypeManager.get(actionTypeId));
								fileEntryManager.saveOrUpdate(fe);
							}
							else if(actionTypeId ==8)
							{
								fe = fileEntryManager.get(fileId);
								fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.REJECT));
								fileEntryManager.saveOrUpdate(fe);
							}
							else if(actionTypeId ==13)
							{
								wf.setFileStatus(fileTypeManager.get(Constants.FileStatus.APPROVED));
		                		fe = fileEntryManager.get(fileId);
								fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.APPROVED));
								fileEntryManager.saveOrUpdate(fe);
							}
							else if(actionTypeId==9)
		                	{
		                		wf.setFileStatus(fileTypeManager.get(Constants.FileStatus.CLOSED));
		                		fe = fileEntryManager.get(fileId);
								fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.CLOSED));
								fileEntryManager.saveOrUpdate(fe);
		                	}
		                	else
		                		wf.setFileStatus(fileTypeManager.get(actionTypeId));
		                	
		                	wf.setFileOutTime(DateUtils.getCurrentSystemTimestamp());
							wFApprovalAuthorityMapManager.saveOrUpdate(wf);
						}
						
					}
					return "Success";
				}
				else
					return "Unable to process the request";
		} catch (Exception e) {
			LOG.info("Exception while submitting the file :"+e.getMessage(),e);
			return "Unable to process the request";
		}
	}

	public List<FileEntryDTO> getFileDetailsByBarcode(String searchText) {
	
		 try {
			List<FileEntryDTO>  fileEntryDTOList = new ArrayList<FileEntryDTO>();
			 if(!"".equalsIgnoreCase(searchText))
			 {
				 List<WFApprovalAuthorityMap>  wfList = fileEntryManager.getFileDetailsByBarcode(searchText);
				 WFApprovalAuthorityMap  wfAppMap = null;
				 if(wfList!=null)
				 {
				   for(WFApprovalAuthorityMap wf : wfList)
				   {
					   FileEntryDTO feDto = new FileEntryDTO();
						   feDto.setId(wf.getId());
						   feDto.setFileId(wf.getFileEntry().getId());
						   feDto.setFileName(wf.getFileEntry().getFileName());
						   feDto.setFileBarcode(wf.getFileEntry().getFileBarcode());
						   feDto.setFileComments(wf.getFileEntry().getFileComments());
						   feDto.setFileCreatedDate(wf.getFileEntry().getFileCreatedDate());
						   feDto.setFileDetails(wf.getFileEntry().getFileDetails());
						   feDto.setFileInitiatorName(wf.getFileEntry().getFileInitiator().getFirstName()+""+wf.getFileEntry().getFileInitiator().getLastName());
						   feDto.setFilePriority(wf.getFileEntry().getFilePriority());
						   feDto.setFileStatus(wf.getFileEntry().getFileStatus().getFileTypeName());
						   feDto.setFileSubject(wf.getFileEntry().getFileSubject());
						   feDto.setFileType(wf.getFileEntry().getFileType());
						   feDto.setSectionName(wf.getFileEntry().getSection().getSectionName());
						   feDto.setPriority(wf.getPriority());
						   wfAppMap =wFApprovalAuthorityMapManager.getFileToUpdateInTime(wf.getFileEntry().getId());
						   if(wfAppMap!=null)
						   {
							   wfAppMap.setFileInTime(DateUtils.getCurrentSystemTimestamp());
							   wFApprovalAuthorityMapManager.saveOrUpdate(wfAppMap);
						   }
						fileEntryDTOList.add(feDto);
				   }
				}
			 }
			return fileEntryDTOList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<FileEntryDTO> getFileDetailsByBarcodeToView(String searchText) {
	try {
			List<FileEntryDTO>  fileEntryDTOList = new ArrayList<FileEntryDTO>();
			 if(!"".equalsIgnoreCase(searchText))
			 {
				 //List<WFApprovalAuthorityMap>  wfList = fileEntryManager.getFileDetailsByBarcodeToView(searchText);
				 List<FileEntry> feList = fileEntryManager.getFileDetailsByBarcodeToView(searchText);
				 fileEntryDTOList = fileStatusReportService.manipulateValues(feList);
				 /*if(wfList!=null)
				 {
				   for(WFApprovalAuthorityMap wf : wfList)
				   {
					   FileEntryDTO feDto = new FileEntryDTO();
						   feDto.setId(wf.getId());
						   feDto.setFileId(wf.getFileEntry().getId());
						   feDto.setFileName(wf.getFileEntry().getFileName());
						   feDto.setFileBarcode(wf.getFileEntry().getFileBarcode());
						   feDto.setFileComments(wf.getFileEntry().getFileComments());
						   feDto.setFileCreatedDate(wf.getFileEntry().getFileCreatedDate());
						   feDto.setFileDetails(wf.getFileEntry().getFileDetails());
						   feDto.setFileInitiatorName(wf.getFileEntry().getFileInitiator().getFirstName()+""+wf.getFileEntry().getFileInitiator().getLastName());
						   feDto.setFilePriority(wf.getFileEntry().getFilePriority());
						   feDto.setFileStatus(wf.getFileEntry().getFileStatus().getFileTypeName());
						   feDto.setFileSubject(wf.getFileEntry().getFileSubject());
						   feDto.setFileType(wf.getFileEntry().getFileType());
						   feDto.setSectionName(wf.getFileEntry().getSection().getSectionName());
						   feDto.setPriority(wf.getPriority());
						fileEntryDTOList.add(feDto);
				   }
				}*/
			 }
			return fileEntryDTOList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String uploadIssueFiles(MultipartFile itemFile , String id){
		
		try{
			LOG.info("KKKKKKKKKKKK-------------->"+Long.parseLong(id));
			FileEntry fe = fileEntryManager.get(Long.parseLong(id));
			if(fe != null)
			{
				 String itemRequestFilePath = getFilePath(itemFile);
				 fe.setAttachFilePath(itemRequestFilePath);
				 fileEntryManager.saveOrUpdate(fe);
			}
		        return "Success";
			}catch(Exception ex){
				LOG.info(ex.getCause(),ex);
				 return null;
			}
	  }
	
	public String getFilePath(MultipartFile multipartFile){
		try{
		String tempPath = null;
		String filePath = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	Date date = new Date();
    	LOG.info("multipartFilemultipartFilemultipartFile======="+multipartFile);
		 if (multipartFile != null && multipartFile.getSize() > 0)
         {
		     String fileNameWithOutExt = FilenameUtils.removeExtension(multipartFile.getOriginalFilename())+"_"+dateFormat.format(date);
		     String fileNameExt = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		     boolean directoryExist = FileUtils.createDirectory(pathConfig.getProperty("uploadAttachment"));
		     if(!directoryExist)
		     {
		    	 
		    	 return "failure";
		     }
		     else
		     {
		     tempPath = pathConfig.getProperty("uploadAttachment") + "/"+ fileNameWithOutExt+"."+fileNameExt;
		     filePath = pathConfig.getProperty("applicationURL") + pathConfig.getProperty("retrieveFileAttachments") + "/"+ fileNameWithOutExt+"."+fileNameExt;
	         LOG.info("tempPath --- : " + tempPath);
	         LOG.info("Server filePath--- : " + filePath);
	         FileOutputStream fos = new FileOutputStream(new File(tempPath));
             fos.write(multipartFile.getBytes());
             fos.close();
			 }
         }
	        return filePath;
		}catch(Exception ex){
			LOG.info(ex.getCause(),ex);
			 return null;
		}
	}
	
	public String cancelFileEntry(Long id) 
	{
		try{
				FileEntry fe	=	fileEntryManager.get(id);
				if(fe != null){
					fe.setActive(0);
					fe.setFileStatus(fileTypeManager.get(Constants.FileStatus.CANCELLED));
					fileEntryManager.saveOrUpdate(fe);
					return "success";
				}
			}
			catch(Exception e)
			{
				LOG.info(e.getCause());
				return null;
			}
			
			return null;
	}

	public String validateFileCode(String value, Long id)
    {
        try
        {
            if (commonService.isFieldValueExits(id, value, "fileCode", "FileEntry"))
            {
                return "{\"success\":true,\"message\":\"File Code Already Exists.\"}";
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

	@Override
	public List<ComboDTO> getCustomComboData(String... extraParams) {
     
		try{
    	  List<UserInfo>   userInfosList= null;
    	  List<ComboDTO> comboDTOs = new ArrayList<ComboDTO>();
			if(extraParams!=null && extraParams[0]!=null && extraParams[0].contains(","))   
	        {
	        		          String[] secAndDesigId = extraParams[0].split(",");
	        		          LOG.info("secAndDesigIdsecAndDesigId while getting data===="+extraParams[0]);
	        		          userInfosList =  userManager.getActiveUsersBySectionNdesig(Long.parseLong(secAndDesigId[0]), Long.parseLong(secAndDesigId[1]));
	           if(userInfosList!=null && userInfosList.size()>0)
	           {
	        	   for(UserInfo fileEntry : userInfosList )
	        	   {
	        		   ComboDTO comboDTO = new ComboDTO();
	        		    comboDTO.setName(fileEntry.getLastName()!=null ?fileEntry.getFirstName()+" "+fileEntry.getLastName():fileEntry.getFirstName());
	        	        comboDTO.setId(fileEntry.getId());
	        	        comboDTOs.add(comboDTO);
	        	   }
	           }
	        		          
	        }
			 return comboDTOs;
      }
      catch(Exception e)
      {
    	  LOG.info("exceprion ssswhile getting data===="+e.getMessage());
    	  e.printStackTrace();
    	  return null;
      }
	
		
	}
}

