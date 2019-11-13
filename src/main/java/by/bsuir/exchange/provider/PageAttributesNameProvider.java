package by.bsuir.exchange.provider;


import java.util.HashMap;
import java.util.Map;


public class PageAttributesNameProvider {
    private static final Map<String, String> loginPage = new HashMap<>();

    public static final String LOGIN_PAGE = "LOGIN_PAGE";
    public static final String CREDENTIAL_ATTRIBUTE = "CREDENTIAL_ATTRIBUTE";


    static {
        loginPage.put(CREDENTIAL_ATTRIBUTE, "credential");
    }

    public static String getProperty(String page, String attribute){
        String result = null;
        switch (page){
            case LOGIN_PAGE : {
                result = loginPage.get(attribute);
                break;
            }
        }
        return result;
    }
}
