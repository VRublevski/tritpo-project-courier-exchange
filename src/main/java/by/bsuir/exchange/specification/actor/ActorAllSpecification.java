package by.bsuir.exchange.specification.actor;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorAllSpecification implements Specification<ActorBean, PreparedStatement, Connection> {
    private String query;
    private Connection connection;

    public ActorAllSpecification(String query) {
        this.query = query;
    }

    @Override
    public boolean specify(ActorBean bean){
        return true;
    }

    @Override
    public PreparedStatement specify() throws SQLException {
        return connection.prepareStatement(query);
    }

    @Override
    public void setHelperObject(Connection connection){
        this.connection = connection;
    }
}
