package com.fts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.ThreadLocalData;
import com.fts.components.AutoCompleteComponent;
import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.dtos.ComboDTO;
import com.fts.dtos.FileTypeActionDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.FileTypeManager;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.models.FileType;
import com.fts.utils.DateUtils;
import com.fts.workflow.hibernate.managers.WFApprovalAuthorityMapManager;
import com.fts.workflow.hibernate.models.WFApprovalAuthorityMap;

@Service
public class FileTypeService implements GridComponent, ServiceComponent, ComboComponent,AutoCompleteComponent{
	
	private static final Log LOG  = LogFactory.getLog(FileTypeService.class);
	
	@Autowired
	private FileTypeManager fileTypeManager;
	@Autowired
	private FileEntryManager fileEntryManager;
	@Autowired
	private WFApprovalAuthorityMapManager wFApprovalAuthorityMapManager;
	
	@Autowired
	private HQLQueryManager hqlQueryManager;
	
	
	@Autowired
    private CommonService commonService;
	
	@Override
	public List<?> getData4Combo(String... extraParams) {
	    List<ComboDTO> comboDTOs = new ArrayList<ComboDTO>();
	    List<FileType> itemCategories =null;
	    if(extraParams[0]!=null)
	    {
	    	if(extraParams[0].contains(","))
	    	{
	    		String[] temp =extraParams[0].split(",");
	    			if(temp[0].equals("FILEID"))
	    			{
	    				FileEntry fe = fileEntryManager.get(Long.valueOf(temp[1]));
	    				if(fe!=null)
	    					itemCategories = fileTypeManager.getAllActiveFileTypesById(getFileActionTypeIdsUsingDescription(fe,Long.valueOf(temp[2])));
	    				else
	    					itemCategories = fileTypeManager.getAllActiveFileTypes();
	    			}
	    			else if(temp[0].equals("OPENGEN"))
	    				itemCategories = fileTypeManager.getAllActiveFileTypesForOpenTypeFiles();
	    			else
	    				itemCategories = fileTypeManager.getAllActiveFileTypes();
	    	}
	    	else
	    	{
	    		if(extraParams[0].equals("OPEN"))
	    			itemCategories = fileTypeManager.getAllActiveFileTypesForOpenTypeFiles();
	    	}
	    }
	    else
	    	itemCategories = fileTypeManager.getAllActiveFileTypes();
        
	    for (FileType obj : itemCategories)
        {
            ComboDTO comboDTO = new ComboDTO();
            comboDTO.setId(obj.getId());
            comboDTO.setName(obj.getFileTypeName());
            comboDTO.setFileTypeCode(obj.getDescription());
            comboDTOs.add(comboDTO);
        }
        return comboDTOs;
	}
	
	public String getFileActionTypeIdsUsingDescription(FileEntry fe,long priority)
	{
		WFApprovalAuthorityMap  wfaMap= wFApprovalAuthorityMapManager.getRecordByPriorityNfileId(fe,priority);
		String actTypeIds="";
				if(wfaMap!=null)
				{	
					String[] actType = wfaMap.getActionTypes().split(",");
					for(int i=0;i<actType.length;i++){
						FileType fType = fileTypeManager.getFileTypeByDescription(actType[i]);
						if(fType!=null)
							actTypeIds+=fType.getId()+",";
					}
					
				}
				if (actTypeIds != null && actTypeIds.length() > 0 && actTypeIds.charAt(actTypeIds.length() - 1) == ',') 
				{
					actTypeIds = actTypeIds.substring(0, actTypeIds.length() - 1);
			    }
				LOG.info(" ACTION TYPE IDs----->"+actTypeIds);
		return actTypeIds;
	}
	
	@Override
	public String insert(String jsonData, String... extraParams) throws Exception {
		try
        {
			FileType fileType = new ObjectMapper().readValue(jsonData, FileType.class);
            if(fileType != null && fileType.getId() != null &&  fileType.getId() > 0)
            {
            	FileType fileType2 = fileTypeManager.get(fileType.getId());
            	if(fileType2!=null)
            	{
            		fileType2.setModifyon(DateUtils.getCurrentSystemTimestamp());
            		fileType2.setModifiedBy(ThreadLocalData.get());
            		fileType2.setFileTypeName(fileType.getFileTypeName());
            		fileType2.setDescription(fileType.getDescription());
            		fileType2.setMapFileTypeActions(fileType.getMapFileTypeActions());
            		fileType2.setActive(fileType.getActive());
            		fileTypeManager.saveOrUpdate(fileType2);
            	}else
            	{
            		 return "Failure";
            	}
            }else
            {
            	fileType.setCreatedon(DateUtils.getCurrentSystemTimestamp());
            	fileType.setCreatedBy(ThreadLocalData.get());    
            	fileType.setMapFileTypeActions(fileType.getMapFileTypeActions());
            	fileTypeManager.saveOrUpdate(fileType);
            }
           
            return "Success";
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return "Failure";
        }
	}
	
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
			String... extraParams) throws Exception {
		 
		GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
        gridPaginationDTO.setRecords(fileTypeManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams));
        gridPaginationDTO.setTotal(fileTypeManager.getRecordsCount(filterString, extraParams));
        return gridPaginationDTO;
	}
	
	 public String validateFileType(String value, Long id)
	    {
	        try
	        {
	            if (commonService.isFieldValueExits(id, value, "fileTypeName", "FileType"))
	            {
	                return "{\"success\":true,\"message\":\"File Type  Already Exists.\"}";
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
	 //METHOD TO GET THE FILE ACTIPN TYPES MAPPING=====@vivek

	    @Override
		public List<FileTypeActionDTO> getData4AutoComplete(String... extraParams) {
			try 
			{ 
				return  hqlQueryManager.getActiveFileActionTypes();
			} 
			catch (Exception e) {
				LOG.info(e.getMessage(),e);
			}
			return null;
	}

}
