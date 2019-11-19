package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.CourierBean;
import by.bsuir.exchange.pool.ConnectionPool;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;
import by.bsuir.exchange.provider.DataBaseAttributesProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CourierSqlRepository extends SqlRepository<CourierBean> {

    public CourierSqlRepository() throws RepositoryInitializationException {
        super();
    }

    @Override
    public Optional<List<CourierBean>> process(ResultSet resultSet) throws SQLException {
        Optional< List< CourierBean> > optionList = Optional.empty();
        List< CourierBean > couriers = new LinkedList<>();
        while (resultSet.next()){
            String table = DataBaseAttributesProvider.COURIER_TABLE;
            String column = DataBaseAttributesProvider.NAME;
            String columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String name = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.SURNAME;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String surname = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.TRANSPORT;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String transport = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long id = resultSet.getLong(columnName);

            column = DataBaseAttributesProvider.USER_ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long user_id = resultSet.getLong(columnName);

            CourierBean courier = new CourierBean(id, name, surname, transport, user_id);
            couriers.add(courier);
        }

        if (couriers.size() != 0 ){
            optionList = Optional.of(couriers);
        }
        return optionList;
    }

    @Override
    public void add(CourierBean courier) throws RepositoryOperationException {
        String template = "INSERT INTO couriers (name, surname, transport, user_id) VALUES (?, ?, ?, ?)";
        try{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, courier.getName());
            statement.setString(2, courier.getSurname());
            statement.setString(3, courier.getTransport());
            statement.setLong(4, courier.getUserId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0){
                throw new RepositoryOperationException("Unable to perform operation");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                courier.setId(generatedKeys.getLong(1));
            }
            pool.releaseConnection(connection);
        } catch (PoolInitializationException | PoolTimeoutException | SQLException e) {
            throw new RepositoryOperationException(e);
        }
    }
}
