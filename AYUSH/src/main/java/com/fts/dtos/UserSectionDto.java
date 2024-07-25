package com.fts.dtos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSectionDto {
	
	private Long id ; 
    private String userName;
    private List<SectionDTO> sections;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<SectionDTO> getSections() {
		return sections;
	}
	public void setSections(List<SectionDTO> sections) {
		this.sections = sections;
	}
	

}
