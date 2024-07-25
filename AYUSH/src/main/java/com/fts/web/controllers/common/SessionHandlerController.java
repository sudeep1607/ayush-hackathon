package com.fts.web.controllers.common;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fts.Constants;
import com.fts.hibernate.models.UserInfo;

@Controller
public class SessionHandlerController
{
    @RequestMapping("/sessionHandler")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        
        if (session != null)
        {
            UserInfo userInfo = (UserInfo) session.getAttribute(Constants.USERSESSION);
            out.write(userInfo != null ? "1" : "0");
        }
        else
        {
            out.write("0");
        }
        
        return null;
    }

}
