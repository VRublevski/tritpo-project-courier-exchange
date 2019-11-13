package by.bsuir.exchange.validator;

import by.bsuir.exchange.bean.CredentialBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialValidator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}" +
                                                "\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    private static final String NAME_PATTERN = "^[A-Za-z]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    public static boolean validate(CredentialBean credential){
        return validateEmail(credential) && validateName(credential) && validatePassword(credential);
    }

    private static boolean validateEmail(CredentialBean credential){
        String email = credential.getEmail();
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private static boolean validateName(CredentialBean credential){
        String name = credential.getName();
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    private static boolean validatePassword(CredentialBean credential){
        String password = credential.getPassword();
        Pattern p = Pattern.compile(PASSWORD_PATTERN);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
