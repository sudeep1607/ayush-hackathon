package com.fts.web.controllers.common;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.ModelAndView;

import com.fts.services.CommonService;

@Controller
@RequestMapping(value = "/printForm")
public class PrintFormController {
	
	private static final Log LOG = LogFactory.getLog(PrintFormController.class);
	@Autowired
	private CommonService commonService;
		
	@RequestMapping(value = "/getFileProcessFlow", method = RequestMethod.GET)
    public ModelAndView getAssetIssuedItemContent(@RequestParam("strSelIds") String selectedFileIds,HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
        	StringBuffer htmlData = commonService.getSelectedFileProcessFlow(selectedFileIds);
        	response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.write(htmlData.toString());
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
        }
        return null;
    }
}
