	package com.fts.dtos;

	public class EnployeeInfoDTO {
		
		private Long id;
		private String employeeName;
		private String employeeId;
		private String firstName;
		private String lastName;
		private String designationName;
		private String sectionName;
		private Long sectionId;
		private Long designationId;
		private Long buildingId	;
		private String buildingName	;
		private Long reportingEmployeeId;
		private String assigningRemarks;
		private String reportingOfficerName;
		public String getAssigningRemarks() {
			return assigningRemarks;
		}
		public void setAssigningRemorks(String assigningRemarks) {
			this.assigningRemarks = assigningRemarks;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getEmployeeName() {
			return employeeName;
		}
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		public String getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
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
		public Long getDesignationId() {
			return designationId;
		}
		public void setDesignationId(Long designationId) {
			this.designationId = designationId;
		}
		public Long getBuildingId() {
			return buildingId;
		}
		public void setBuildingId(Long buildingId) {
			this.buildingId = buildingId;
		}
		public String getBuildingName() {
			return buildingName;
		}
		public void setBuildingName(String buildingName) {
			this.buildingName = buildingName;
		}
		public Long getReportingEmployeeId() {
			return reportingEmployeeId;
		}
		public void setReportingEmployeeId(Long reportingEmployeeId) {
			this.reportingEmployeeId = reportingEmployeeId;
		}
		public void setAssigningRemarks(String assigningRemarks) {
			this.assigningRemarks = assigningRemarks;
		}
		public String getReportingOfficerName() {
			return reportingOfficerName;
		}
		public void setReportingOfficerName(String reportingOfficerName) {
			this.reportingOfficerName = reportingOfficerName;
		}
		
		
		
		
		

	}
