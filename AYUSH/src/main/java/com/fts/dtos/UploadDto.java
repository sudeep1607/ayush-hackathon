package com.fts.dtos;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadDto
{
  
    private String fileType;
    private String fileName;
    private CommonsMultipartFile file;

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public CommonsMultipartFile getFile()
    {
        return file;
    }

    public void setFile(CommonsMultipartFile file)
    {
        this.file = file;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

}
