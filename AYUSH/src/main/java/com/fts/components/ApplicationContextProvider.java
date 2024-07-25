package com.fts.components;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


@Service
public class ApplicationContextProvider implements ApplicationContextAware
{

    public void setApplicationContext(ApplicationContext ctx) throws BeansException
    {
        AppContext.setApplicationContext(ctx);
    }

}
