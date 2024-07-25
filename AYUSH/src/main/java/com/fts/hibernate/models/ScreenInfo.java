package com.fts.hibernate.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SCREEN_INFO")
public class ScreenInfo  
{
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 8855290954527792711L;

    @Id 
  	@GeneratedValue(strategy=GenerationType.AUTO)
  	protected Long id;
    
    @OneToOne
    @JoinColumn(name = "menu_id")
    private MenuInfo menuInfo;
    private String name;
    private String description;
    private String module;
    private String method;
    private String iconCls;
    private Integer priority;
    
    @Column(columnDefinition = "int default 1")
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public MenuInfo getMenuInfo()
    {
        return menuInfo;
    }

    public void setMenuInfo(MenuInfo menuInfo)
    {
        this.menuInfo = menuInfo;
    }

    public String getIconCls()
    {
        return iconCls;
    }

    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
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

    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
