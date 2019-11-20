package by.bsuir.exchange.repository.specification;

import by.bsuir.exchange.bean.DeliveryBean;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeliveryByCourierIdSpecification implements Specification<DeliveryBean, PreparedStatement, Connection> {
    private final static String QUERY = "SELECT * FROM deliveries WHERE courier_id = ?";

    private Connection connection;
    private long courierId;

    public DeliveryByCourierIdSpecification(long courierId) {
        this.courierId = courierId;
    }

    @Override
    public boolean specify(DeliveryBean entity) {
        return courierId == entity.getClientId();
    }

    @Override
    public PreparedStatement specify() throws Exception {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setLong(1, courierId);
        return statement;
    }

    @Override
    public void setHelperObject(Connection obj) {
        connection = obj;
    }
}
