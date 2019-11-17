package by.bsuir.exchange.validator;

import by.bsuir.exchange.bean.ClientBean;
import by.bsuir.exchange.bean.CourierBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourierValidator {
    private static final String NAME_PATTERN = "^[A-Z][a-z]*$";

    public static boolean validate(CourierBean courierBean){
        boolean isValid = true;
        if (courierBean.getName() != null){
            isValid &= validateName(courierBean);
        }
        if (courierBean.getSurname() != null){
            isValid &= validateSurname(courierBean);
        }
        return isValid;
    }

    private static boolean validateName(CourierBean courierBean){
        String name = courierBean.getName();
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    private static boolean validateSurname(CourierBean courierBean){
        String surname = courierBean.getSurname();
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(surname);
        return m.matches();
    }
}
