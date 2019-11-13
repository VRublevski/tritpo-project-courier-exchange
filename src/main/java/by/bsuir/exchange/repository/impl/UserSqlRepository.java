package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.provider.DataBaseAttributesProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;

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
            String login = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.PASSWORD;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String password = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.ROLE;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String roleString = resultSet.getString(columnName);
            RoleEnum role = RoleEnum.valueOf(roleString.toUpperCase());
            UserBean user = new UserBean(login, password, role);
            optionUser = Optional.of(user);
        }
        return optionUser;
    }
}
