package by.bsuir.exchange.repository.specification;

import by.bsuir.exchange.bean.CourierBean;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CourierByUserIdSpecification implements Specification<CourierBean, PreparedStatement, Connection>{
    private final static String QUERY = "SELECT * FROM couriers WHERE user_id = ?";

    private Connection connection;
    private long userId;

    public CourierByUserIdSpecification(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean specify(CourierBean entity) {
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
