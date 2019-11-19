package by.bsuir.exchange.provider;


import java.util.ResourceBundle;

public class ConfigurationProvider{
    private static final String BUNDLE_NAME = "config";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
    public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
    public static final String REGISTER_PAGE_PATH = "REGISTER_PAGE_PATH";
    public static final String DELIVERIES_PAGE_PATH = "DELIVERIES_PAGE_PATH";
    public static final String COURIER_PAGE_PATH = "COURIER_PAGE_PATH";
    public static final String CABINET_PAGE_PATH = "CABINET_PAGE_PATH";
    public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
    public static final String HOME_PAGE_PATH = "HOME_PAGE_PATH";

    public static String getProperty(String name){
        return resourceBundle.getString(name);
    }
}
