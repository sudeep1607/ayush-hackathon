package com.fts.dtos;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComboDTO
{
	private Long id;
	private Long fileId;
	private String name;
	private String refId;
	private long sectionId;
	private long buildingId;
	private long designationId;
	private String designationName;
	private String subStoreInchargeName ;
	private String fileTypeCode ;
	private String sectionName ;
	private String userName ;
	private long priority;
	private long appUser;
	private String actions ;
	private String approvalUsers ;
	private long fileEntry;
	
    public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getSubStoreInchargeName() {
		return subStoreInchargeName;
	}
	public void setSubStoreInchargeName(String subStoreInchargeName) {
		this.subStoreInchargeName = subStoreInchargeName;
	}
	public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getRefId()
    {
        return refId;
    }
    public void setRefId(String refId)
    {
        this.refId = refId;
    }
	public long getSectionId() {
		return sectionId;
	}
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}
	public long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}
	public long getDesignationId() {
		return designationId;
	}
	public void setDesignationId(long designationId) {
		this.designationId = designationId;
	}
	public String getFileTypeCode() {
		return fileTypeCode;
	}
	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getAppUser() {
		return appUser;
	}
	public void setAppUser(long appUser) {
		this.appUser = appUser;
	}
	public long getPriority() {
		return priority;
	}
	public void setPriority(long priority) {
		this.priority = priority;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getApprovalUsers() {
		return approvalUsers;
	}
	public void setApprovalUsers(String approvalUsers) {
		this.approvalUsers = approvalUsers;
	}
	public long getFileEntry() {
		return fileEntry;
	}
	public void setFileEntry(long fileEntry) {
		this.fileEntry = fileEntry;
	}
}
