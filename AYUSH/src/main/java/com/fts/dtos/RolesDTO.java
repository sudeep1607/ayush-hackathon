package com.fts.dtos;

public class RolesDTO
{
    private Long id ; 
    private String name;
    private String description;
    private boolean checked;
    private boolean defaultRole;
    
	public boolean isDefaultRole() {
		return defaultRole;
	}
	public void setDefaultRole(boolean defaultRole) {
		this.defaultRole = defaultRole;
	}
	public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public boolean isChecked()
    {
        return checked;
    }
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

}
