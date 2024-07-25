package com.fts.dtos;

import java.util.List;

public class WorkflowConfigDTO {
	
    protected Long id;
    private long fileId;
	private long fileEntry;
	private long section;
	private long designation;
	private int active;
	private String appUser;
	private String description;
	private String fileBarcode;
	private String sectionName;
	private long fileActionType;
	private String userName;
	private String designationName;
	private String fileTypeCode;
	private long priority;
	private long editMode;
	private List<ComboDTO> approvalUsers;
	
	
	public long getFileActionType() {
		return fileActionType;
	}
	public void setFileActionType(long fileActionType) {
		this.fileActionType = fileActionType;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getFileBarcode() {
		return fileBarcode;
	}
	public void setFileBarcode(String fileBarcode) {
		this.fileBarcode = fileBarcode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getFileEntry() {
		return fileEntry;
	}
	public void setFileEntry(long fileEntry) {
		this.fileEntry = fileEntry;
	}
	
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public List<ComboDTO> getApprovalUsers() {
		return approvalUsers;
	}
	public void setApprovalUsers(List<ComboDTO> approvalUsers) {
		this.approvalUsers = approvalUsers;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getFileTypeCode() {
		return fileTypeCode;
	}
	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}
	public long getPriority() {
		return priority;
	}
	public void setPriority(long priority) {
		this.priority = priority;
	}
	public long getSection() {
		return section;
	}
	public void setSection(long section) {
		this.section = section;
	}
	public long getDesignation() {
		return designation;
	}
	public void setDesignation(long designation) {
		this.designation = designation;
	}
	public long getEditMode() {
		return editMode;
	}
	public void setEditMode(long editMode) {
		this.editMode = editMode;
	}
	public String getAppUser() {
		return appUser;
	}
	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}
	
    
	
}
