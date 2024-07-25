package com.fts.components;

import java.util.Vector;

public interface GridComponent
{
    public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams) throws Exception;
}
