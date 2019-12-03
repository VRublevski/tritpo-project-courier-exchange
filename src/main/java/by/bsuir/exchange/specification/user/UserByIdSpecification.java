package by.bsuir.exchange.specification.user;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserByIdSpecification implements Specification<UserBean, PreparedStatement, Connection> {
    private final static String QUERY = "SELECT * FROM users WHERE id = ? AND archival = 0";

    private Connection connection;
    private long id;

    public UserByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public boolean specify(UserBean entity) {
        return id == entity.getId();
    }

    @Override
    public PreparedStatement specify() throws Exception {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setLong(1, id);
        return statement;
    }

    @Override
    public void setHelperObject(Connection obj) {
        connection = obj;
    }

}
