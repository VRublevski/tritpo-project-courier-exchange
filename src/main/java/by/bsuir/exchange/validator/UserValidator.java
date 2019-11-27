package by.bsuir.exchange.validator;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.entity.RoleEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}" +
                                                "\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=\\S+$).{6,16}$";

    public static boolean validate(UserBean userBean){
        boolean isValid = true;
        if (userBean.getEmail() != null){
            isValid &= validateEmail(userBean);
        }
        if (userBean.getPassword() != null){
            isValid &= validatePassword(userBean);
        }
        if (userBean.getRole() != null){
            isValid &= validateRole(userBean);
        }
        return isValid;
    }

    private static boolean validateEmail(UserBean credential){
        String email = credential.getEmail();
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private static boolean validatePassword(UserBean credential){
        String password = credential.getPassword();
        Pattern p = Pattern.compile(PASSWORD_PATTERN);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private static boolean validateRole(UserBean user){
        String roleString = user.getRole();
        boolean status;
        try {
            RoleEnum.valueOf(roleString.toUpperCase());
            status = true;
        }catch (IllegalArgumentException e){
            status = false;
        }
        return status;
    }
}
