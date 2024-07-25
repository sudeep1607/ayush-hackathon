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
import com.fts.components.ComboComponent;
import com.fts.components.CustomComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.dtos.ComboDTO;
import com.fts.dtos.OrganizationDTO;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.OrganizationManager;
import com.fts.hibernate.managers.UserManager;
import com.fts.hibernate.models.Organization;
import com.fts.utils.DateUtils;

@Service
public class OrganizationService implements GridComponent, ServiceComponent, ComboComponent,CustomComboComponent{
	
	private static final Log LOG  = LogFactory.getLog(OrganizationService.class);
	
	@Autowired
	private OrganizationManager organizationManager;
	
	@Autowired
    private CommonService commonService;
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private HQLQueryManager hQLQueryManager;
	
	@Override
	public List<?> getData4Combo(String... extraParams) {
	    List<ComboDTO> comboDTOs = new ArrayList<ComboDTO>();
        List<Organization> organization = organizationManager.getAllActiveOrganizations();
        for (Organization obj : organization)
        {
            ComboDTO comboDTO = new ComboDTO();
            comboDTO.setId(obj.getId());
            comboDTO.setName(obj.getOrganizationName());
            comboDTO.setRefId(obj.getOrganizationIncharge().getId()+"");
            comboDTOs.add(comboDTO);
        }
        return comboDTOs;
	}
	
	@Override
	public String insert(String jsonData, String... extraParams) throws Exception {
		try
        {
			Organization organization = new ObjectMapper().readValue(jsonData, Organization.class);
            if(organization != null && organization.getId() != null &&  organization.getId() > 0)
            {
            	Organization organization2 = organizationManager.get(organization.getId());
                if(organization2!=null)
                {
                	organization2.setModifyon(DateUtils.getCurrentSystemTimestamp());
                	organization2.setModifiedBy(ThreadLocalData.get());
                	organization2.setDescription(organization.getDescription());
                	organization2.setOrganizationName(organization.getOrganizationName());
                	organization2.setActive(organization.getActive());
                	organization2.setOrganizationIncharge(userManager.get(organization.getOrganizationInchargeId()));
                	organizationManager.saveOrUpdate(organization2);
                }else
                {
                     return "Failure";
                }
            }else
            {
            	organization.setOrganizationIncharge(userManager.get(organization.getOrganizationInchargeId()));
            	organization.setCreatedon(DateUtils.getCurrentSystemTimestamp());
            	organization.setCreatedBy(ThreadLocalData.get());                
            	organizationManager.saveOrUpdate(organization);
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
		List<Organization> organizations = organizationManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams);
		List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();
		for(Organization organization : organizations)
		{
			OrganizationDTO organizationDTO = new OrganizationDTO();
			organizationDTO.setId(organization.getId());
			organizationDTO.setOrganizationName(organization.getOrganizationName());
			organizationDTO.setDescription(organization.getDescription());
			organizationDTO.setOrganizationInchargeId(organization.getOrganizationIncharge().getId());
		    String lastName = organization.getOrganizationIncharge().getLastName() != null ? organization.getOrganizationIncharge().getLastName() : "";
		    organizationDTO.setOrganizationInchargeName(organization.getOrganizationIncharge().getFirstName()+" "+lastName);
		    organizationDTO.setActive(organization.getActive());
		    organizationDTOs.add(organizationDTO);
		}
        gridPaginationDTO.setRecords(organizationDTOs);
        gridPaginationDTO.setTotal(organizationManager.getRecordsCount(filterString, extraParams));
        return gridPaginationDTO;
	}
	
	 public String validateBuilding(String value, Long id)
	    {
	        try
	        {
	            if (commonService.isFieldValueExits(id, value, "organizationName", "Organization"))
	            {
	                return "{\"success\":true,\"message\":\"organization Already Exists.\"}";
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
		public List<?> getCustomComboData(String... extraParams) 
		{
			try 
			{
				 return hQLQueryManager.getOrganizationCombo();
			}
			catch (Exception e)
			{
				LOG.info("exception e"+e.getCause(),e);
				return null;
			}
		}

}
