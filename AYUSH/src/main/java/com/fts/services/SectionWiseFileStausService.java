package com.fts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.ThreadLocalData;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.dtos.SectionWiseStatusDbDTO;
import com.fts.filemanagement.managers.FileEntryManager;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.SectionManager;
import com.fts.hibernate.managers.UserSectionManager;
import com.fts.hibernate.models.Section;

@Service
public class SectionWiseFileStausService implements GridComponent{

	private static final Log LOG = LogFactory.getLog(SectionWiseFileStausService.class);
	
	
	@Autowired
	private UserSectionManager userSectionManager;

	@Autowired
	private SectionManager sectionManager;
	
	@Autowired
	private FileEntryManager fileEntryManager;
	
	
	public List<SectionWiseStatusDbDTO> getDashBoardData()
	{
		    List<Section> sections = null;
	     	if(ThreadLocalData.get().getDefaultRoleId()==3)
	     		sections = userSectionManager.getUserWiseSections(ThreadLocalData.get().getId());
	     	else
	     		sections = sectionManager.getAllActiveSections();
	     	
			if(sections.size() > 0)
			{
				return	getDashBoardObject(sections);
			}
			return null;
	}
	private List<SectionWiseStatusDbDTO> getDashBoardObject(List<Section> sections) {

		try {
			  List<SectionWiseStatusDbDTO> sectionWiseStatusDbDTOsList = new ArrayList<SectionWiseStatusDbDTO>();
			
				for(Section s: sections)
				{
					SectionWiseStatusDbDTO sectionWiseStatusDbDTO  =new SectionWiseStatusDbDTO();
					 //sectionName
					sectionWiseStatusDbDTO.setSectionName(s.getSectionName());
					sectionWiseStatusDbDTO.setId(s.getId());
					sectionWiseStatusDbDTO.setApprovedCount(fileEntryManager.getAllApprovedFiles(s.getId()));
					sectionWiseStatusDbDTO.setPendingCount(fileEntryManager.getAllPendingFiles(s.getId()));
					sectionWiseStatusDbDTO.setRejectedCount(fileEntryManager.getAllRejectedFiles(s.getId()));
					sectionWiseStatusDbDTO.setRejectedCount(fileEntryManager.getAllRejectedFiles(s.getId()));
					sectionWiseStatusDbDTO.setClosedCount(fileEntryManager.getAllClosedFiles(s.getId()));
					sectionWiseStatusDbDTOsList.add(sectionWiseStatusDbDTO);
				}
				return sectionWiseStatusDbDTOsList;
		} catch (Exception e) {
			e.getMessage();
			LOG.info(""+e.getCause());
			return null;
		}
		
	}
	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
			String... extraParams) throws Exception {
		GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
		gridPaginationDTO.setRecords(getDashBoardData());
		return gridPaginationDTO;
	}

}
