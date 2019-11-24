package by.bsuir.exchange.specification.actor.factory;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.specification.Specification;
import by.bsuir.exchange.specification.actor.ActorByIdSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActorIdSqlSpecificationFactory {
    private static final String QUERY = "SELECT * FROM %s WHERE id = ?";

    public static Specification<ActorBean, PreparedStatement, Connection> getSpecification(RoleEnum role, long id) {
        String roleString = role.toString().toLowerCase();
        String template = String.format(QUERY, roleString);
        ActorByIdSpecification specification = new ActorByIdSpecification(id);
        specification.setQuery(template);
        return specification;
    }
}
