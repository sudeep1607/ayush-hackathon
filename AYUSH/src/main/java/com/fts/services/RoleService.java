package com.fts.services;

import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fts.ThreadLocalData;
import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.ServiceComponent;
import com.fts.dtos.ScreensDTO;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.RoleManager;
import com.fts.hibernate.models.Role;
import com.fts.utils.DateUtils;

@Service
public class RoleService implements GridComponent, ServiceComponent, ComboComponent
{
    private static final Log LOG  = LogFactory.getLog(RoleService.class);
    @Autowired
    private RoleManager roleManager;
    
    @Autowired
    private HQLQueryManager hqlQueryManager;
    
    @Autowired
    private CommonService commonService;
    
    @Override
    public GridPaginationDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams) throws Exception
    {
        GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
        gridPaginationDTO.setRecords(roleManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams));
        gridPaginationDTO.setTotal(roleManager.getRecordsCount(filterString, extraParams));
        return gridPaginationDTO;
    }
    @Override
    @Transactional
    public String insert(String jsonData, String... extraParams) throws Exception
    { 
        try
        {
            Role roleInfo = new ObjectMapper().readValue(jsonData, Role.class);
            LOG.info("---->"+roleInfo.getId());
            if(roleInfo != null && roleInfo.getId() != null &&  roleInfo.getId() > 0)
            {
            	Role uroleInfo = roleManager.get(roleInfo.getId());
            	if(uroleInfo!=null)
            	{
            		uroleInfo.setModifyon(DateUtils.getCurrentSystemTimestamp());
            		uroleInfo.setModifiedBy(ThreadLocalData.get());
            		uroleInfo.setDescription(roleInfo.getDescription());
            		uroleInfo.setName(roleInfo.getName());
            		uroleInfo.setActive(roleInfo.getActive());
            		uroleInfo.setScreens(roleInfo.getScreens());
            		roleManager.saveOrUpdate(uroleInfo);
            		
            	}
            	else
            	{
            		return "Failure";
            	}
            }
           else
            {
            	roleInfo.setCreatedon(DateUtils.getCurrentSystemTimestamp());
            	roleInfo.setCreatedBy(ThreadLocalData.get());
            	//maxId=roleManager.getMaxId(); 
            	//roleInfo.setId(maxId+1);
            		 
            	roleManager.saveOrUpdate(roleInfo);
            }
            return "Success";
        }
        catch (Exception e)
        {
            LOG.info("---->"+e.getStackTrace(),e);
            return "Failure";
        }
    }
    
    public List<ScreensDTO> getScreens(Long roleId)
    {
        try
        {
            List<ScreensDTO> rolesDTOs = hqlQueryManager.getRoleMappingScreens(roleId); 
            return rolesDTOs;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public String validateRoleName(String value, Long id)
    {
        try
        {
            if (commonService.isFieldValueExits(id, value, "name", "Role"))
            {
                return "{\"success\":true,\"message\":\"Role Already Exists.\"}";
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
    public List<?> getData4Combo(String... extraParams)
    {
        return roleManager.loadAll();
    }

}
