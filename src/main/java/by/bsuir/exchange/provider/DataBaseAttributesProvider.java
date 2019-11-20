package by.bsuir.exchange.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DataBaseAttributesProvider {
    private static final String BUNDLE_NAME = "db";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    private static final Map<String, String> usersColumns = new HashMap<>();
    private static final Map<String, String > clientColumns = new HashMap<>();
    private static final Map<String, String > courierColumns = new HashMap<>();
    private static final Map<String, String > deliveryColumns = new HashMap<>();

    public static final String DRIVER_NAME = "DRIVER_NAME";
    public static final String DATABASE_URL = "DATABASE_URL";

    public static final String USER_TABLE = "user";
    public static final String ID = "ID";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String ROLE = "ROLE";

    public static final String CLIENT_TABLE = "client";
    public static final String NAME = "NAME";
    public static final String SURNAME = "SURNAME";
    public static final String USER_ID = "USER_ID";

    public static final String COURIER_TABLE = "courier";
    public static final String TRANSPORT = "TRANSPORT";

    public static final String DELIVERY_TABLE = "delivery";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CLIENT_FINISHED = "CLIENT_FINISHED";
    public static final String COURIER_ID = "COURIER_ID";
    public static final String COURIER_FINISHED = "COURIER_FINISHED";

    //FIXME why one should do this???
    static {
        usersColumns.put(ID, "id");
        usersColumns.put(EMAIL, "email");
        usersColumns.put(PASSWORD, "password");
        usersColumns.put(ROLE, "role");

        clientColumns.put(ID, "id");
        clientColumns.put(NAME, "name");
        clientColumns.put(SURNAME, "surname");
        clientColumns.put(USER_ID, "user_id");


        courierColumns.put(ID, "id");
        courierColumns.put(NAME, "name");
        courierColumns.put(SURNAME, "surname");
        courierColumns.put(TRANSPORT, "transport");
        courierColumns.put(USER_ID, "user_id");

        deliveryColumns.put(ID, "id");
        deliveryColumns.put(CLIENT_ID, "clientId");
        deliveryColumns.put(CLIENT_FINISHED, "clientFinished");
        deliveryColumns.put(COURIER_ID, "courierId");
        deliveryColumns.put(COURIER_FINISHED, "courierFinished");
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
            case CLIENT_TABLE : {
                columnNames = clientColumns;
                break;
            }
            case COURIER_TABLE : {
                columnNames = courierColumns;
                break;
            }
            case DELIVERY_TABLE: {
                columnNames = deliveryColumns;
                break;
            }
        }
        String column = columnNames.get(columnName);
        String key = String.format("%s.%s", tableName, column);
        return resourceBundle.getString(key);
    }
}
