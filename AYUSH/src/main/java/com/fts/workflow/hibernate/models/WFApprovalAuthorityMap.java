package com.fts.workflow.hibernate.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fts.hibernate.models.CreateAndModify;
import com.fts.filemanagement.models.FileEntry;
import com.fts.hibernate.models.FileType;
import com.fts.hibernate.models.UserInfo;

@Entity
@JsonIgnoreProperties(ignoreUnknown =true)
@JsonRootName("records")
@Table(name = "wf_approval_auth_map")
public class WFApprovalAuthorityMap extends CreateAndModify {
	
	@Id 
	@GeneratedValue
	protected Long id;
	public Long getId()
	{
	    return id;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@Cascade({CascadeType.REPLICATE})
	private FileEntry fileEntry;
	
	@OneToOne(fetch = FetchType.LAZY)
	@Cascade({CascadeType.REPLICATE})
	private UserInfo approvalAuthority;
	
	@OneToOne(fetch = FetchType.LAZY)
	@Cascade({CascadeType.REPLICATE})
	private FileType fileStatus;
	
	@Column(length = 30)
	private Timestamp fileInTime;
	
	@Column(length = 30)
	private Timestamp fileOutTime;
	
	private Long priority;
	private Long activeStatusId;
	private Long approvalStatusId;
	@Column(name="ACTIONS")
	private String actionTypes;
 
	public FileEntry getFileEntry() {
		return fileEntry;
	}
	public void setFileEntry(FileEntry fileEntry) {
		this.fileEntry = fileEntry;
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
	public void setId(Long id) {
		this.id = id;
	}
	public UserInfo getApprovalAuthority() {
		return approvalAuthority;
	}
	public void setApprovalAuthority(UserInfo approvalAuthority) {
		this.approvalAuthority = approvalAuthority;
	}
	public FileType getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(FileType fileStatus) {
		this.fileStatus = fileStatus;
	}
	public Timestamp getFileInTime() {
		return fileInTime;
	}
	public void setFileInTime(Timestamp fileInTime) {
		this.fileInTime = fileInTime;
	}
	public Timestamp getFileOutTime() {
		return fileOutTime;
	}
	public void setFileOutTime(Timestamp fileOutTime) {
		this.fileOutTime = fileOutTime;
	}
	public String getActionTypes() {
		return actionTypes;
	}
	public void setActionTypes(String actionTypes) {
		this.actionTypes = actionTypes;
	}
}
