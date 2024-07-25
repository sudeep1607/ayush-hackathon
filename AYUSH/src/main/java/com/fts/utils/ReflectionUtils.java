package com.fts.utils;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author venkata.m
 * 
 */
public class ReflectionUtils
{

    private static final Log LOG = LogFactory.getLog(ReflectionUtils.class);

    /**
     * 
     * @param obj
     * @param method
     * @return
     */
    public static Method getMethod(Object obj, String method)
    {
        Method[] methodsList = obj.getClass().getMethods();
        for (int i = 0; i < methodsList.length; i++)
        {
            Method tmpMethod = methodsList[i];
            if (tmpMethod.getName().equals((method).trim()))
            {
                return tmpMethod;
            }
        }
        return null;
    }

    /**
     * 
     * @param method
     */
    @SuppressWarnings("rawtypes")
	public static void getParamters(Method method)
    {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class[] parameterTypes = method.getParameterTypes();

        int i = 0;
        for (Annotation[] annotations : parameterAnnotations)
        {
            Class parameterType = parameterTypes[i++];
            for (Annotation annotation : annotations)
			{
				/*
				 * if (annotation instanceof WebParam) { WebParam myAnnotation = (WebParam)
				 * annotation; LOG.debug("Method Parameter : " + parameterType.getName());
				 * LOG.debug("Param Annotation Name : " + myAnnotation.name()); }
				 */}
        }
    }
    

}
