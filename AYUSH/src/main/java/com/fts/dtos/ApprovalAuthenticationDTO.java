package com.fts.dtos;
import java.sql.Date;
public class ApprovalAuthenticationDTO {
	    protected Long id;
		private String authenticatedOfficerName;
	    private String authenticationRemarks;
	    private String authenticationReleasedStatus;
	    private Date authenticationAssignedDate;
		private String temporarayOfficerAuthenticated;
		private long temporarayOfficerId;
		private long authenticatedOfficerId;
		private long approvalId ;
		private String temporarayOfficerDesignation;
		private String authenticatedOfficerDesignation;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getAuthenticatedOfficerName() {
			return authenticatedOfficerName;
		}
		public void setAuthenticatedOfficerName(String authenticatedOfficerName) {
			this.authenticatedOfficerName = authenticatedOfficerName;
		}
		public String getAuthenticationRemarks() {
			return authenticationRemarks;
		}
		public void setAuthenticationRemarks(String authenticationRemarks) {
			this.authenticationRemarks = authenticationRemarks;
		}
		public Date getAuthenticationAssignedDate() {
			return authenticationAssignedDate;
		}
		public void setAuthenticationAssignedDate(Date authenticationAssignedDate) {
			this.authenticationAssignedDate = authenticationAssignedDate;
		}
		public String getTemporarayOfficerAuthenticated() {
			return temporarayOfficerAuthenticated;
		}
		public void setTemporarayOfficerAuthenticated(String temporarayOfficerAuthenticated) {
			this.temporarayOfficerAuthenticated = temporarayOfficerAuthenticated;
		}
		public long getTemporarayOfficerId() {
			return temporarayOfficerId;
		}
		public void setTemporarayOfficerId(long temporarayOfficerId) {
			this.temporarayOfficerId = temporarayOfficerId;
		}
		public long getAuthenticatedOfficerId() {
			return authenticatedOfficerId;
		}
		public void setAuthenticatedOfficerId(long authenticatedOfficerId) {
			this.authenticatedOfficerId = authenticatedOfficerId;
		}
		public long getApprovalId() {
			return approvalId;
		}
		public void setApprovalId(long approvalId) {
			this.approvalId = approvalId;
		}
		public String getTemporarayOfficerDesignation() {
			return temporarayOfficerDesignation;
		}
		public void setTemporarayOfficerDesignation(String temporarayOfficerDesignation) {
			this.temporarayOfficerDesignation = temporarayOfficerDesignation;
		}
		public String getAuthenticatedOfficerDesignation() {
			return authenticatedOfficerDesignation;
		}
		public void setAuthenticatedOfficerDesignation(String authenticatedOfficerDesignation) {
			this.authenticatedOfficerDesignation = authenticatedOfficerDesignation;
		}
		public String getAuthenticationReleasedStatus() {
			return authenticationReleasedStatus;
		}
		public void setAuthenticationReleasedStatus(String authenticationReleasedStatus) {
			this.authenticationReleasedStatus = authenticationReleasedStatus;
		}
		
		
		

}
