package com.fts.components;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fts.utils.URLConnect;

/**
 * @author rabindranath.s
 */
public class Geocoder
{
    private static final Log LOG = LogFactory.getLog(Geocoder.class);

    public static class GeocoderStatus
    {
        private static final String OK = "OK";
    }

    public static String geocode(String lat, String lng)
    {
        String url = "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&latlng=" + lat + "," + lng;
        String json = URLConnect.connect(url);
        return json;
    }

    public static String getFormattedAddress(String lat, String lng)
    {
        String address = lat + "," + lng;;
        try
        {
            String geocodeJsonData = Geocoder.geocode(lat, lng);
            if (geocodeJsonData != null && !"".equals(geocodeJsonData))
            {
                JSONObject responsJSON = (JSONObject) JSONValue.parse(geocodeJsonData);
                if (GeocoderStatus.OK.equalsIgnoreCase((String) responsJSON.get("status")))
                {
                    JSONArray results = (JSONArray) responsJSON.get("results");
                    JSONObject addressObj = (JSONObject) results.get(0);
                    address = (String) addressObj.get("formatted_address");
                }
                else
                {
                    //address = (String) responsJSON.get("status");
                }
            }
            else
            {
                //address = "URL Connection Exception";
            }
            
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            //address = "Exception Occured While Geocoding";
        }
        return address;
    }
}
