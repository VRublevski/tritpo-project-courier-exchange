package by.bsuir.exchange.specification.actor;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActorByIdSpecification implements Specification<ActorBean, PreparedStatement, Connection> {
    private final static String QUERY = "SELECT * FROM clients WHERE id = ?";

    private String query;
    private Connection connection;
    private long id;

    public ActorByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public boolean specify(ActorBean entity) {
        return id == entity.getId();
    }

    @Override
    public PreparedStatement specify() throws Exception {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        return statement;
    }

    @Override
    public void setHelperObject(Connection obj) {
        connection = obj;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
