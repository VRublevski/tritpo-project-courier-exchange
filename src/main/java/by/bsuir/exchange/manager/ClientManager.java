package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.ClientBean;
import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.ClientSqlRepository;
import by.bsuir.exchange.repository.impl.SqlRepository;
import by.bsuir.exchange.specification.client.ClientByUserIdSpecification;
import by.bsuir.exchange.specification.Specification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class ClientManager implements CommandHandler {
    private static ClientManager instance;

    public static ClientManager getInstance() throws ManagerInitializationException {
        if (instance == null){
            SqlRepository<ClientBean> clientRepository;
            try {
                clientRepository = new ClientSqlRepository();
            } catch (RepositoryInitializationException e) {
                throw new ManagerInitializationException(e);
            }
            instance = new ClientManager(clientRepository);
        }
        return instance;
    }

    private SqlRepository<ClientBean> clientRepository;

    private ClientManager(SqlRepository<ClientBean> clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
        boolean status;
        switch (command){
            case REGISTER:{
                status = register(request);
                break;
            }
            case LOGIN: {
                status = login(request);
                break;
            }
            default: {
                throw new ManagerOperationException("Unexpected command");
            }
        }
        return status;
    }

    private boolean login(HttpServletRequest request) throws ManagerOperationException {
        boolean status = false;
        UserBean user = (UserBean) request.getAttribute(PageAttributesNameProvider.USER_ATTRIBUTE);
        long userId = user.getId();
        Specification<ClientBean, PreparedStatement, Connection> specification = new ClientByUserIdSpecification(userId);
        try {
            Optional<List< ClientBean > > clientsOptional = clientRepository.find(specification);
            if (clientsOptional.isPresent()){
                ClientBean client = clientsOptional.get().get(0);
                request.setAttribute(PageAttributesNameProvider.CLIENT_ATTRIBUTE, client);
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttributesNameProvider.ID, client.getId());
                status = true;
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return status;
    }


    private boolean register(HttpServletRequest request) throws ManagerOperationException {
        String userAttribute = PageAttributesNameProvider.USER_ATTRIBUTE;
        UserBean user = (UserBean) request.getAttribute(userAttribute);

        long userId = user.getId();
        ClientBean client = (ClientBean) request.getAttribute(PageAttributesNameProvider.CLIENT_ATTRIBUTE);
        client.setUserId(userId);
        try {
            clientRepository.add(client);
            request.setAttribute(PageAttributesNameProvider.CLIENT_ATTRIBUTE, client);
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttributesNameProvider.ID,  client.getId());
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return true;
    }
}
