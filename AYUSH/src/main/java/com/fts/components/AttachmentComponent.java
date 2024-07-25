package com.fts.components;

import java.sql.Blob;

public interface AttachmentComponent
{
    public Blob getAttachment(String... params);
}
