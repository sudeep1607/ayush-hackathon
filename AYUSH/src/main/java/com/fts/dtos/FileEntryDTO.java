package com.fts.dtos;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class FileEntryDTO
{
    private Long id;
    private Long fileId;
	
	private String fileNo;
	private Date fileCreatedDate;
	private long section;
	private String fileName;
	private String  fileSubject;
	private String  fileDetails;
	private String  fileComments;
	private String  filePriority;
	private String  fileType;
	private String  fileBarcode;
	private String  sectionName;
	private long  designation;
	private String  fileStatus;
	private String  fileInitiatorName;
	private String  attachFilePath;
	private long  priority;
	private long  wfaId;
	private long  pendingAt;
	private String  pendingWith;
	private Timestamp fileInTime;
	private Timestamp fileOutTime;
	private String duration;
	private long  maxFileNo;
	private int  isWorkFlowCreated;
	private String  fileCode;

	private List<FileProcessDTO> fileProcess;
	
	
	
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getFileInitiatorName() {
		return fileInitiatorName;
	}
	public void setFileInitiatorName(String fileInitiatorName) {
		this.fileInitiatorName = fileInitiatorName;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public long getDesignation() {
		return designation;
	}
	public void setDesignation(long designation) {
		this.designation = designation;
	}
	private long  fileInitiator;
	private String  initiatorName;
	
	public long getFileInitiator() {
		return fileInitiator;
	}
	public void setFileInitiator(long fileInitiator) {
		this.fileInitiator = fileInitiator;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public Date getFileCreatedDate() {
		return fileCreatedDate;
	}
	public void setFileCreatedDate(Date fileCreatedDate) {
		this.fileCreatedDate = fileCreatedDate;
	}
	
	public long getSection() {
		return section;
	}
	public void setSection(long section) {
		this.section = section;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSubject() {
		return fileSubject;
	}
	public void setFileSubject(String fileSubject) {
		this.fileSubject = fileSubject;
	}
	public String getFileDetails() {
		return fileDetails;
	}
	public void setFileDetails(String fileDetails) {
		this.fileDetails = fileDetails;
	}
	public String getFileComments() {
		return fileComments;
	}
	public void setFileComments(String fileComments) {
		this.fileComments = fileComments;
	}
	public String getFilePriority() {
		return filePriority;
	}
	public void setFilePriority(String filePriority) {
		this.filePriority = filePriority;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileBarcode() {
		return fileBarcode;
	}
	public void setFileBarcode(String fileBarcode) {
		this.fileBarcode = fileBarcode;
	}
	public String getAttachFilePath() {
		return attachFilePath;
	}
	public void setAttachFilePath(String attachFilePath) {
		this.attachFilePath = attachFilePath;
	}
	public long getPriority() {
		return priority;
	}
	public void setPriority(long priority) {
		this.priority = priority;
	}
	public long getWfaId() {
		return wfaId;
	}
	public void setWfaId(long wfaId) {
		this.wfaId = wfaId;
	}
	public long getPendingAt() {
		return pendingAt;
	}
	public void setPendingAt(long pendingAt) {
		this.pendingAt = pendingAt;
	}
	public String getPendingWith() {
		return pendingWith;
	}
	public void setPendingWith(String pendingWith) {
		this.pendingWith = pendingWith;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public long getMaxFileNo() {
		return maxFileNo;
	}
	public void setMaxFileNo(long maxFileNo) {
		this.maxFileNo = maxFileNo;
	}
	public int getIsWorkFlowCreated() {
		return isWorkFlowCreated;
	}
	public void setIsWorkFlowCreated(int isWorkFlowCreated) {
		this.isWorkFlowCreated = isWorkFlowCreated;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public List<FileProcessDTO> getFileProcess() {
		return fileProcess;
	}
	public void setFileProcess(List<FileProcessDTO> fileProcess) {
		this.fileProcess = fileProcess;
	}
	
}
