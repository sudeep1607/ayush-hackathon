package com.fts.dtos;

public class ScreensDTO
{
    private Long id;
    private String name;
    private String description;
    private Long menuId;
    private String menuName;
    private String viewName;
    private String iconCls;
    private boolean checked;
    private boolean viewOnly;
    
    
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
    public Long getMenuId()
    {
        return menuId;
    }
    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }
    public String getMenuName()
    {
        return menuName;
    }
    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }
    public String getViewName()
    {
        return viewName;
    }
    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }
    public String getIconCls()
    {
        return iconCls;
    }
    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }
    public boolean isChecked()
    {
        return checked;
    }
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    public boolean isViewOnly()
    {
        return viewOnly;
    }
    public void setViewOnly(boolean viewOnly)
    {
        this.viewOnly = viewOnly;
    }
}
