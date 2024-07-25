package com.fts.hibernate.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@MappedSuperclass
 
public class CreateAndModify   {
	
	@Column(columnDefinition = "int default 1")
	private int active;
	
	@Column(length = 30, nullable = false)
	private Timestamp createdon;
	
	@Column(length = 30)
	private Timestamp modifyon;
	
	@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.REPLICATE })
    @Fetch(FetchMode.SELECT)
	private UserInfo createdBy;
	
	@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.REPLICATE })
    @Fetch(FetchMode.SELECT)
	private UserInfo modifiedBy;
	
	@Column
	private String remarks;

	 

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Timestamp getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Timestamp createdon) {
		this.createdon = createdon;
	}

	public Timestamp getModifyon() {
		return modifyon;
	}

	public void setModifyon(Timestamp modifyon) {
		this.modifyon = modifyon;
	}

	public UserInfo getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserInfo createdBy) {
		this.createdBy = createdBy;
	}

	public UserInfo getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserInfo modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
