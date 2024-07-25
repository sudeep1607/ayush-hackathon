package com.fts.hibernate.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.fts.hibernate.models.CreateAndModify;



@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "ROLE")
public class Role extends CreateAndModify
{
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
    
	public Long getId()
	{
	    return id;
	}
    public Role()
    {

    }
    public Role(String id)
    {
        this.id = Long.parseLong(id);
    }

    public Role(Long id)
    {
        this.id = id;
    }
    

    public void setId(Long id) {
		this.id = id;
	}


	@Column(nullable = false)
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ROLE_SCREEN", joinColumns =
    {
        @JoinColumn(name = "ROLE_ID")
    }
    ,inverseJoinColumns =
    {
        @JoinColumn(name = "SCREEN_ID")
    })
    private List<ScreenInfo> screens;

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

    public List<ScreenInfo> getScreens()
    {
        return screens;
    }

    public void setScreens(List<ScreenInfo> screens)
    {
        this.screens = screens;
    }

}
