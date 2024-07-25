package com.fts.dtos;

import java.sql.Timestamp;

public class FileProcessDTO
{
	private Long id;
	private long fileEntryId;
	private String approvalUserName;
	private String fileStatus;
	private Timestamp fileInTime;
	private Timestamp fileOutTime;
	private long priority;
	private String fileActions;
	private String duration;
	private String fileName;
	public long getFileEntryId() {
		return fileEntryId;
	}
	public void setFileEntryId(long fileEntryId) {
		this.fileEntryId = fileEntryId;
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
	
	public String getFileActions() {
		return fileActions;
	}
	public void setFileActions(String fileActions) {
		this.fileActions = fileActions;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApprovalUserName() {
		return approvalUserName;
	}
	public void setApprovalUserName(String approvalUserName) {
		this.approvalUserName = approvalUserName;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public long getPriority() {
		return priority;
	}
	public void setPriority(long priority) {
		this.priority = priority;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
