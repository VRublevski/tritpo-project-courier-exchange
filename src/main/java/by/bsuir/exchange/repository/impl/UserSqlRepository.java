package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.pool.ConnectionPool;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;
import by.bsuir.exchange.provider.DataBaseAttributesProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;

import java.sql.*;
import java.util.Optional;

public class UserSqlRepository<T> extends SqlRepository<UserBean> {

    public UserSqlRepository() throws RepositoryInitializationException {
        super();
    }

    @Override
    public Optional<UserBean> process(ResultSet resultSet) throws SQLException {
        Optional<UserBean> optionUser = Optional.empty();
        if (resultSet.next()){
            String table = DataBaseAttributesProvider.USER_TABLE;
            String column = DataBaseAttributesProvider.NAME;
            String columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String name = resultSet.getString(columnName);
            column = DataBaseAttributesProvider.EMAIL;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String email = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.PASSWORD;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String password = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.ROLE;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String roleString = resultSet.getString(columnName);
            RoleEnum role = RoleEnum.valueOf(roleString.toUpperCase());
            UserBean user = new UserBean(name, email, password, role);
            optionUser = Optional.of(user);
        }
        return optionUser;
    }

    @Override
    public void add(UserBean user) throws RepositoryOperationException {    //FIXME close statement
        String template = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().toString());
            statement.execute();
            pool.releaseConnection(connection);
        } catch (PoolInitializationException | PoolTimeoutException | SQLException e) {
            throw new RepositoryOperationException(e);
        }

    }
}
