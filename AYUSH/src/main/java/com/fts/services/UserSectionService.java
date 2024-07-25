package com.fts.services;

import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.dtos.SectionDTO;
import com.fts.dtos.UserSectionDto;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.SectionManager;
import com.fts.hibernate.managers.UserManager;
import com.fts.hibernate.managers.UserSectionManager;
import com.fts.hibernate.models.UserSection;

@Service
public class UserSectionService implements GridComponent, ServiceComponent, ComboComponent{
	
	private static final Log LOG  = LogFactory.getLog(UserSectionService.class);
	
	@Autowired
	private UserSectionManager userSectionManager;
	
	@Autowired
    private CommonService commonService;
	@Autowired
    private UserManager userManager;
	@Autowired
    private HQLQueryManager hqlQueryManager;
	@Autowired
    private SectionManager sectionManager;
	
	@Override
	public List<?> getData4Combo(String... extraParams) {
		return userSectionManager.loadAll();
	}
	
	@Override
	 @Transactional
	public String insert(String jsonData, String... extraParams) throws Exception 
	{
		try
        {
			UserSectionDto userSecDto = new ObjectMapper().readValue(jsonData, UserSectionDto.class);
            if(userSecDto.getId() > 0)
            {
            	List<UserSection> userSectList = userSectionManager.getMappedSectionsByUserId(userSecDto.getId());
            	if(userSectList!=null && userSectList.size()>0)
            	{
            		for(UserSection us:userSectList)
            		{
            			userSectionManager.delete(us);
            		}
            	}
        		if(userSecDto.getSections().size()>0)
        		{
        			for(SectionDTO usd : userSecDto.getSections())
        			{
        				UserSection usrSec = new UserSection();
        				usrSec.setUser(userManager.get(userSecDto.getId()));
        				usrSec.setSection(sectionManager.get(usd.getId()));
        				userSectionManager.saveOrUpdate(usrSec);
        			}
        		}
            }
            return "Success";
        }
        catch (Exception e)
        {
            LOG.info(""+e.getStackTrace(),e);
            return "Failure";
        }
	}
	
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
			String... extraParams) throws Exception {
		GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
		 gridPaginationDTO.setRecords(userManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams));
        //gridPaginationDTO.setRecords(userSectionManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams));
       // gridPaginationDTO.setTotal(userSectionManager.getRecordsCount(filterString, extraParams));
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
	 
	 public List<SectionDTO> getUserSections(Long userId)
	    {
	        try
	        {
	            List<SectionDTO> secDTOs = hqlQueryManager.getUserSectionMappings(userId); 
	            return secDTOs;
	        }
	        catch (Exception e)
	        {
	            return null;
	        }
	    }

}
