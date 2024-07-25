package com.fts.hibernate.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "User_section")

public class UserSection
{
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
    @Cascade({CascadeType.REPLICATE })
	private UserInfo user;
	
	@OneToOne(fetch = FetchType.EAGER)
    @Cascade({CascadeType.REPLICATE })
	private  Section section ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
	
	
	
 }	
		


