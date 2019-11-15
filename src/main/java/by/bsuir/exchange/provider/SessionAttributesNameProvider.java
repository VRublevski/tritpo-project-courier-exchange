package by.bsuir.exchange.provider;

import java.util.HashMap;
import java.util.Map;

public class SessionAttributesNameProvider {
    private static final Map<String, String> attributes = new HashMap<>();

    public static final String ROLE = "ROLE";
    public static final String LANG = "LANG";
    public static final String RU = "RU";
    public static final String EN = "EN";

    static {
        attributes.put(ROLE, "role");
        attributes.put(LANG, "lang");
        attributes.put(RU, "ru_RU");
        attributes.put(EN, "en_EN");
    }

    public static String getProperty(String name){
        return attributes.get(name);
    }
}
