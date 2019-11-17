package by.bsuir.exchange.provider;


import java.util.HashMap;
import java.util.Map;


public class PageAttributesNameProvider {
    private static final Map<String, String> globalPage = new HashMap<>();
    private static final Map<String, String> loginPage = new HashMap<>();
    private static final Map<String, String> registerPage = new HashMap<>();

    public static final String GLOBAL_PAGE = "GLOBAL_PAGE";
    public static final String LANG = "LANG";

    public static final String LOGIN_PAGE = "LOGIN_PAGE";
    public static final String REGISTER_PAGE = "REGISTER_PAGE";
    public static final String USER_ATTRIBUTE = "USER_ATTRIBUTE";


    static {
        globalPage.put(LANG, "lang");
        globalPage.put(USER_ATTRIBUTE, "user");
    }

    public static String getProperty(String page, String attribute){
        String result = null;
        if (!page.equals(GLOBAL_PAGE)){
            switch (page){
                case LOGIN_PAGE : {
                    result = loginPage.get(attribute);
                    break;
                }
                case REGISTER_PAGE : {
                    result = registerPage.get(attribute);
                    break;
                }
            }
        }
        if (result == null){
            result = globalPage.get(attribute);
        }
        return result;
    }
}
