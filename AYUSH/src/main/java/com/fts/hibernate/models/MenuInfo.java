package com.fts.hibernate.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "MENU_INFO")
public class MenuInfo  
{
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
    
    @Id 
  	@GeneratedValue(generator = "menuSeq")
  	@SequenceGenerator(name = "menuSeq", sequenceName = "Menu_SEQ", allocationSize = 1)
  	protected Long id;
    
  	public Long getId()
  	{
  	    return id;
  	}
    
    private String menuText;
    private String viewMode;
    private String iconCls;
    private Boolean status;
    private Integer priority;

    public String getMenuText()
    {
        return menuText;
    }

    public void setMenuText(String menuText)
    {
        this.menuText = menuText;
    }

    public String getIconCls()
    {
        return iconCls;
    }

    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }

    public Boolean getStatus()
    {
        return status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public String getViewMode()
    {
        return viewMode;
    }

    public void setViewMode(String viewMode)
    {
        this.viewMode = viewMode;
    }

	public void setId(Long id) {
		this.id = id;
	}

}
