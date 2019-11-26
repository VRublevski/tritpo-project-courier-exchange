package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.pool.ConnectionPool;
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

public class UserSqlRepository extends SqlRepository<UserBean> {

    public UserSqlRepository() throws RepositoryInitializationException {
        super();
    }

    @Override
    public Optional<List<UserBean>> process(ResultSet resultSet) throws SQLException {
        Optional< List< UserBean > > optionList = Optional.empty();
        List< UserBean > users = new LinkedList<>();
        while (resultSet.next()){
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

            column = DataBaseAttributesProvider.ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long id = resultSet.getLong(columnName);

            UserBean user = new UserBean(id, email, password, role);
            users.add(user);
        }
        if (users.size() != 0 ){
            optionList = Optional.of(users);
        }
        return optionList;
    }

    @Override
    public void add(UserBean user) throws RepositoryOperationException {    //FIXME close statement
        String template = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
        try{
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toUpperCase());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0){
                throw new RepositoryOperationException("Unable to perform operation");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                user.setId(generatedKeys.getLong(1));
            }
            pool.releaseConnection(connection);
        } catch (PoolTimeoutException | SQLException | PoolOperationException e) {
            throw new RepositoryOperationException(e);
        }
    }

    @Override
    public void update(UserBean entity) {
        throw new UnsupportedOperationException();
    }
}
