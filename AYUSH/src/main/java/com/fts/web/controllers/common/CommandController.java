package com.fts.web.controllers.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fts.components.AppContext;
import com.fts.components.AttachmentComponent;
import com.fts.components.AutoCompleteComponent;
import com.fts.components.ComboComponent;
import com.fts.components.CustomComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.fts.components.ServiceComponent;
import com.fts.utils.GridFilter;

@RestController
public class CommandController 
{
    private static final Log LOG = LogFactory.getLog(CommandController.class);

    @Autowired
    private GridFilter gridFilter;
    
    @RequestMapping("/gridData")
    public GridDTO getGridDataInJSON(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String actionType = request.getParameter("actionType");
            String extraParams = request.getParameter("extraParams");
            
            String searchParam = request.getParameter("searchParam");
            String limit = request.getParameter("limit");
            String start = request.getParameter("start");

            Hashtable<String, Hashtable<String, String>> filterInfo = parseFilters(request);
            Hashtable<String, Hashtable<String, String>> searchInfo = parseSearchInfo(request.getParameter("search"));
            String filterString = gridFilter.generateQuery(start, limit, filterInfo);
            String searchString = gridFilter.generateSearchQuery(searchInfo);
            Vector<String> sortInfo = parseSortInfo(request);

            if (actionType != null)
            {
                GridComponent component = (GridComponent) AppContext.getApplicationContext().getBean(actionType);
                return component.getGridDataInJSON(sortInfo, filterString + " " + searchString, start, limit, extraParams, searchParam);
            }
            return null;
        }
        catch (Exception e)
        {
            LOG.error(e.getCause(), e);
            return null;
        }
    }

    /**
     * Used to parse sorting information from the request object
     *
     * @param request
     */
    private Vector<String> parseSortInfo(HttpServletRequest request)
    {
        Vector<String> sortInfo = new Vector<String>();
        try
        {
            if (request.getParameter("sort") != null)// Example Format for Sorting -- [{"property":"dariRecordNo","direction":"ASC"}]
            {
                JSONArray jsonArray = (JSONArray) (new JSONParser()).parse(request.getParameter("sort"));
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                sortInfo.add(jsonObject.get("property").toString());
                sortInfo.add(jsonObject.get("direction").toString().toUpperCase());
            }
        }
        catch (Exception e)
        {
            LOG.trace(e.getCause(), e);
        }
        return sortInfo;
    }

    /**
     * Used to parse filter information from the request object
     * 
     * @param request This class is specially written for preparing dynamic query for Extjs grid based filter Some of this parameters passed depends upon the manually written file
     *            overrides.js
     */
    private Hashtable<String, Hashtable<String, String>> parseFilters(HttpServletRequest request)
    {
        try
        {
            Hashtable<String, Hashtable<String, String>> map = new Hashtable<String, Hashtable<String, String>>();

            if (request.getParameter("filter") != null)
            {
                JSONArray jsonArray = (JSONArray) (new JSONParser()).parse(request.getParameter("filter"));
                for (int i = 0; i < jsonArray.size(); i++)
                {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String fieldName = (String) jsonObject.get("fieldName");
                    String dataType = (String) jsonObject.get("type");
                    String searchValue = "";
                    JSONArray searchList = new JSONArray();
                    if (!"list".equals(dataType))
                    {
                        searchValue = (String) jsonObject.get("value");
                    }
                    else
                    {
                        searchList = (JSONArray) jsonObject.get("value");
                        for (Object s : searchList)
                        {
                            searchValue += (("".equals(searchValue)) ? s.toString() : "," + s.toString());
                        }
                    }

                    String operator = (String) jsonObject.get("operator");
                    String dateFormat = (String) ("date".equals(dataType) ? jsonObject.get("dateFormat") : "NONE");

                    Hashtable<String, String> searchInfo = new Hashtable<String, String>();
                    searchInfo.put("DATATYPE", dataType);
                    searchInfo.put("SEARCHVALUE", searchValue);
                    searchInfo.put("OPERATOR", operator);
                    searchInfo.put("DATEFORMAT", dateFormat);

                    map.put(i + "#" + fieldName, searchInfo);
                }

                return map;
            }
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
        }
        return null;
    }

    /**
     * This controller is to work with all the form submissions
     */
    @RequestMapping("/command")
    public String insert(HttpServletRequest request, @RequestBody String jsonData)
    {
        try
        {
        	LOG.info("request in command controller:"+ request);
        	LOG.info("jsonData data in command controller:"+ jsonData);
            ServiceComponent serviceComponent = (ServiceComponent) AppContext.getApplicationContext().getBean(request.getParameter("actionType"));
            return serviceComponent.insert(jsonData, request.getParameter("extraParams"));
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return "{\"success\":false,\"message\":\"Remote Exception\"}";
        }
    }
    
    @RequestMapping("/comboData")
    public List<?> getData4Combo(HttpServletRequest request)
    {
        ComboComponent comboComponent = (ComboComponent) AppContext.getApplicationContext().getBean(request.getParameter("actionType"));
        return comboComponent.getData4Combo(request.getParameter("extraParams"));
    }
    @RequestMapping("/customComboData")
    public List<?> getCustomCombo(HttpServletRequest request)
    {
    	CustomComboComponent comboComponent = (CustomComboComponent) AppContext.getApplicationContext().getBean(request.getParameter("actionType"));
        return comboComponent.getCustomComboData(request.getParameter("extraParams"),request.getParameter("query"));
    }
    @RequestMapping("/authoCompleteData")
    public List<?> getData4AutoComplete(HttpServletRequest request)
    {
    	AutoCompleteComponent autoCompleteComponent = (AutoCompleteComponent) AppContext.getApplicationContext().getBean(request.getParameter("actionType"));
        return autoCompleteComponent.getData4AutoComplete(request.getParameter("extraParams"),request.getParameter("query"));
    }
    private Hashtable<String, Hashtable<String, String>> parseSearchInfo(String search)
    {
        try
        {
            Hashtable<String, Hashtable<String, String>> map = new Hashtable<String, Hashtable<String, String>>();

            if (search != null)
            {
                JSONArray jsonArray = (JSONArray) (new JSONParser()).parse(search);
                for (int i = 0; i < jsonArray.size(); i++)
                {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String fieldName = (String) jsonObject.get("fieldName");
                    String dataType = (String) jsonObject.get("type");
                    String searchValue = "";
                    JSONArray searchList = new JSONArray();
                    if (!"list".equals(dataType))
                    {
                        searchValue = (String) jsonObject.get("value");
                    }
                    else
                    {
                        searchList = (JSONArray) jsonObject.get("value");
                        for (Object s : searchList)
                        {
                            searchValue += (("".equals(searchValue)) ? s.toString() : "," + s.toString());
                        }
                    }

                    String operator = (String) jsonObject.get("operator");
                    String dateFormat = (String) ("date".equals(dataType) ? jsonObject.get("dateFormat") : "NONE");

                    Hashtable<String, String> searchInfo = new Hashtable<String, String>();
                    searchInfo.put("DATATYPE", dataType);
                    searchInfo.put("SEARCHVALUE", searchValue);
                    searchInfo.put("OPERATOR", operator);
                    searchInfo.put("DATEFORMAT", dateFormat);

                    map.put(i + "#" + fieldName, searchInfo);
                }

                return map;
            }
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
        }
        return null;
    }
    
    /***
     *  To Show the image
     *  @param refId
     *  @param actionType
     */
    @RequestMapping("/showAttachment")
    public String showAttachment(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            AttachmentComponent attachmentComponent = (AttachmentComponent) AppContext.getApplicationContext().getBean(request.getParameter("actionType"));
            Blob blob = attachmentComponent.getAttachment(request.getParameter("refId"));
            
            if(blob != null)
            {
                response.setContentType("image/png");
                
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(blob.getBytes(1, (int) blob.length())));
                OutputStream os = response.getOutputStream();
                ImageIO.write(bufferedImage, "png", os);
                os.close();
            }
            return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }

}
