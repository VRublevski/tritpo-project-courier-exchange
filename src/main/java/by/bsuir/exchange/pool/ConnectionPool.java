package by.bsuir.exchange.pool;

import by.bsuir.exchange.pool.exception.PoolDestructionException;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolOperationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionPool {
    ConnectionPool outer;

    public abstract Connection getConnection() throws PoolTimeoutException, PoolOperationException;
    public abstract void releaseConnection(Connection con) throws PoolOperationException, PoolTimeoutException;
    public abstract void closePool() throws PoolOperationException, PoolDestructionException;

    public static ConnectionPool getLocalPool() throws PoolInitializationException {
        ConnectionPool localPool = new ConnectionPool(){
            private static final int ALL_DONE = 2;
            private Connection connection;
            private int statusOperations;
            private int statusClosed;

            @Override
            public Connection getConnection() throws PoolOperationException, PoolTimeoutException {
                if (connection == null){
                    try {
                        connection = outer.getConnection();
                        if (outer == GlobalConnectionPool.getInstance()){
                            connection.setAutoCommit(false);
                        }
                    } catch (SQLException | PoolInitializationException e) {
                        throw new PoolOperationException(e);
                    }
                }
                return connection;
            }

            @Override
            public void releaseConnection(Connection con) throws PoolOperationException, PoolTimeoutException {
                ++statusOperations;
            }

            @Override
            public void closePool() throws PoolOperationException, PoolDestructionException {
                if (connection != null) {
                    try {
                        if (outer == GlobalConnectionPool.getInstance()) {
                            connection.commit();
                            outer.releaseConnection(connection);
                        } else {
                            outer.releaseConnection(connection);
                        }
                        connection = null;
                    } catch (PoolTimeoutException | SQLException | PoolInitializationException e) {
                        throw new PoolDestructionException(e);
                    }
                }
            }
        };
        localPool.outer = GlobalConnectionPool.getInstance();
        return localPool;
    }

    public ConnectionPool combine(ConnectionPool other) throws PoolInitializationException {
        other.outer = this;
        return this;
    }
}
