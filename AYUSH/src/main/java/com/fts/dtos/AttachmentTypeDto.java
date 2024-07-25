package com.fts.dtos;

public class AttachmentTypeDto
{
    private Long attachmentTypeId;
    private String attachmentTypeName;
    private String fileSerialNo;
    private String fileName;
    private String filePath;
    private String thumbanailFilePath;
    private String fileType;
    private Long attachmentsId;
    private boolean specific;

    public Long getAttachmentTypeId()
    {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(Long attachmentTypeId)
    {
        this.attachmentTypeId = attachmentTypeId;
    }

    public String getAttachmentTypeName()
    {
        return attachmentTypeName;
    }

    public void setAttachmentTypeName(String attachmentTypeName)
    {
        this.attachmentTypeName = attachmentTypeName;
    }

    public String getFileSerialNo()
    {
        return fileSerialNo;
    }

    public void setFileSerialNo(String fileSerialNo)
    {
        this.fileSerialNo = fileSerialNo;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public Long getAttachmentsId()
    {
        return attachmentsId;
    }

    public void setAttachmentsId(Long attachmentsId)
    {
        this.attachmentsId = attachmentsId;
    }

    public String getThumbanailFilePath()
    {
        return thumbanailFilePath;
    }

    public void setThumbanailFilePath(String thumbanailFilePath)
    {
        this.thumbanailFilePath = thumbanailFilePath;
    }

    public boolean isSpecific()
    {
        return specific;
    }

    public void setSpecific(boolean specific)
    {
        this.specific = specific;
    }
}
