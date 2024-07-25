package com.fts.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

/**
 * @author rabindranath.s
 */
public class URLConnect
{
    private static final Logger LOG = Logger.getLogger(URLConnect.class);

    public static String send(String url)
    {
        InputStream inputStream = null;
        try
        {
            URL encodedUrl = new URL(url.replace(" ", "%20"));
            URLConnection connection = encodedUrl.openConnection();
            inputStream = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer out = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null)
            {
                out.append(line);
            }

            return out.toString();
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return "URL Connection Exception.";
        }
        finally
        {
            try
            {
                if (inputStream != null) inputStream.close();
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
            }
        }
    }

    /**
     * @author rabindranath.s
     * @param urlString
     * @return response
     * @throws Exception
     */
    public static String connect(String urlString)
    {
        String response = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.connect();
            urlConnection.setReadTimeout(20 * 1000);
            urlConnection.setConnectTimeout(20 * 1000);
            inputStream = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer out = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null)
            {
                out.append(line);
            }

            response = out.toString();
            in.close();

            return response;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return null;
        }
        finally
        {
            try
            {
                if (inputStream != null) inputStream.close();
                if (urlConnection != null) urlConnection.disconnect();
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
            }
        }
    }
}
