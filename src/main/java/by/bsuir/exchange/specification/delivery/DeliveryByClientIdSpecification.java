package by.bsuir.exchange.specification.delivery;

import by.bsuir.exchange.bean.DeliveryBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeliveryByClientIdSpecification implements Specification<DeliveryBean, PreparedStatement, Connection> {
    private final static String QUERY = "SELECT * FROM deliveries WHERE client_id = ? AND archival = 0";

    private Connection connection;
    private long clientId;

    public DeliveryByClientIdSpecification(long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean specify(DeliveryBean entity) {
        return clientId == entity.getClientId();
    }

    @Override
    public PreparedStatement specify() throws Exception {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setLong(1, clientId);
        return statement;
    }

    @Override
    public void setHelperObject(Connection obj) {
        connection = obj;
    }
}
