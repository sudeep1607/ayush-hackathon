package com.fts.dtos;

import java.util.List;

public class TreeNodeDTO
{
    private Long id;
    private String text;
    private Boolean leaf = false;
    private Boolean expanded = false;
    private String module;
    private String method;
    private String iconCls;
    private Long menuId;
    private Long count;
    private List<TreeNodeDTO> children;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Boolean getLeaf()
    {
        return leaf;
    }

    public void setLeaf(Boolean leaf)
    {
        this.leaf = leaf;
    }

    public Boolean getExpanded()
    {
        return expanded;
    }

    public void setExpanded(Boolean expanded)
    {
        this.expanded = expanded;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getIconCls()
    {
        return iconCls;
    }

    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }

    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public List<TreeNodeDTO> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeNodeDTO> children)
    {
        this.children = children;
    }

    public Long getMenuId()
    {
        return menuId;
    }

    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
