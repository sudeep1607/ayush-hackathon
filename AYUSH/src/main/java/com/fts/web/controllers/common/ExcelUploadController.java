package com.fts.web.controllers.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/uploadExcel")
public class ExcelUploadController
{
    private static final Log LOG = LogFactory.getLog(ExcelUploadController.class);

    @Value("#{pathConfig['excelUploadPath']}")
    private String excelUploadPath;

    @Value("#{pathConfig['xmlMappingPath']}")
    private String xmlMappingPath;
    
    @RequestMapping(value = "/itemsEntry")
    public String uploadItemsEntry(@RequestBody MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        LOG.info("multipartFile :"+multipartFile);
        String storeId = request.getParameter("storeId");
        LOG.info("storeId :"+storeId);
        if (multipartFile != null)
        {
            String filePath = excelUploadPath + System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return "{\"success\":false,\"data\":{\"result\":\"Not a file\"}}";
    }

    public String nullValidateFormatter(int row, int cell)
    {
        return String.format("{\"success\":false,\"data\":{\"result\":\"Invalid Data. The Data should not be empty for Row %d : Cell %d\"}}", row, cell);
    }

    public String objectValidateFormatter(String object, int row, int cell)
    {
        return String.format("{\"success\":false,\"data\":{\"result\":\"Invalid Data. The " + object + " doesnot exist for Row %d : Cell %d\"}}", row, cell);
    }

    public String exceptionMsgFormatter(String message)
    {
        return String.format("{\"success\":false,\"data\":{\"result\":\"Remote Exception %d\"}}", message);
    }

    public String masterDataValidateFormatter(String masterData, int row, int cell)
    {
        return String.format("{\"success\":false,\"data\":{\"result\":\"Invalid Data. The Data must be " + masterData + " for Row %d : Cell %d\"}}", row, cell);
    }

    @SuppressWarnings("unused")
    public static boolean isNumericDoubleVal(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    public static boolean isNegativeNumericDoubleVal(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
        if(d>0){
            return true;  
        }
        else{
            return false;  
        }
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      
    }
    @SuppressWarnings("unused")
    public static boolean isNumericLongVal(String str)  
    {  
      try  
      {  
          Long d = Long.parseLong(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
    public boolean isThisDateValid(String dateToValidate, String dateFromat){

        if(dateToValidate == null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException new java.sql.Date(dateToValidate.getTime());
            //Date date = new java.sql.Date(sdf.parse(dateToValidate).getTime());
            java.util.Date date = sdf.parse(dateToValidate);
            Date dateToSend = new Date(date.getTime());
            LOG.info("date by Excel :"+dateToSend);
        } catch (Exception e) {
            LOG.info(e.getCause());
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public Date getValidDate(String dateToValidate, String dateFromat){

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            java.util.Date date = sdf.parse(dateToValidate);
            Date dateToSend = new Date(date.getTime());
            
            /*
            
            Date date = new java.sql.Date(sdf.parse(dateToValidate).getTime());
            String dateVal = date.getDate()+"-"+date.getMonth()+"-"+date.getYear();
            Date date1 = new Date(dateVal);
            date = sdf.format(date);
            LOG.info("date by Excel1 :"+date);*/
            return dateToSend;
        } catch (Exception e) {
            LOG.info(e.getCause());
            e.printStackTrace();
            return null;
        }
    }
}
