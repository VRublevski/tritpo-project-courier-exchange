package by.bsuir.exchange.repository.specification;

import by.bsuir.exchange.bean.ClientBean;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClientByUserIdSpecification implements Specification<ClientBean, PreparedStatement, Connection> {
    private final static String QUERY = "SELECT * FROM clients WHERE user_id = ?";

    private Connection connection;
    private long userId;

    public ClientByUserIdSpecification(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean specify(ClientBean entity) {
        return userId == entity.getUserId();
    }

    @Override
    public PreparedStatement specify() throws Exception {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setLong(1, userId);
        return statement;
    }

    @Override
    public void setHelperObject(Connection obj) {
        connection = obj;
    }
}
