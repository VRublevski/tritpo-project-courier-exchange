package by.bsuir.exchange.specification.actor.factory;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.specification.Specification;
import by.bsuir.exchange.specification.actor.ActorAllSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActorAllSqlSpecification {
    private static final String QUERY = "SELECT * FROM %s WHERE archival=0";

    public static Specification<ActorBean, PreparedStatement, Connection> getSpecification(RoleEnum role) {
        String roleString = role.toString().toLowerCase();
        String template = String.format(QUERY, roleString);
        return new ActorAllSpecification(template);
    }
}
