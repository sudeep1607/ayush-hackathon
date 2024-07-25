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
import com.fts.hibernate.models.CreateAndModify;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "SECTION")
public class Section extends CreateAndModify 
{
    public Section()
    {
        
    }
    public Section(String id)
    {
        this.id = Long.parseLong(id);
    }
    
    public Section(Long id)
    {
        this.id = id;
    }
	@Id 
    @GeneratedValue
    protected Long id;
	
	@Column(name = "section_name")
	private String sectionName;
	
	@Column(name = "description")
	private String description;
	
	@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.REPLICATE })
	private UserInfo sectionIncharge;
	
	@Transient
	private Long sectionInchargeId;
	
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserInfo getSectionIncharge() {
		return sectionIncharge;
	}

	public void setSectionIncharge(UserInfo sectionIncharge) {
		this.sectionIncharge = sectionIncharge;
	}

    public Long getSectionInchargeId()
    {
        return sectionInchargeId;
    }
    public void setSectionInchargeId(Long sectionInchargeId)
    {
        this.sectionInchargeId = sectionInchargeId;
    }
	
}
