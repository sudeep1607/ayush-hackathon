package com.fts.dtos;

public class RecordsCountDTO 
{
	private Long dayDownloadCount;
	private Integer totalDownloadCount;
	private Long suggestionCount;
	private Long trackMeCount;
	private Long doorLockCount;
	private Long verificationsCount;
	private Long pubTransportComplCount;
	public Long getDayDownloadCount() {
		return dayDownloadCount;
	}
	public void setDayDownloadCount(Long dayDownloadCount) {
		this.dayDownloadCount = dayDownloadCount;
	}
	public Integer getTotalDownloadCount() {
		return totalDownloadCount;
	}
	public void setTotalDownloadCount(Integer totalDownloadCount) {
		this.totalDownloadCount = totalDownloadCount;
	}
	public Long getSuggestionCount() {
		return suggestionCount;
	}
	public void setSuggestionCount(Long suggestionCount) {
		this.suggestionCount = suggestionCount;
	}
	public Long getTrackMeCount() {
		return trackMeCount;
	}
	public void setTrackMeCount(Long trackMeCount) {
		this.trackMeCount = trackMeCount;
	}
	public Long getDoorLockCount() {
		return doorLockCount;
	}
	public void setDoorLockCount(Long doorLockCount) {
		this.doorLockCount = doorLockCount;
	}
	public Long getVerificationsCount() {
		return verificationsCount;
	}
	public void setVerificationsCount(Long verificationsCount) {
		this.verificationsCount = verificationsCount;
	}
	public Long getPubTransportComplCount() {
		return pubTransportComplCount;
	}
	public void setPubTransportComplCount(Long pubTransportComplCount) {
		this.pubTransportComplCount = pubTransportComplCount;
	}
}
