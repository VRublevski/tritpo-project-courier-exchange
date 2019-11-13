package by.bsuir.exchange.pool;

import by.bsuir.exchange.pool.exception.PoolDestractionException;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;
import by.bsuir.exchange.provider.DataBaseAttributesProvider;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool{
    private static final int CAPACITY = 20;

    private static volatile ConnectionPool INSTANCE;
    private static ReentrantLock instanceLock = new ReentrantLock();
    private boolean isClosed;

    private LinkedBlockingQueue<Connection> connections;

    public static ConnectionPool getInstance() throws PoolInitializationException {
        if (INSTANCE == null){
            instanceLock.lock();
            if (INSTANCE == null){
                try {
                    INSTANCE = new ConnectionPool();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new PoolInitializationException(e);
                }
            }
            instanceLock.unlock();
        }

        return INSTANCE;
    }


    private ConnectionPool() throws SQLException, ClassNotFoundException {
        String url = DataBaseAttributesProvider.getProperty(DataBaseAttributesProvider.DATABASE_URL);
        String driverName = DataBaseAttributesProvider.getProperty(DataBaseAttributesProvider.DRIVER_NAME);
        connections = new LinkedBlockingQueue<>(CAPACITY);
        Class.forName(driverName);
        for (int i = 0; i < CAPACITY; i++){
            Connection connection =  DriverManager.getConnection(url);
            connections.add(connection);
        }
    }

    public Connection getConnection() throws PoolTimeoutException {
        Connection connection;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            throw new PoolTimeoutException(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws PoolTimeoutException {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            throw new PoolTimeoutException(e);
        }
    }

    public void closePool() throws PoolDestractionException {
        instanceLock.lock();
        try{
            if (!isClosed){
                String driverName = DataBaseAttributesProvider.getProperty(DataBaseAttributesProvider.DRIVER_NAME);
                Driver driver = DriverManager.getDriver(driverName);
                DriverManager.deregisterDriver(driver);
                for (Connection connection : connections){
                    connection.close();
                }
                isClosed = true;
            }
        instanceLock.unlock();
        } catch (SQLException e) {
            throw  new PoolDestractionException(e);
        }
    }
}
