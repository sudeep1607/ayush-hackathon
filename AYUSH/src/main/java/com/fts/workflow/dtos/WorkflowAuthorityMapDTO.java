package com.fts.workflow.dtos;

public class WorkflowAuthorityMapDTO {

	private Long id;
	private Long fileEntryId;
	private String workFlowName;
	private Long activestatusId;
	private Long approvalstatusid;
	private Long approvalauthorityId;
	private String authorityName;
	private String description;
	private Long priority;
	private String mappedStatusIds;
	
	public String getMappedStatusIds() {
		return mappedStatusIds;
	}
	public void setMappedStatusIds(String mappedStatusIds) {
		this.mappedStatusIds = mappedStatusIds;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getWorkFlowName() {
		return workFlowName;
	}
	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}
	public Long getActivestatusId() {
		return activestatusId;
	}
	public void setActivestatusId(Long activestatusId) {
		this.activestatusId = activestatusId;
	}
	public Long getApprovalstatusid() {
		return approvalstatusid;
	}
	public void setApprovalstatusid(Long approvalstatusid) {
		this.approvalstatusid = approvalstatusid;
	}
	public Long getApprovalauthorityId() {
		return approvalauthorityId;
	}
	public void setApprovalauthorityId(Long approvalauthorityId) {
		this.approvalauthorityId = approvalauthorityId;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public Long getFileEntryId() {
		return fileEntryId;
	}
	public void setFileEntryId(Long fileEntryId) {
		this.fileEntryId = fileEntryId;
	}
	
	
	
      
	
}
