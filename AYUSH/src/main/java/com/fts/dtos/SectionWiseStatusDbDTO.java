package com.fts.dtos;

public class SectionWiseStatusDbDTO {
	
	private Long id;
	private long pendingCount;
	private long approvedCount;
	private long rejectedCount;
	private long closedCount;
	private String sectionName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getPendingCount() {
		return pendingCount;
	}
	public void setPendingCount(long pendingCount) {
		this.pendingCount = pendingCount;
	}
	public long getApprovedCount() {
		return approvedCount;
	}
	public void setApprovedCount(long approvedCount) {
		this.approvedCount = approvedCount;
	}
	public long getRejectedCount() {
		return rejectedCount;
	}
	public void setRejectedCount(long rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public long getClosedCount() {
		return closedCount;
	}
	public void setClosedCount(long closedCount) {
		this.closedCount = closedCount;
	}
	

}
