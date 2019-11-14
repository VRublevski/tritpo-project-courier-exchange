package by.bsuir.exchange.validator;

import by.bsuir.exchange.bean.UserBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}" +
                                                "\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    private static final String NAME_PATTERN = "^[A-Za-z]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=\\S+$).{6,16}$";

    public static boolean validate(UserBean credential){
        boolean isValid = true;
        if (credential.getEmail() != null){
            isValid &= validateEmail(credential);
        }
        if (credential.getPassword() != null){
            isValid &= validatePassword(credential);
        }
        if (credential.getName() != null){
            isValid &= validateName(credential);
        }
        return isValid;
    }

    private static boolean validateEmail(UserBean credential){
        String email = credential.getEmail();
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private static boolean validateName(UserBean credential){
        String name = credential.getName();
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    private static boolean validatePassword(UserBean credential){
        String password = credential.getPassword();
        Pattern p = Pattern.compile(PASSWORD_PATTERN);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
