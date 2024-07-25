package com.fts.dtos;


import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentDto
{
    private Long referenceId;
    private Long attachmentTableId;
    private String attachmentTableName;

    private List<AttachmentTypeDto> attachmentTypeDtoList;

    public Long getAttachmentTableId()
    {
        return attachmentTableId;
    }

    public void setAttachmentTableId(Long attachmentTableId)
    {
        this.attachmentTableId = attachmentTableId;
    }

    public String getAttachmentTableName()
    {
        return attachmentTableName;
    }

    public void setAttachmentTableName(String attachmentTableName)
    {
        this.attachmentTableName = attachmentTableName;
    }

    public List<AttachmentTypeDto> getAttachmentTypeDtoList()
    {
        return attachmentTypeDtoList;
    }

    public void setAttachmentTypeDtoList(List<AttachmentTypeDto> attachmentTypeDtoList)
    {
        this.attachmentTypeDtoList = attachmentTypeDtoList;
    }

    public Long getReferenceId()
    {
        return referenceId;
    }

    public void setReferenceId(Long referenceId)
    {
        this.referenceId = referenceId;
    }

}
