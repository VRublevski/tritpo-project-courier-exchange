package by.bsuir.exchange.specification.user;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserByEmailSqlSpecification implements Specification<UserBean, PreparedStatement, Connection> {
    private final static String LOGIN_QUERY = "SELECT * FROM users WHERE email = ? AND archival=0";

    private String email;
    private Connection connection;

    public UserByEmailSqlSpecification(UserBean user) {
        this.email = user.getEmail();
    }

    @Override
    public boolean specify(UserBean bean){
        return email.equals(bean.getEmail());
    }

    @Override
    public PreparedStatement specify() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY);
        statement.setString(1, email);
        return statement;
    }

    @Override
    public void setHelperObject(Connection connection){
        this.connection = connection;
    }
}
