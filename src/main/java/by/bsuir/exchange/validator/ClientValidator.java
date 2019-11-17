package by.bsuir.exchange.validator;

import by.bsuir.exchange.bean.ClientBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientValidator {
    private static final String NAME_PATTERN = "^[A-Z][a-z]*$";

    public static boolean validate(ClientBean clientBean){
        boolean isValid = true;
        if (clientBean.getName() != null){
            isValid &= validateName(clientBean);
        }
        if (clientBean.getSurname() != null){
            isValid &= validateSurname(clientBean);
        }
        return isValid;
    }

    private static boolean validateName(ClientBean clientBean){
        String name = clientBean.getName();
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    private static boolean validateSurname(ClientBean clientBean){
        String surname = clientBean.getSurname();
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(surname);
        return m.matches();
    }

}
