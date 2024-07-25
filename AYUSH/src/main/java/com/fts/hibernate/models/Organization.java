package com.fts.hibernate.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "ORGANIZATION")
public class Organization extends CreateAndModify 
{
    
   
	@Id 
    @GeneratedValue
    protected Long id;
	
	 public Organization()
	    {
	        
	    }
	    public Organization(String id)
	    {
	        this.id = Long.parseLong(id);
	    }
	    
	    public Organization(Long id)
	    {
	        this.id = id;
	    }
	
	@Column(name = "organization_name")
	private String organizationName;
	
	@Column(name = "description")
	private String description;
	
	@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.REPLICATE })
	private UserInfo organizationIncharge;
	
	@Transient
	private  Long organizationInchargeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UserInfo getOrganizationIncharge() {
		return organizationIncharge;
	}
	public void setOrganizationIncharge(UserInfo organizationIncharge) {
		this.organizationIncharge = organizationIncharge;
	}
	public Long getOrganizationInchargeId() {
		return organizationInchargeId;
	}
	public void setOrganizationInchargeId(Long organizationInchargeId) {
		this.organizationInchargeId = organizationInchargeId;
	}

	
	
}
