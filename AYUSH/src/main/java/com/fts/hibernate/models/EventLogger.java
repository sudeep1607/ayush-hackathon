package com.fts.hibernate.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
//import javax.persistence.SequenceGenerator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fts.hibernate.models.UserInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class EventLogger     { 
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2249639186198664614L;
	
	
		@Id 
		@GeneratedValue
		protected Long id;
		public Long getId()
		{
		    return id;
		}
		
		@Column(length = 5000, nullable = false)
		private String url;
		
		@Column(nullable = true)
		private String module;
		
		@Column(columnDefinition = "VARCHAR(4000) NULL")
		private String requestJSON;
		
		@Column(columnDefinition = "VARCHAR(4000) NULL")
		private String responseJSON;
		
		@OneToOne(fetch = FetchType.LAZY)
		@Cascade({ CascadeType.REPLICATE })
		private UserInfo userInfo;
		
		@Column(nullable = false)
		private Timestamp createdOn;
		
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
		
		public String getRequestJSON() {
			return requestJSON;
		}
		
		public void setRequestJSON(String requestJSON) {
			this.requestJSON = requestJSON;
		}
		
		public String getResponseJSON() {
			return responseJSON;
		}
		
		public void setResponseJSON(String responseJSON) {
			this.responseJSON = responseJSON;
		}
		
		public Timestamp getCreatedOn() {
			return createdOn;
		}
		
		public void setCreatedOn(Timestamp createdOn) {
			this.createdOn = createdOn;
		}
		
		public String getModule() {
			return module;
		}
		
		public void setModule(String module) {
			this.module = module;
		}
		
		public UserInfo getUserInfo() {
			return userInfo;
		}
		
		public void setUserInfo(UserInfo userInfo) {
			this.userInfo = userInfo;
		}

		public void setId(Long id) {
			this.id = id;
		}	
		
}
