package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.ActorBean;
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

public class ActorSqlRepository extends SqlRepository<ActorBean> {
    private String updateTemplate;
    private String insertTemplate;

    public ActorSqlRepository(String updateTemplate, String insertTemplate) throws RepositoryInitializationException {
        super();
        this.updateTemplate = updateTemplate;
        this.insertTemplate = insertTemplate;
    }

    @Override
    public Optional<List<ActorBean>> process(ResultSet resultSet) throws SQLException {
        Optional< List<ActorBean> > optionList = Optional.empty();
        List<ActorBean> clients = new LinkedList<>();
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

            column = DataBaseAttributesProvider.BALANCE;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            double balance = resultSet.getDouble(columnName);

            column = DataBaseAttributesProvider.USER_ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long user_id = resultSet.getLong(columnName);

            ActorBean client = new ActorBean(id, name, surname, balance, user_id);
            clients.add(client);
        }
        if (clients.size() != 0 ){
            optionList = Optional.of(clients);
        }
        return optionList;
    }

    /*Sets id of the bean argument on success*/
    @Override
    public void add(ActorBean client) throws RepositoryOperationException {
        try{
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertTemplate, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setDouble(3, client.getBalance());
            statement.setLong(4, client.getUserId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0){
                throw new RepositoryOperationException("Unable to perform operation");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                client.setId(generatedKeys.getLong(1));
            }
            pool.releaseConnection(connection);
        } catch (PoolTimeoutException | SQLException | PoolOperationException e) {
            throw new RepositoryOperationException(e);
        }
    }

    @Override
    public void update(ActorBean entity) throws RepositoryOperationException {
        try {
            GlobalConnectionPool pool = GlobalConnectionPool.getInstance();
            Connection connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(updateTemplate);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setDouble(3, entity.getBalance());
            statement.setLong(4, entity.getId());
            statement.executeUpdate();
            pool.releaseConnection(connection);
        }catch (PoolInitializationException | PoolTimeoutException | SQLException e) {
            throw new RepositoryOperationException(e);
        }
    }


}
