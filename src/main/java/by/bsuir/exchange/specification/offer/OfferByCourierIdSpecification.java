package by.bsuir.exchange.specification.offer;

import by.bsuir.exchange.bean.OfferBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OfferByCourierIdSpecification implements Specification<OfferBean, PreparedStatement, Connection> {
    private static final String QUERY = "SELECT * FROM offers WHERE courier_id = ? AND archival=0";

    private Connection connection;
    private long courierId;

    public OfferByCourierIdSpecification(long courierId) {
        this.courierId = courierId;
    }

    @Override
    public boolean specify(OfferBean entity) {
        return courierId == entity.getCourierId();
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
