package by.bsuir.exchange.specification.actor;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActorByUserIdSpecification implements Specification<ActorBean, PreparedStatement, Connection> {

    private String query;
    private Connection connection;
    private long userId;

    public ActorByUserIdSpecification(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean specify(ActorBean entity) {
        return userId == entity.getUserId();
    }

    @Override
    public PreparedStatement specify() throws Exception {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, userId);
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
