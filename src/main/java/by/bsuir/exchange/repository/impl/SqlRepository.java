package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.pool.ConnectionPool;
import by.bsuir.exchange.pool.GlobalConnectionPool;
import by.bsuir.exchange.pool.exception.PoolDestructionException;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolOperationException;
import by.bsuir.exchange.repository.Repository;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//FIXME add uses predefined names of tables

public abstract class SqlRepository<T> implements Repository<T, PreparedStatement, Connection> {
    ConnectionPool pool;

    SqlRepository() throws RepositoryInitializationException {
        try {
            pool = GlobalConnectionPool.getInstance();
        } catch (PoolInitializationException e) {
            throw new RepositoryInitializationException(e);
        }
    }

    @Override
    public Optional<List<T>> find(Specification<T, PreparedStatement, Connection> specification) throws RepositoryOperationException {
        Optional< List<T> > list;
        PreparedStatement preparedStatement = null;
        try{
            Connection connection = pool.getConnection();
            specification.setHelperObject(connection);
            preparedStatement = specification.specify();
            ResultSet resultSet = preparedStatement.executeQuery();
            list = process(resultSet);
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
        return list;
    }

    public abstract Optional< List<T> > process(ResultSet resultSet) throws SQLException;

    public void closeRepository() throws RepositoryOperationException {
        try {
            pool.closePool();
        } catch (PoolOperationException | PoolDestructionException e) {
            throw new RepositoryOperationException(e);
        }
    }

    public <T2> SqlRepository<T> pack(SqlRepository<T2> other) throws RepositoryInitializationException { //Two only
        try {
            if (this.pool == GlobalConnectionPool.getInstance()){
                this.pool = ConnectionPool.getLocalPool();
            }
            if (other.pool == GlobalConnectionPool.getInstance()){
                other.pool = ConnectionPool.getLocalPool();
            }
            this.pool.combine(other.pool);
        } catch (PoolInitializationException e) {
            throw new RepositoryInitializationException(e);
        }
        return this;
    }
}
