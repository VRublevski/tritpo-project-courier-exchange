package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.RequestAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.factory.ActorSqlRepositoryFactory;
import by.bsuir.exchange.repository.impl.SqlRepository;
import by.bsuir.exchange.specification.Specification;
import by.bsuir.exchange.specification.actor.factory.ActorIdSqlSpecificationFactory;
import by.bsuir.exchange.specification.actor.factory.ActorUserIdSqlSpecificationFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class ActorManager implements CommandHandler {
    private static final double ZERO = 0;
    private static ActorManager instance;

    public static ActorManager getInstance() throws ManagerInitializationException {
        if (instance == null){
            SqlRepository<ActorBean> clientRepository;
            SqlRepository<ActorBean> courierRepository;
            try {
                clientRepository = ActorSqlRepositoryFactory.getRepository(RoleEnum.CLIENT);
                courierRepository = ActorSqlRepositoryFactory.getRepository(RoleEnum.COURIER);
            } catch (RepositoryInitializationException e) {
                throw new ManagerInitializationException(e);
            }
            instance = new ActorManager(clientRepository, courierRepository);
        }
        return instance;
    }

    private SqlRepository<ActorBean> clientRepository;
    private SqlRepository<ActorBean> courierRepository;

    private ActorManager(SqlRepository<ActorBean> clientRepository, SqlRepository<ActorBean> courierRepository){
        this.clientRepository = clientRepository;
        this.courierRepository = courierRepository;
    }

    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
        boolean status;
        HttpSession session = request.getSession();
        RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
        SqlRepository<ActorBean> repository = role == RoleEnum.CLIENT? clientRepository : courierRepository;
        switch (command){
            case REGISTER:{
                status = register(request, repository);
                break;
            }
            case LOGIN: {
                status = login(request, repository);
                break;
            }
            case UPDATE_PROFILE_COURIER:
            case UPDATE_PROFILE_CLIENT: {
                status = updateProfile(request, repository);
                break;
            }
            default: {
                throw new ManagerOperationException("Unexpected command");
            }
        }
        return status;
    }

    private boolean login(HttpServletRequest request, SqlRepository<ActorBean> repository) throws ManagerOperationException {
        boolean status = false;
        UserBean user = (UserBean) request.getAttribute(PageAttributesNameProvider.USER_ATTRIBUTE);
        long userId = user.getId();
        HttpSession session = request.getSession();
        RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
        Specification<ActorBean, PreparedStatement, Connection> specification = ActorUserIdSqlSpecificationFactory.getSpecification(role, userId);
        try {
            Optional<List<ActorBean> > clientsOptional = repository.find(specification);
            if (clientsOptional.isPresent()){
                ActorBean client = clientsOptional.get().get(0);
                request.setAttribute(RequestAttributesNameProvider.ACTOR, client);
                session = request.getSession();
                session.setAttribute(SessionAttributesNameProvider.ID, client.getId());
                status = true;
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return status;
    }


    private boolean register(HttpServletRequest request, SqlRepository<ActorBean> repository) throws ManagerOperationException {
        String userAttribute = PageAttributesNameProvider.USER_ATTRIBUTE;
        UserBean user = (UserBean) request.getAttribute(userAttribute);
        long userId = user.getId();

        String attribute = getActorAttribute(request);
        ActorBean actor = (ActorBean) request.getAttribute(attribute);
        actor.setUserId(userId);
        actor.setBalance(ZERO);
        try {
            repository.add(actor);
            request.setAttribute(RequestAttributesNameProvider.ACTOR, actor);
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttributesNameProvider.ID,  actor.getId());
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return true;
    }

    private boolean updateProfile(HttpServletRequest request, SqlRepository<ActorBean> repository) throws ManagerOperationException {
        String attribute = getActorAttribute(request);
        ActorBean actor = (ActorBean) request.getAttribute(attribute);
        HttpSession session = request.getSession();
        long id = (long) session.getAttribute(SessionAttributesNameProvider.ID);
        actor.setId(id);
        RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
        boolean status;
        try {
            Specification<ActorBean, PreparedStatement, Connection> actorByIdSpecification = ActorIdSqlSpecificationFactory.getSpecification(role, id);
            Optional< List<ActorBean> >  optionalCouriers = repository.find(actorByIdSpecification);
            if (optionalCouriers.isPresent()){
                ActorBean clientFound = optionalCouriers.get().get(0);
                if (actor.getName().isEmpty()){
                    actor.setName(clientFound.getName());
                }
                if (actor.getSurname().isEmpty()){
                    actor.setSurname(clientFound.getSurname());
                }
                repository.update(actor);
                request.setAttribute(RequestAttributesNameProvider.ACTOR, actor);
                status = true;
            }else{
                status = false;
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return status;
    }

    private String getActorAttribute(HttpServletRequest request){
        HttpSession session = request.getSession();
        RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
        return role == RoleEnum.CLIENT ? PageAttributesNameProvider.CLIENT_ATTRIBUTE : PageAttributesNameProvider.COURIER_ATTRIBUTE;
    }
}
