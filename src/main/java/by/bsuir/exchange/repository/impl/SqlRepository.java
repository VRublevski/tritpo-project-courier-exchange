package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.pool.ConnectionPool;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.repository.Repository;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public abstract class SqlRepository<T> implements Repository<T, PreparedStatement, Connection> {
    private ConnectionPool pool;


    SqlRepository() throws RepositoryInitializationException {
        try {
            pool = ConnectionPool.getInstance();
        } catch (PoolInitializationException e) {
            throw new RepositoryInitializationException(e);
        }
    }

    @Override
    public Optional<T> find(Specification<T, PreparedStatement, Connection> specification) throws RepositoryOperationException {
        Optional<T> bean;
        PreparedStatement preparedStatement = null;
        try{
            Connection connection = pool.getConnection();
            specification.setHelperObject(connection);
            preparedStatement = specification.specify();
            ResultSet resultSet = preparedStatement.executeQuery();
            bean = process(resultSet);
            pool.releaseConnection(connection);
        } catch (Exception e) {
            throw new RepositoryOperationException(e);
        } finally {
            if (preparedStatement != null){
                try{
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    public abstract Optional<T> process(ResultSet resultSet) throws SQLException;
}
