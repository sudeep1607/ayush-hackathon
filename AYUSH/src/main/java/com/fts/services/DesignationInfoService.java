package com.fts.services;

import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.ThreadLocalData;
import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.DesignationInfoManager;
import com.fts.hibernate.models.DesignationInfo;
import com.fts.utils.DateUtils;

@Service
public class DesignationInfoService implements GridComponent, ServiceComponent, ComboComponent {
	
	private static final Log LOG  = LogFactory.getLog(DesignationInfoService.class);
	
	@Autowired
	private DesignationInfoManager  designationInfoManager;
	
	@Autowired
    private CommonService commonService;	

	@Override
	public List<?> getData4Combo(String... extraParams) {
		return designationInfoManager.getActiveDesignations();
	}

	@Override
	public String insert(String jsonData, String... extraParams) throws Exception {
		try
        {
            DesignationInfo designationInfo = new ObjectMapper().readValue(jsonData, DesignationInfo.class);
            if(designationInfo != null && designationInfo.getId() != null &&  designationInfo.getId() > 0)
            {
            	DesignationInfo uDesignationInfo = designationInfoManager.get(designationInfo.getId());
            	if(uDesignationInfo!=null)
            	{
            		uDesignationInfo.setModifyon(DateUtils.getCurrentSystemTimestamp());
            		uDesignationInfo.setModifiedBy(ThreadLocalData.get());
            		uDesignationInfo.setDesignationDescription(designationInfo.getDesignationDescription());
            		uDesignationInfo.setDesignationName(designationInfo.getDesignationName());
            		uDesignationInfo.setActive(designationInfo.getActive());
            		designationInfoManager.saveOrUpdate(uDesignationInfo);
            	}else
            	{
            		 return "Failure";
            	}
            }else
            {
            	designationInfo.setCreatedon(DateUtils.getCurrentSystemTimestamp());
            	designationInfo.setCreatedBy(ThreadLocalData.get());            	
            	designationInfoManager.saveOrUpdate(designationInfo);
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
        gridPaginationDTO.setRecords(designationInfoManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams));
        gridPaginationDTO.setTotal(designationInfoManager.getRecordsCount(filterString, extraParams));
        return gridPaginationDTO;
	}
	
	 public String validateDesignation(String value, Long id)
	    {
	        try
	        {
	            if (commonService.isFieldValueExits(id, value, "designationName", "DesignationInfo"))
	            {
	                return "{\"success\":true,\"message\":\"Designation Already Exists.\"}";
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

}
