package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.ClientBean;
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

public class ClientSqlRepository extends SqlRepository<ClientBean> {

    public ClientSqlRepository() throws RepositoryInitializationException {
        super();
    }

    @Override
    public Optional<List<ClientBean>> process(ResultSet resultSet) throws SQLException {
        Optional< List< ClientBean> > optionList = Optional.empty();
        List< ClientBean > clients = new LinkedList<>();
        while (resultSet.next()){
            String table = DataBaseAttributesProvider.CLIENT_TABLE;
            String column = DataBaseAttributesProvider.NAME;
            String columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String name = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.SURNAME;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String surname = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long id = resultSet.getLong(columnName);

            column = DataBaseAttributesProvider.USER_ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long user_id = resultSet.getLong(columnName);

            ClientBean client = new ClientBean(id, name, surname, user_id);
            clients.add(client);
        }
        if (clients.size() != 0 ){
            optionList = Optional.of(clients);
        }
        return optionList;
    }

    /*Sets id of the bean argument on success*/
    @Override
    public void add(ClientBean client) throws RepositoryOperationException {
        String template = "INSERT INTO clients (name, surname, user_id) VALUES (?, ?, ?)";
        try{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setLong(3, client.getUserId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0){
                throw new RepositoryOperationException("Unable to perform operation");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                client.setId(generatedKeys.getLong(1));
            }
            pool.releaseConnection(connection);
        } catch (PoolInitializationException | PoolTimeoutException | SQLException e) {
            throw new RepositoryOperationException(e);
        }
    }

    @Override
    public void update(ClientBean entity) {
        throw new UnsupportedOperationException();
    }
}
