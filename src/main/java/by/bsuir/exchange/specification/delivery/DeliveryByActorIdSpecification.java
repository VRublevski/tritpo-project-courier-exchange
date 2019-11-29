package by.bsuir.exchange.specification.delivery;

import by.bsuir.exchange.bean.DeliveryBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeliveryByActorIdSpecification implements Specification<DeliveryBean, PreparedStatement, Connection> {
    private static final String QUERY = "SELECT * FROM deliveries WHERE client_id=? AND courier_id=? AND archival = 0";

    private Connection connection;
    private long clientId;
    private long courierId;

    public DeliveryByActorIdSpecification(long clientId, long courierId) {
        this.clientId = clientId;
        this.courierId = courierId;
    }

    @Override
    public boolean specify(DeliveryBean entity) {
        return clientId == entity.getClientId() && courierId == entity.getCourierId();
    }

    @Override
    public PreparedStatement specify() throws Exception {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setLong(1, clientId);
        statement.setLong(2, courierId);
        return statement;
    }

    @Override
    public void setHelperObject(Connection obj) {
        connection = obj;
    }
}
