package com.fts.services;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.dtos.ResponseDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.managers.CommonManager;
import com.fts.utils.DateUtils;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class CommonService
{
    private static final Log LOG = LogFactory.getLog(CommonService.class);

    @Autowired
    private FileEntryManager fileEntryManager;
    @Autowired
    private FileStatusReportService fileStatusReportService;
    @Autowired
    private CommonManager commonManager;
  
    @Autowired
    private Configuration freemarkerMailConfiguration;

 
    public static ResponseDTO getResponseObj(int statusCode, String message, Object object)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusCode(statusCode);
        responseDTO.setMessage(message);
        responseDTO.setObject(object);
        return responseDTO;
    }

    public boolean isFieldValueExits(Long id, String value, String field, String entityName)
    {
        return commonManager.isFieldValueExits(id, value, field, entityName);
    }
    public boolean isFieldValueExitsbystore(Long id, String value, String field, String entityName,Long storeId)
    {
        return commonManager.isFieldValueExitsbystore(id, value, field, entityName,storeId);
    }
    public String getSOSRefId(Long sosRequestId)
    {
        return "SOS" + DateUtils.getCurrentDateAsString("dd-MM-yyyy").replace("-", "") + sosRequestId;
    }

    public String getTrackMeRequestId(Long id)
    {
        return "TRC" + DateUtils.getCurrentDateAsString("dd-MM-yyyy").replace("-", "") + id;
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public StringBuffer getSelectedFileProcessFlow(String selecedFileIds)
    {
        try
        {
        	List<FileEntry> feList =null;
        	if(selecedFileIds!=null)
        	{
        		feList = fileEntryManager.getAllActiveSelectedFileForProcessFlowView(selecedFileIds);
        	}
        	
        	Map hMap = new HashMap();
        	
			hMap.put("fileList", feList);
			hMap.put("filesDtoList", fileStatusReportService.manipulateValues(feList));
			hMap.put("waterMarkImage","FTS");
			StringBuffer html = new StringBuffer();
			SimpleHash hash = new SimpleHash();
			hash.put("model", hMap);
			html.append(FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerMailConfiguration.getTemplate("fileProcessFlow.ftl"), hash));
			return html;
		}
		catch (Exception e)
		{
			LOG.error(e.getCause(), e);
			return null;
		}
    }
	public boolean isFieldValueExitsTwice(Long sectionId,Long building,String sectionName, String field, String entityName)
    {
        return commonManager.isFieldValueExitsTwice(sectionId, building, sectionName, field,entityName);
    }

	public boolean isWFExitsInWFConfig(Long storeId) {
		 return commonManager.isWFExists(storeId);
	}
}
