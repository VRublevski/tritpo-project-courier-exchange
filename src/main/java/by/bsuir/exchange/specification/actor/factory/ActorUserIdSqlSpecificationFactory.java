package by.bsuir.exchange.specification.actor.factory;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.specification.Specification;
import by.bsuir.exchange.specification.actor.ActorByUserIdSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActorUserIdSqlSpecificationFactory {
    private static final String QUERY = "SELECT * FROM %s WHERE user_id = ?";

    public static Specification<ActorBean, PreparedStatement, Connection> getSpecification(RoleEnum role, long userId) {
        String roleString = role.toString().toLowerCase();
        String template = String.format(QUERY, roleString);
        ActorByUserIdSpecification specification = new ActorByUserIdSpecification(userId);
        specification.setQuery(template);
        return specification;
    }
}
