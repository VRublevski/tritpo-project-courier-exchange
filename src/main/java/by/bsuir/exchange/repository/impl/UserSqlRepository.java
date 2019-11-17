package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.pool.ConnectionPool;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;
import by.bsuir.exchange.provider.DataBaseAttributesProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserSqlRepository extends SqlRepository<UserBean> {

    public UserSqlRepository() throws RepositoryInitializationException {
        super();
    }

    @Override
    public Optional<UserBean> process(ResultSet resultSet) throws SQLException {
        Optional<UserBean> optionUser = Optional.empty();
        if (resultSet.next()){
            String table = DataBaseAttributesProvider.USER_TABLE;
            String column = DataBaseAttributesProvider.EMAIL;
            String columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String email = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.PASSWORD;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String password = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.ROLE;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String role = resultSet.getString(columnName);
            UserBean user = new UserBean(email, password, role);
            optionUser = Optional.of(user);
        }
        return optionUser;
    }

    @Override
    public void add(UserBean user) throws RepositoryOperationException {    //FIXME close statement
        String template = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
        try{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toUpperCase());
            statement.execute();
            pool.releaseConnection(connection);
        } catch (PoolInitializationException | PoolTimeoutException | SQLException e) {
            throw new RepositoryOperationException(e);
        }

    }
}
