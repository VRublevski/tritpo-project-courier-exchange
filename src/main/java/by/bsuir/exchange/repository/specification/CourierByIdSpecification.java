package by.bsuir.exchange.repository.specification;

import by.bsuir.exchange.bean.CourierBean;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CourierByIdSpecification implements Specification<CourierBean, PreparedStatement, Connection> {
    private final static String QUERY = "SELECT * FROM couriers WHERE id = ?";

    private Connection connection;
    private long id;

    public CourierByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public boolean specify(CourierBean entity) {
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
