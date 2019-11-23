package by.bsuir.exchange.repository.impl;

import by.bsuir.exchange.bean.ImageBean;
import by.bsuir.exchange.provider.DataBaseAttributesProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(ImageBean entity) throws RepositoryOperationException {
        throw new UnsupportedOperationException();
    }

}
