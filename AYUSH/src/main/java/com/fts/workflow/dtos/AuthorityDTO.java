package com.fts.workflow.dtos;

public class AuthorityDTO {

	private Long id ; 
    private String name;
    private String workFlowName;
    private String description;
    private boolean checked;
    private Long priority;
	private Long activeStatusId;
	private Long approvalStatusId;
	private Long sectionId;
	private Long desigId;
	  private String designationName;
	  private String sectionName;
    
	  
	  
    
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getDesigId() {
		return desigId;
	}
	public void setDesigId(Long desigId) {
		this.desigId = desigId;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public Long getActiveStatusId() {
		return activeStatusId;
	}
	public void setActiveStatusId(Long activeStatusId) {
		this.activeStatusId = activeStatusId;
	}
	public Long getApprovalStatusId() {
		return approvalStatusId;
	}
	public void setApprovalStatusId(Long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getWorkFlowName() {
		return workFlowName;
	}
	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}
	
}
