package com.fts.web.interceptors;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fts.ThreadLocalData;
import com.fts.hibernate.managers.EventLoggerManager;
import com.fts.hibernate.models.EventLogger;
import com.fts.hibernate.models.UserInfo;
import com.fts.utils.DateUtils;

@Component
public class EventLogInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	EventLoggerManager eventLoggerManager; 
	
	private static final Log LOG = LogFactory.getLog(EventLogInterceptor.class);
	private static final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        try
        {
            startTime.set(System.currentTimeMillis());
            String path = request.getRequestURI();
            String requestedResMethod = path.substring(path.lastIndexOf("/") + 1);
            LOG.info("requestedResMethod:"+ requestedResMethod);
            addToLog(request, path, ThreadLocalData.get());
        } catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            
        }
        return super.preHandle(request, response, handler);
    }
	
	 public void postHandle( HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView modelAndView)
	 {
		 try
	        {
				long executionTime = System.currentTimeMillis() - startTime.get().longValue();
				 LOG.info(executionTime + " ms : Time taken for Rest url '"
	                    + request.getRequestURI());
				 String jsonData = response.getOutputStream().toString();
	             updateAuditLogWithResponseJson(jsonData, request);
	            
	        } catch (Exception e)
	        {
	            LOG.info(e.getCause(), e);
	        }
	 }
	 
	 
	
	
	
	
	
	
	 @SuppressWarnings("unchecked")
	 public void addToLog(HttpServletRequest httpReq, String requestedResPath, UserInfo userInfo  ) {
	        EventLogger elog = new EventLogger();
	        elog.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
	        Enumeration<String> parameterNames = httpReq.getParameterNames();
	        JSONObject obj = new JSONObject();
	        Map<String, String> a = new HashMap<String, String>();
	        try {
	            while (parameterNames.hasMoreElements()) {
	                String nextElement = parameterNames.nextElement();
	                String parameter = httpReq.getParameter(nextElement);
	                a.put(nextElement, parameter);
	            }
	            obj.putAll(a);
	            elog.setRequestJSON(obj.toJSONString());
	            elog.setUrl(requestedResPath);
        		elog.setUserInfo(userInfo);
        		eventLoggerManager.saveOrUpdate(elog);
	             httpReq.setAttribute("eventLogId", elog.getId());
	        } catch (Exception e) {
	            LOG.info(e.getMessage(),e);
	        }
	    }
	 
	 public boolean updateAuditLogWithResponseJson(String responseJSON, HttpServletRequest request) {
	        Long elogID = (Long) request.getAttribute("eventLogId");
	        LOG.info("eventLogId:" + "eventLogId");
	        EventLogger elog=null;
	        try {
				if (elogID != null && elogID > 0) {
					elog = eventLoggerManager.get(elogID);
					elog.setResponseJSON(responseJSON);
				} else {
				    LOG.info("No audit log exist");
				    elog = new EventLogger();
				    elog.setResponseJSON(responseJSON);
				    elog.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
				}
				if(elog!=null)
				eventLoggerManager.saveOrUpdate(elog);
			} catch (Exception e) {
				 LOG.info(e.getCause(), e);
			}
	        return true;
	    }
}
