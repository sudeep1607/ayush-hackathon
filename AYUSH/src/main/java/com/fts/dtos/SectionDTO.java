package com.fts.dtos;


public class SectionDTO
{
    private Long id;
    private String sectionName;
    private String description;
    private Long sectionInchargeId;
    private String sectionInchargeName;
    private Long buildingId;
    private String buildingName;
    private int active;
    private String designationName;
    private boolean checked;

	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getSectionName()
    {
        return sectionName;
    }
    public void setSectionName(String sectionName)
    {
        this.sectionName = sectionName;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public Long getSectionInchargeId()
    {
        return sectionInchargeId;
    }
    public void setSectionInchargeId(Long sectionInchargeId)
    {
        this.sectionInchargeId = sectionInchargeId;
    }
    public String getSectionInchargeName()
    {
        return sectionInchargeName;
    }
    public void setSectionInchargeName(String sectionInchargeName)
    {
        this.sectionInchargeName = sectionInchargeName;
    }
    public Long getBuildingId()
    {
        return buildingId;
    }
    public void setBuildingId(Long buildingId)
    {
        this.buildingId = buildingId;
    }
    public String getBuildingName()
    {
        return buildingName;
    }
    public void setBuildingName(String buildingName)
    {
        this.buildingName = buildingName;
    }
    public int getActive()
    {
        return active;
    }
    public void setActive(int active)
    {
        this.active = active;
    }
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
