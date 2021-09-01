package com.zebrands.mx.framework.utils;

import java.io.IOException;
import java.util.logging.Level;

public class PropertiesLoader {
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(PropertiesLoader.class.getName());
    private static java.util.Properties properties;

    public synchronized static String getProperty(String id){
        if (properties != null)
            return properties.getProperty(id);

        properties = new java.util.Properties();

        try(java.io.InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream("application.properties")){
            if(input != null)
                properties.load(input);
            return properties.getProperty(id);
        }
        catch(IOException ioe){
            LOG.log(Level.SEVERE, ioe.getMessage(), ioe);
            properties = null;
        }
        return null;
    }

}
