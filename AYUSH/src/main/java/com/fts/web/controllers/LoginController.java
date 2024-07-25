package com.fts.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController
{
    @RequestMapping("/login")
    public ModelAndView getView(@RequestParam(value = "code", required = false) String code, HttpServletRequest request)
    {
        if ("0".equals(code))
        {
            request.setAttribute("message", " You are successfully logged out");
        }
        else if ("1".equals(code))
        {
            request.setAttribute("message", " The username or password is invalid");
        }
        else if ("2".equals(code))
        {
            request.setAttribute("message", " The User is blocked");
        }
        else if ("3".equals(code))
        {
            request.setAttribute("message", " Session Unavailable");
        }else if ("4".equals(code))
        {
            request.setAttribute("message", "User validity is expired");
        }else if ("5".equals(code))
        {
            request.setAttribute("message", "Not Registered in AMS, Please try after 24 Hrs..");
        }
        return new ModelAndView("login");
    }
    @RequestMapping("/admin")
    public ModelAndView adminView(@RequestParam(value = "code", required = false) String code, HttpServletRequest request)
    {
        return new ModelAndView("login");
    }
}
