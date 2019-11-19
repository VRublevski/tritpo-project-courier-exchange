package by.bsuir.exchange.repository.specification;

import by.bsuir.exchange.bean.CourierBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourierAllSpecification implements Specification<CourierBean, PreparedStatement, Connection> {
    private final static String ALL_QUERY = "SELECT * FROM couriers";

    private Connection connection;

    @Override
    public boolean specify(CourierBean bean){
        return true;
    }

    @Override
    public PreparedStatement specify() throws SQLException {
        return connection.prepareStatement(ALL_QUERY);
    }

    @Override
    public void setHelperObject(Connection connection){
        this.connection = connection;
    }
}
