/**
 * 
 */
package com.fts.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author keshab.g
 */
public class HttpClientUtils
{
    private static final Log LOG = LogFactory.getLog(HttpClientUtils.class);
    private static HttpClient httpclient;

    public static HttpClient getHttpClient()
    {
        if (httpclient == null) return new HttpClient();
        else return httpclient;
    }

    @SuppressWarnings("deprecation")
    public static String getResponseByHttpClient(String JSON, String url, HashMap<String, String> hm)
    {
        // Prepare HTTP post
        try
        {
            LOG.info("httpclient url : " + url);
            PostMethod post = new PostMethod(url);

            if (JSON != null)
            {
                post.setRequestBody(JSON);
                post.setRequestHeader("Content-type", "Application/json");
            }
            else if (hm.size() > 0)
            {
                for (Map.Entry<String, String> entry : hm.entrySet())
                {
                    post.setParameter(entry.getKey(), entry.getValue());
                }
            }

            // Get HTTP client
            HttpClient httpclient = getHttpClient();

            // Execute request
            try
            {
                int result = httpclient.executeMethod(post);

                // Display status code
                LOG.info("Response status code: " + result);

                // Display response
                LOG.info("Response body: ");
                LOG.info(post.getResponseBodyAsString());

                return post.getResponseBodyAsString();

            }
            catch (HttpException e)
            {
                LOG.info("error:  " + e.getMessage(), e);
            }
            catch (IOException e)
            {
                LOG.info("error:  " + e.getMessage(), e);
            }
            finally
            {
                LOG.info("Response body: close connection ");
                // Release current connection to the connection pool once you are
                // done
                post.releaseConnection();
            }
        }
        catch (Exception e)
        {
            LOG.info("error " + e.getMessage(), e);
        }
        return null;
    }

    public static String getResponseByHttpClient(String url)
    {
        try
        {
            LOG.info("httpclient url : " + url);
            GetMethod get = new GetMethod(url);

            // Get HTTP client
            HttpClient httpclient = getHttpClient();

            // Execute request
            try
            {
                int result = httpclient.executeMethod(get);

                // Display status code
                LOG.info("Response status code: " + result);

                // Display response
                LOG.info("Response body: ");
                LOG.info(get.getResponseBodyAsString());
                
                if(result == 200)
                    return get.getResponseBodyAsString();
                else
                    return "unable to connect";

            }
            catch (HttpException e)
            {
                LOG.info("error:  " + e.getMessage(), e);
            }
            catch (IOException e)
            {
                LOG.info("error:  " + e.getMessage(), e);
                return "unable to connect";
            }
            finally
            {
                LOG.info("Response body: close connection ");
                // Release current connection to the connection pool once you are
                // done
                get.releaseConnection();
            }
        }
        catch (Exception e)
        {
            LOG.info("error " + e.getMessage(), e);
        }
        return null;
    }
    
    @SuppressWarnings("deprecation")
    public static void sendNotificationToGCMserver(String googleApiKey, Set<String> registrationIds, String messageKey, String content, String title)
    {
        try
        { // Below is a good tutorial , how to post json data
          // http://hmkcode.com/android-send-json-data-to-server/

            String url = "https://android.googleapis.com/gcm/send";
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod(url);
            JSONObject mainData = new JSONObject();
            try
            {
                JSONObject data = new JSONObject();
                data.putOpt(messageKey, content);
                data.putOpt("title", title);
                JSONArray regIds = new JSONArray();
                for (String s : registrationIds)
                {
                    regIds.put(s);
                }
                LOG.info("regIds : "+regIds.toString());
                mainData.put("registration_ids", regIds);
                mainData.put("data", data);
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            post.setRequestBody(mainData.toString());
            post.setRequestHeader("Authorization", "key=" + googleApiKey);
            post.setRequestHeader("Content-Type", "application/json");
            int executeMethod = client.executeMethod(post);
            LOG.info("response code =" + executeMethod);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
