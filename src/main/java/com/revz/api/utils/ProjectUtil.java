package com.revz.api.utils;
import static com.revz.api.utils.LoadProperties.loadPropertiesAsResource;


public class ProjectUtil {
    private static String ENV;
    private static String url;

    static {
        loadPropertiesAsResource("/properties/environment.properties");
        ENV = System.getProperty("environment",LoadProperties.getProp("environment"));
    }

    public String setEnvUrl(String environment){
        switch (environment){
            case "DEV" :
                url = "http://localhost/3000";
                break;
            case "PROD":
                url = "https://jsonplaceholder.typicode.com";
                break;
            default:
                url = "http://localhost/3000";
        }
        return url;
    }

    public String getUrlValue(){
       return url;
    }

    public ProjectUtil() {
        url = setEnvUrl(ENV);
    }

}
