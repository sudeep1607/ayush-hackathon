package com.fts.hibernate.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonRootName;

import com.fts.hibernate.models.CreateAndModify;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("records")
public class DesignationInfo extends CreateAndModify{
	
	
	@Id 
	@GeneratedValue(generator = "desigSeq")
	@SequenceGenerator(name = "desigSeq", sequenceName = "Designation_SEQ", allocationSize = 1)
	protected Long id;
	public DesignationInfo()
    {
       
    }
	public Long getId()
	{
	    return id;
	}
	public DesignationInfo(String id)
    {
        this.id = Long.parseLong(id);
    }
    
    public DesignationInfo(Long id)
    {
        this.id = id;
    }

	@Column(unique=true)
	private String designationName;	
	@Column
	private String designationDescription;
	
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getDesignationDescription() {
		return designationDescription;
	}
	public void setDesignationDescription(String designationDescription) {
		this.designationDescription = designationDescription;
	}
	public void setId(Long id) {
		this.id = id;
	}			
	
	
}
