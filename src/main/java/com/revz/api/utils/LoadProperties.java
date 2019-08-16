package com.revz.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
    private static Properties properties;

    /**
     * Loads the properties file from the project main/resources based on
     * @param PropertyFileLocation
     */
    public static void loadPropertiesAsResource(String PropertyFileLocation) {
        properties = new Properties();
        try(final InputStream stream =LoadProperties.class.getResourceAsStream(PropertyFileLocation))
        {
            properties.load(stream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the key from environment.properties file related to chosen profile
     * @param key
     */
    public static String getProp(String key) {
        if ((key == null) || key.isEmpty()) {
            return "";
        } else {
            return properties.getProperty(key);
        }
    }
}