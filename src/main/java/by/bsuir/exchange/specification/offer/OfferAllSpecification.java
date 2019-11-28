package by.bsuir.exchange.specification.offer;

import by.bsuir.exchange.bean.OfferBean;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OfferAllSpecification implements Specification<OfferBean, PreparedStatement, Connection> {
    private static final String QUERY = "SELECT * FROM offers";
    private Connection connection;

    @Override
    public boolean specify(OfferBean entity) {
        return true;
    }

    @Override
    public PreparedStatement specify() throws Exception {
        return connection.prepareStatement(QUERY);
    }

    @Override
    public void setHelperObject(Connection obj) {
        connection = obj;
    }
}
