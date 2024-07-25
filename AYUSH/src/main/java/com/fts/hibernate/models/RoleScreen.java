package com.fts.hibernate.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "Role_screen")

public class RoleScreen
{
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	@OneToOne
	@JoinColumn(name = "role_id")
	private Role roleId;
	
	@OneToOne
	@JoinColumn(name = "screen_id")
	private  ScreenInfo screenId ;
	
	
	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public ScreenInfo getScreenId() {
		return screenId;
	}

	public void setScreenId(ScreenInfo screenId) {
		this.screenId = screenId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 }	
		


