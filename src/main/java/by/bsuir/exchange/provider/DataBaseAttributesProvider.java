package by.bsuir.exchange.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DataBaseAttributesProvider {
    private static final String BUNDLE_NAME = "db";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    private static final Map<String, String> usersColumns = new HashMap<>();

    public static final String DRIVER_NAME = "DRIVER_NAME";
    public static final String DATABASE_URL = "DATABASE_URL";

    public static final String USER_TABLE = "user";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String ROLE = "ROLE";

    static {
        usersColumns.put(EMAIL, "email");
        usersColumns.put(PASSWORD, "password");
        usersColumns.put(ROLE, "role");
    }

    public static String getProperty(String name){
        return resourceBundle.getString(name);
    }

    public static String getColumnName(String tableName, String columnName){
        Map<String, String> columnNames = null;
        switch (tableName){
            case USER_TABLE :{
                columnNames = usersColumns;
                break;
            }
        }
        String column = columnNames.get(columnName);
        String key = String.format("%s.%s", tableName, column);
        return resourceBundle.getString(key);
    }
}
