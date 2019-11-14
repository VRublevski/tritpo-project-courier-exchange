package by.bsuir.exchange.provider;


import java.util.HashMap;
import java.util.Map;


public class PageAttributesNameProvider {
    private static final Map<String, String> globalPage = new HashMap<>();
    private static final Map<String, String> loginPage = new HashMap<>();

    public static final String LOGIN_PAGE = "LOGIN_PAGE";
    public static final String USER_ATTRIBUTE = "CREDENTIAL_ATTRIBUTE";

    public static final String GLOBAL_PAGE = "GLOBAL_PAGE";
    public static final String LANG = "LANG";

    static {
        globalPage.put(LANG, "lang");
        loginPage.put(USER_ATTRIBUTE, "user");
    }

    public static String getProperty(String page, String attribute){
        String result = null;
        switch (page){
            case GLOBAL_PAGE:{
                result = globalPage.get(attribute);
                break;
            }
            case LOGIN_PAGE : {
                result = loginPage.get(attribute);
                break;
            }
        }
        return result;
    }
}
