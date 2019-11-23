package by.bsuir.exchange.specification.client;

import by.bsuir.exchange.bean.ClientBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClientByIdSpecification implements Specification<ClientBean, PreparedStatement, Connection> {
    private final static String QUERY = "SELECT * FROM clients WHERE id = ?";

    private Connection connection;
    private long id;

    public ClientByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public boolean specify(ClientBean entity) {
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
