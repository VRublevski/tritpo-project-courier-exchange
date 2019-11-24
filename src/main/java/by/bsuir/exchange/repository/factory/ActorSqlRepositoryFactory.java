package by.bsuir.exchange.repository.factory;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.impl.ActorSqlRepository;
import by.bsuir.exchange.repository.impl.SqlRepository;

public class ActorSqlRepositoryFactory {
    private static final String updateTemplate = "UPDATE %s SET name=?, surname=?, balance=? WHERE id=?";
    private static final String insertTemplate = "INSERT INTO %s (name, surname, balance, user_id) VALUES (?, ?, ?, ?)";

    public static SqlRepository<ActorBean> getRepository(RoleEnum role) throws RepositoryInitializationException {
        String roleString = role.toString().toLowerCase();
        String roleUpdateTemplate = String.format(updateTemplate, roleString);
        String roleInsertTemplate = String.format(insertTemplate, roleString);

        return new ActorSqlRepository(roleUpdateTemplate, roleInsertTemplate);
    }
}
