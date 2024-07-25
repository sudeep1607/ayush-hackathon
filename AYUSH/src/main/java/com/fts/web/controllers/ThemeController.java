package com.fts.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/theme")
public class ThemeController
{
    private static final Log LOG = LogFactory.getLog(ThemeController.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private Properties appConfig;

    @RequestMapping
    public String getView(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try
        {
            response.setContentType("text/css");
            PrintWriter out = response.getWriter();

            String theme = appConfig.getProperty("THEME");
            if(theme != null && !"".equals(theme))
            {
                String css = getCss(theme);
                out.write(css);
            }
            else
            {
                out.write("");
            }

            return null;
        }
        catch (Exception e)
        {
            LOG.error(e.getCause(), e);
            return null;
        }
    }

    public String getCss(String theme)
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("bgColor", "#" + appConfig.getProperty(theme + "_bgColor"));
            map.put("btnBgColor", "#" + appConfig.getProperty(theme + "_btnBgColor"));
            map.put("btnFontColor", "#" + appConfig.getProperty(theme + "_btnFontColor"));
            map.put("bgImage", "url" + appConfig.getProperty(theme + "_bgImage"));
            Template template = configuration.getTemplate("theme.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        }
        catch (Exception e)
        {
            LOG.error(e.getCause(), e);
            return null;
        }
    }

}
