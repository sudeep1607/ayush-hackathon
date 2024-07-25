package com.fts.dtos;


public class OrganizationDTO
{
    private Long id;
    private String organizationName;
    private String description;
    private String organizationInchargeName;
    private Long organizationInchargeId;
    private String designationName;
    private int active;
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
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
	public String getOrganizationInchargeName() {
		return organizationInchargeName;
	}
	public void setOrganizationInchargeName(String organizationInchargeName) {
		this.organizationInchargeName = organizationInchargeName;
	}
	public Long getOrganizationInchargeId() {
		return organizationInchargeId;
	}
	public void setOrganizationInchargeId(Long organizationInchargeId) {
		this.organizationInchargeId = organizationInchargeId;
	}
	public int getActive()
    {
        return active;
    }
    public void setActive(int active)
    {
        this.active = active;
    }
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

}
