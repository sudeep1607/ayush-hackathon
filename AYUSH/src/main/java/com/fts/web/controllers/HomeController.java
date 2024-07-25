package com.fts.web.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fts.ThreadLocalData;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.models.MenuInfo;
import com.fts.hibernate.models.Role;
import com.fts.services.UserInfoService;

@Controller
@RequestMapping("/home")
public class HomeController
{
	private static final Log LOG = LogFactory.getLog(HomeController.class);
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private HQLQueryManager hQLQueryManager;

    @RequestMapping
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("home");
        Long roleId= 0l;
        String extraParams[] =  new String[1];
        List<MenuInfo> menuInfos=new ArrayList<MenuInfo>();;
    	try {
			roleId =  Long.valueOf(request.getParameter("rl"));
		} catch (Exception e) {
		}
    	Role defaultRole =  userInfoService.getUserDefaultRole( );
        if(roleId > 0)
        {
        	menuInfos = userInfoService.getUserMappedMunuItems(roleId);
        	modelAndView.addObject("userDefaultRole", userInfoService.getUserDefaultRole(roleId));
        	ThreadLocalData.get().setDefaultRoleId(roleId);
        	extraParams[0] =  roleId+"";
        }else
        {
        	if(defaultRole !=null)
        	{
        		menuInfos = userInfoService.getUserMappedMunuItems(defaultRole.getId());
        		extraParams[0] =  defaultRole.getId()+"";
        	}else
        	{
        		menuInfos = userInfoService.getUserMappedMunuItems();
        		extraParams[0] =  "0";
        	}
        	modelAndView.addObject("userDefaultRole", defaultRole);
        	ThreadLocalData.get().setDefaultRoleId(defaultRole.getId());
        }
        modelAndView.addObject("menuInfos", menuInfos);
        LOG.info("defaultRole :"+defaultRole.getId());
        String filterString = "";
        modelAndView.addObject("alertCount", hQLQueryManager.getAlertForMonthRecordsCount(filterString, extraParams));
        modelAndView.addObject("newFilesCount", hQLQueryManager.getNewlyAssignedFilesCount(filterString, extraParams));
        modelAndView.addObject("userinfo", ThreadLocalData.get());
        return modelAndView;
    }
}
