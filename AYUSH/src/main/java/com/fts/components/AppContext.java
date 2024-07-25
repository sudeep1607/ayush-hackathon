package com.fts.components;

import org.springframework.context.ApplicationContext;

public final class AppContext
{
    private AppContext()
    {

    }

    private static ApplicationContext ctx;

    /**
     * Injected from the class "ApplicationContextProvider" which is automatically loaded during Spring-Initialization.
     */
    public static void setApplicationContext(ApplicationContext applicationContext)
    {
        ctx = applicationContext;
    }

    /**
     * Get access to the Spring ApplicationContext from everywhere in your Application.
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext()
    {
        return ctx;
    }
}
