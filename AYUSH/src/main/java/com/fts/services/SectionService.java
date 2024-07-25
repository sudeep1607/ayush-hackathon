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
import com.fts.components.CustomComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.dtos.ComboDTO;
import com.fts.dtos.SectionDTO;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.SectionManager;
import com.fts.hibernate.managers.UserManager;
import com.fts.hibernate.models.Section;
import com.fts.utils.DateUtils;

@Service
public class SectionService implements GridComponent, ServiceComponent, ComboComponent,CustomComboComponent,AutoCompleteComponent{
	
	private static final Log LOG  = LogFactory.getLog(SectionService.class);
	
	@Autowired
	private SectionManager sectionManager;
	
	@Autowired
    private CommonService commonService;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private HQLQueryManager hQLQueryManager;
	
	@Override
	public List<?> getData4Combo(String... extraParams) {
		//return sectionManager.getActiveSections();
		
		List<ComboDTO> comboDTOs = new ArrayList<ComboDTO>();
        List<Section> sections = sectionManager.getAllActiveSections();
        for (Section obj : sections)
        {
            ComboDTO comboDTO = new ComboDTO();
            comboDTO.setId(obj.getId());
            comboDTO.setSectionName(obj.getSectionName());
            comboDTO.setRefId(obj.getSectionIncharge().getId()+"");
            comboDTOs.add(comboDTO);
        }
        return comboDTOs;
	}
	
	@Override
    public String insert(String jsonData, String... extraParams) throws Exception {
        try
        {
            Section section  = new ObjectMapper().readValue(jsonData, Section.class);
            if(section != null && section.getId() != null &&  section.getId() > 0)
            {
                Section section2 = sectionManager.get(section.getId());
                if(section2!=null)
                {
                    section2.setModifyon(DateUtils.getCurrentSystemTimestamp());
                    section2.setModifiedBy(ThreadLocalData.get());
                    section2.setDescription(section.getDescription());
                    section2.setSectionIncharge(userManager.get(section.getSectionInchargeId()));
                    section2.setSectionName(section.getSectionName());
                    section2.setActive(section.getActive());
                    sectionManager.saveOrUpdate(section2);
                }else
                {
                     return "Failure";
                }
            }else
            {
                section.setSectionIncharge(userManager.get(section.getSectionInchargeId()));
                section.setCreatedon(DateUtils.getCurrentSystemTimestamp());
                section.setCreatedBy(ThreadLocalData.get());                
                sectionManager.saveOrUpdate(section);
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
        List<Section> sections = sectionManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams);
        List<SectionDTO> sectionDTOs = new ArrayList<SectionDTO>();
        
        for(Section section : sections)
        {
            SectionDTO sectionDTO = new SectionDTO();
            sectionDTO.setId(section.getId());
            sectionDTO.setSectionName(section.getSectionName());
            sectionDTO.setDescription(section.getDescription());
            sectionDTO.setSectionInchargeId(section.getSectionIncharge().getId());
            String lastName = section.getSectionIncharge().getLastName() != null ? section.getSectionIncharge().getLastName() : "";
            sectionDTO.setSectionInchargeName(section.getSectionIncharge().getFirstName()+" "+lastName);
            sectionDTO.setActive(section.getActive());
            sectionDTOs.add(sectionDTO);
        }
        gridPaginationDTO.setRecords(sectionDTOs);
        gridPaginationDTO.setTotal(sectionManager.getRecordsCount(filterString, extraParams));
        return gridPaginationDTO;
	}
	
	 public String validateSection(String value, Long id)
	    {
	        try
	        {
	            if (commonService.isFieldValueExits(id, value, "sectionName", "Section"))
	            {
	                return "{\"success\":true,\"message\":\"sectionName Already Exists.\"}";
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
			     /*if("store".equalsIgnoreCase(extraParams[0]) && extraParams[0] != null){
			    	 return hQLQueryManager.getSectionBuilding();
			     }else{
			    	 String bid = extraParams[0] != null ? extraParams[0] : "0";
					 return hQLQueryManager.getSectionCombo(Long.parseLong(bid));
			     }*/
			     return hQLQueryManager.getSection();
			     
			}
			catch (Exception e)
			{
				LOG.info(e.getCause(),e);
				return null;
			}
		}

		 public String validateBuildingSection(Long sectionId ,Long buildingId, String sectionName)
		    {
		        try
		        {
		            if (commonService.isFieldValueExitsTwice(sectionId, buildingId, sectionName, "building_Id" ,"Section"))
		            {
		                return "{\"success\":true,\"message\":\"ALREADY THERE IS A SECTION REGISTERED UNDER SELECTED BUILDING\"}";
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
		public List<?> getData4AutoComplete(String... extraParams)
		{
			try 
			{
			     return hQLQueryManager.getSectionByUserId();
			}
			catch (Exception e)
			{
				LOG.info(e.getCause(),e);
				return null;
			}
		}
}
