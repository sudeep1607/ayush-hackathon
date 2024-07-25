package com.fts.dtos.notifiation;

public class MenuNotificationDTO
{
    private Long menuId;
    private Integer count;
    private Long refId;
    public Long getMenuId()
    {
        return menuId;
    }
    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }
    public Integer getCount()
    {
        return count;
    }
    public void setCount(Integer count)
    {
        this.count = count;
    }
    public Long getRefId()
    {
        return refId;
    }
    public void setRefId(Long refId)
    {
        this.refId = refId;
    }
    
}
