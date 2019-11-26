package by.bsuir.exchange.listener;

import by.bsuir.exchange.provider.DataBaseAttributesProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            String driverName = DataBaseAttributesProvider.getProperty(DataBaseAttributesProvider.DRIVER_NAME);
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            Logger logger = LogManager.getRootLogger();
            logger.fatal("Unable to load a sql driver");
        }

        try {
            String chainFactoryClassName = "by.bsuir.exchange.chain.ChainFactory";
            Class.forName(chainFactoryClassName);
        } catch (ClassNotFoundException e) {
            Logger logger = LogManager.getRootLogger();
            logger.fatal("Unable to load the chain factory class");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            String driverName = DataBaseAttributesProvider.getProperty(DataBaseAttributesProvider.DRIVER_NAME);
            Driver driver = DriverManager.getDriver(driverName);
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            Logger logger = LogManager.getRootLogger();
            logger.fatal("Unable to load a sql driver");
        }
    }
}
