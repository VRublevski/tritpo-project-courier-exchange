package by.bsuir.exchange.specification.user;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAllSpecification implements Specification<UserBean, PreparedStatement, Connection> {
    private final static String LOGIN_QUERY = "SELECT * FROM users WHERE role <> 'ADMIN'";  //FIXME archive

    private Connection connection;

    public UserAllSpecification() { }

    @Override
    public boolean specify(UserBean bean){
        return true;
    }

    @Override
    public PreparedStatement specify() throws SQLException {
        return connection.prepareStatement(LOGIN_QUERY);
    }

    @Override
    public void setHelperObject(Connection connection){
        this.connection = connection;
    }
}
