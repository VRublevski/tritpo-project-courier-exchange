package by.bsuir.exchange.provider;

import java.util.HashMap;
import java.util.Map;

public class SessionAttributesNameProvider {
    private static final Map<String, String> attributes = new HashMap<>();

    public static final String ROLE = "ROLE";


    static {
        attributes.put(ROLE, "role");
    }

    public static String getProperty(String name){
        return attributes.get(name);
    }
}
