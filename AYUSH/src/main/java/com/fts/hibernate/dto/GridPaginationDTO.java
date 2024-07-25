package com.fts.hibernate.dto;

import java.util.List;

import com.fts.components.GridDTO;

public class GridPaginationDTO implements GridDTO
{
    private Long total;
    private boolean success = true;
    private String message;
    private List<? extends Object> records;
    
    public Long getTotal()
    {
        return total;
    }
    public void setTotal(Long total)
    {
        this.total = total;
    }
    public boolean isSuccess()
    {
        return success;
    }
    public void setSuccess(boolean success)
    {
        this.success = success;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public List<? extends Object> getRecords()
    {
        return records;
    }
    public void setRecords(List<? extends Object> records)
    {
        this.records = records;
    }
}
