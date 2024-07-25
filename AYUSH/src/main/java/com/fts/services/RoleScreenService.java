package com.fts.services;

import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.RoleScreenManager;

@Service
public class RoleScreenService implements GridComponent, ServiceComponent, ComboComponent{
	
	private static final Log LOG  = LogFactory.getLog(SectionService.class);
	
	@Autowired
	private RoleScreenManager roleScreenManager;
	
	@Autowired
    private CommonService commonService;
	
	@Override
	public List<?> getData4Combo(String... extraParams) {
		return roleScreenManager.loadAll();
	}
	
	@Override
	public String insert(String jsonData, String... extraParams) throws Exception {
		try
        {
           
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
        gridPaginationDTO.setRecords(roleScreenManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams));
        gridPaginationDTO.setTotal(roleScreenManager.getRecordsCount(filterString, extraParams));
        return gridPaginationDTO;
	}
	
	 public String validateDesignation(String value, Long id)
	    {
	        try
	        {
	            if (commonService.isFieldValueExits(id, value, "roleId", "RoleScreen"))
	            {
	                return "{\"success\":true,\"message\":\"roleId Already Exists.\"}";
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
