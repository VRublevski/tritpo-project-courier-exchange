package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.ImageBean;
import by.bsuir.exchange.pool.GlobalConnectionPool;
import by.bsuir.exchange.pool.exception.PoolInitializationException;
import by.bsuir.exchange.pool.exception.PoolTimeoutException;
import by.bsuir.exchange.provider.DataBaseAttributesProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ImageSqlRepository extends SqlRepository<ImageBean> {

    public ImageSqlRepository() throws RepositoryInitializationException {
        super();
    }


    @Override
    public Optional<List<ImageBean>> process(ResultSet resultSet) throws SQLException {
        Optional< List< ImageBean> > optionList = Optional.empty();
        List< ImageBean > images = new LinkedList<>();
        while (resultSet.next()){
            String table = DataBaseAttributesProvider.IMAGES_TABLE;
            String column = DataBaseAttributesProvider.ID;
            String columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long id = resultSet.getLong(columnName);

            column = DataBaseAttributesProvider.ROLE;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String role = resultSet.getString(columnName);

            column = DataBaseAttributesProvider.ROLE_ID;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            long roleId = resultSet.getLong(columnName);

            column = DataBaseAttributesProvider.FILE_NAME;
            columnName = DataBaseAttributesProvider.getColumnName(table, column);
            String fileName = resultSet.getString(columnName);

            ImageBean image = new ImageBean(id, roleId, role, fileName);
            images.add(image);
        }
        if (images.size() != 0 ){
            optionList = Optional.of(images);
        }
        return optionList;
    }

    @Override
    public void add(ImageBean entity) throws RepositoryOperationException {
        String template = "INSERT INTO images (role, role_id, file_name) VALUES (?, ?, ?)";
        try{
            GlobalConnectionPool pool = GlobalConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getRole().toUpperCase());
            statement.setLong(2, entity.getRoleId());
            statement.setString(3, entity.getFileName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0){
                throw new RepositoryOperationException("Unable to perform operation");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                entity.setId(generatedKeys.getLong(1));
            }
            pool.releaseConnection(connection);
        } catch (PoolInitializationException | PoolTimeoutException | SQLException e) {
            throw new RepositoryOperationException(e);
        }
    }

    @Override
    public void update(ImageBean entity) throws RepositoryOperationException {
        String template = "UPDATE images SET file_name=? WHERE role = ? AND role_id = ?";
        try{
            GlobalConnectionPool pool = GlobalConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(template);
            statement.setString(1, entity.getFileName());
            statement.setString(2, entity.getRole());
            statement.setLong(3, entity.getRoleId());
            statement.executeUpdate();
            pool.releaseConnection(connection);
        } catch (PoolInitializationException | PoolTimeoutException | SQLException e) {
            throw new RepositoryOperationException(e);
        }
    }

}
