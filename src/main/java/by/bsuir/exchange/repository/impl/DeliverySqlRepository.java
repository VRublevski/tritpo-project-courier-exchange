package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.DeliveryBean;
import by.bsuir.exchange.pool.GlobalConnectionPool;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolOperationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;
import by.bsuir.exchange.provider.DataBaseAttributesProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DeliverySqlRepository extends SqlRepository<DeliveryBean> {

    public DeliverySqlRepository() throws RepositoryInitializationException {
        super();
    }

    @Override
    public Optional<List<DeliveryBean>> process(ResultSet resultSet) throws SQLException {
        Optional< List< DeliveryBean> > optionList = Optional.empty();
        List< DeliveryBean > deliveries = new LinkedList<>();
        while (resultSet.next()){
            String table = DataBaseAttributesProvider.DELIVERY_TABLE;
            String column = DataBaseAttributesProvider.ID;
            String columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long id = resultSet.getLong(columnName);

            column = DataBaseAttributesProvider.CLIENT_ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long clientId = resultSet.getLong(columnName);

            column = DataBaseAttributesProvider.CLIENT_FINISHED;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            boolean clientFinished = resultSet.getBoolean(columnName);

            column = DataBaseAttributesProvider.COURIER_ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long courierId = resultSet.getLong(columnName);

            column = DataBaseAttributesProvider.COURIER_FINISHED;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            boolean courierFinised = resultSet.getBoolean(columnName);

            column = DataBaseAttributesProvider.ARCHIVAL;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            boolean archival = resultSet.getBoolean(columnName);

            DeliveryBean courier = new DeliveryBean(id, clientId, clientFinished, courierId, courierFinised, archival);
            deliveries.add(courier);
        }

        if (deliveries.size() != 0 ){
            optionList = Optional.of(deliveries);
        }
        return optionList;
    }

    @Override
    public void add(DeliveryBean bean) throws RepositoryOperationException {
        String template = "INSERT INTO deliveries (client_id, client_finished, courier_id, courier_finished, archival) VALUES (?, ?, ?, ?, ?)";
        try{
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, bean.getClientId());
            statement.setBoolean(2, bean.getClientFinished());
            statement.setLong(3, bean.getCourierId());
            statement.setBoolean(4, bean.getCourierFinished());
            statement.setBoolean(5, bean.getArchival());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0){
                throw new RepositoryOperationException("Unable to perform operation");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                bean.setId(generatedKeys.getLong(1));
            }
            pool.releaseConnection(connection);
        } catch (PoolTimeoutException | SQLException | PoolOperationException e) {
            throw new RepositoryOperationException(e);
        }
    }

    @Override
    public void update(DeliveryBean entity) throws RepositoryOperationException {
        String template = "UPDATE deliveries SET client_finished=?, courier_finished=?, archival=? WHERE id=?";
        try{
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template);
            statement.setBoolean(1, entity.getClientFinished());
            statement.setBoolean(2, entity.getCourierFinished());
            statement.setBoolean(3, entity.getArchival());
            statement.setLong(4, entity.getId());
            statement.executeUpdate();
            pool.releaseConnection(connection);
        } catch (PoolTimeoutException | SQLException | PoolOperationException e) {
            throw new RepositoryOperationException(e);
        }
    }
}
