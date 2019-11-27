package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.OfferBean;
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

public class OfferSqlRepository extends SqlRepository<OfferBean> {

    public OfferSqlRepository() throws RepositoryInitializationException {
        super();
    }

    @Override
    public Optional<List<OfferBean>> process(ResultSet resultSet) throws SQLException {
        Optional< List< OfferBean> > optionList = Optional.empty();
        List< OfferBean > offers = new LinkedList<>();
        while (resultSet.next()){
            String table = DataBaseAttributesProvider.OFFER_TABLE;
            String column = DataBaseAttributesProvider.ID;
            String columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long id = resultSet.getLong(columnName);

            column = DataBaseAttributesProvider.TRANSPORT;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String transport = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.PRICE;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            double price = resultSet.getDouble(columnName);

            column = DataBaseAttributesProvider.COURIER_ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long courierId = resultSet.getLong(columnName);

            OfferBean courier = new OfferBean(id, transport, price, courierId);
            offers.add(courier);
        }

        if (offers.size() != 0 ){
            optionList = Optional.of(offers);
        }
        return optionList;
    }

    @Override
    public void add(OfferBean bean) throws RepositoryOperationException {
        String template = "INSERT INTO offers (price, transport, courier_id) VALUES (?, ?, ?)";
        try{
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, bean.getPrice());
            statement.setString(2, bean.getTransport());
            statement.setLong(3, bean.getCourierId());
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
    public void update(OfferBean bean) throws RepositoryOperationException {
        String template = "UPDATE offers SET price=?, transport=? WHERE id=?";
        try{
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template);
            statement.setDouble(1, bean.getPrice());
            statement.setString(2, bean.getTransport());
            statement.setLong(3, bean.getId());
            statement.executeUpdate();
            pool.releaseConnection(connection);
        } catch (PoolTimeoutException | SQLException | PoolOperationException e) {
            throw new RepositoryOperationException(e);
        }
    }
}
