package by.bsuir.exchange.repository.specification;

public class UserByEmailSqlSpecification implements Specification{
    private final static String LOGIN_QUERY = "SELECT * FROM users WHERE email = '%s'";

    private String email;

    public UserByEmailSqlSpecification(String email) {
        this.email = email;
    }

    public String specify(){
        return String.format(LOGIN_QUERY, email);
    }
}
