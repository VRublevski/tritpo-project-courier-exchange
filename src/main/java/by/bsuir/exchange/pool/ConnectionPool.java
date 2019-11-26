package by.bsuir.exchange.pool;

import by.bsuir.exchange.pool.exception.PoolDestructionException;
import by.bsuir.exchange.pool.exception.PoolOperationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;

import java.sql.Connection;

public abstract class ConnectionPool {
    protected ConnectionPool outer;

    public abstract Connection getConnection() throws PoolTimeoutException, PoolOperationException;
    public abstract void releaseConnection(Connection con) throws PoolOperationException, PoolTimeoutException;
    public abstract void closePool() throws PoolOperationException, PoolDestructionException;
}
