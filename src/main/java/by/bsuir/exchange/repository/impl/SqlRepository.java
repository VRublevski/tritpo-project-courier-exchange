package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.pool.GlobalConnectionPool;
import by.bsuir.exchange.pool.ConnectionPool;
import by.bsuir.exchange.pool.exception.PoolDestructionException;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolOperationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;
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

    public <T2> SqlRepository<T> pack(SqlRepository<T2> other) { //Two only
        ConnectionPool localPool = new ConnectionPool(){
            private static final int ALL_DONE = 2;
            private Connection connection;
            private int statusOperations;
            private int statusClosed;

            {
                outer = SqlRepository.this.pool;
            }

            @Override
            public Connection getConnection() throws PoolOperationException, PoolTimeoutException {
                if (connection == null){
                    try {
                        connection = outer.getConnection();
                        connection.setAutoCommit(false);
                    } catch (SQLException e) {
                        throw new PoolOperationException(e);
                    }
                }
                return connection;
            }

            @Override
            public void releaseConnection(Connection con) throws PoolOperationException, PoolTimeoutException {
                ++statusOperations;
                if (statusOperations == ALL_DONE){
                    try {
                        connection.commit();
                        outer.releaseConnection(connection);
                        connection = null;
                    } catch ( SQLException e) {
                        throw new PoolOperationException(e);
                    }
                }
            }

            @Override
            public void closePool() throws PoolOperationException, PoolDestructionException {
                ++statusClosed;
                if (statusClosed == ALL_DONE){
                    if (connection != null){
                        try {
                            outer.releaseConnection(connection);
                        } catch (PoolTimeoutException e) {
                            throw new PoolDestructionException(e);
                        }
                    }
                }
            }
        };
        this.pool = localPool;
        other.pool = localPool;
        return this;
    }
}
