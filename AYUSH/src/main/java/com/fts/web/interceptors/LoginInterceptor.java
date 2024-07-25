package com.fts.web.interceptors;

import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fts.Constants;
import com.fts.ThreadLocalData;
import com.fts.hibernate.models.UserInfo;
import com.fts.services.UserInfoService;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter
{
    private static final Log LOG = LogFactory.getLog(LoginInterceptor.class);

    @Autowired
    private UserInfoService userInfoService;
    private static Hashtable<String, HttpSession> loggedUsers = new Hashtable<String, HttpSession>();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        try
        {
            request.setCharacterEncoding("UTF-8");
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String action = request.getParameter("actionName");

            if ("signout".equals(action))
            {
                HttpSession session = request.getSession(false);
                // To avoid exception while already signed out and again trying to sign out
                if (session != null)
                {
                    session.invalidate();
                }
                response.sendRedirect("login?code=0");
                return false;
            }
            else
            {
                if (request.getSession(false) == null && !"login".equals(action))
                {
                    response.sendRedirect("login?code=3");
                    return false;
                }
                else
                {
                    UserInfo userSessionDTO = null;
                    if ((request.getSession(false)) != null && (request.getSession(false)).getAttribute(Constants.USERSESSION) != null)
                    {
                        userSessionDTO = (UserInfo) (request.getSession(false)).getAttribute(Constants.USERSESSION);
                    }
                    else if (!"login".equals(action))
                    {
                        response.sendRedirect("login?code=1");
                        return false;
                    }

                    if ("login".equals(action))
                    {
                        // Kept the following to lines to avoid Illegal State Exception ( back & re-login with out actually signed-out from the system)
                        HttpSession session = request.getSession(false);
                        if (session != null)
                        {
                            session.invalidate();
                        }
                      
                        	UserInfo userInfo = userInfoService.getUserByUserNameAndPwd(userName, password);
                            if (userInfo == null)
                            {
                                response.sendRedirect("login?code=1");
                                return false;
                            } 
                            else
                            {
                            	if(userInfo.getActive()==0)
                            	{
                            		 response.sendRedirect("login?code=2");
                                     return false;
                            	}
                           
                                String refresh = "";
                                ThreadLocalData.set(userInfo);
                                HttpSession httpSession = request.getSession(true);
                                httpSession.setAttribute(Constants.USERSESSION, userInfo);
                                HttpSession existingUserSession = loggedUsers.get(userName);
                                if (existingUserSession != null)
                                {
                                    try
                                    {
                                        existingUserSession.invalidate();
                                        httpSession.setAttribute("FORCEDLOGOUT", "1");
                                        refresh = "?r=1";
                                    }
                                    catch (Exception e)
                                    {
                                        LOG.trace(e.getCause());
                                    }
                                }
                                loggedUsers.put(userName, httpSession);
                                response.sendRedirect("home" + refresh);
                                return true;
                            } 
                    }
                    else
                    {
                        ThreadLocalData.set(userSessionDTO);
                    }
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return false;
        }
    }
}
