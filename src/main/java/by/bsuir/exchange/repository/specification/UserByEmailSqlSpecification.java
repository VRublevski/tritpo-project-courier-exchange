package by.bsuir.exchange.repository.specification;

import by.bsuir.exchange.bean.UserBean;

public class UserByEmailSqlSpecification implements Specification{
    private final static String LOGIN_QUERY = "SELECT * FROM users WHERE email = '%s'";

    private String email;

    public UserByEmailSqlSpecification(UserBean user) {
        this.email = user.getEmail();
    }

    public String specify(){
        return String.format(LOGIN_QUERY, email);
    }
}
