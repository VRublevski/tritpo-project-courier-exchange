package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.CredentialBean;
import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.SqlRepository;
import by.bsuir.exchange.repository.impl.UserSqlRepository;
import by.bsuir.exchange.repository.specification.Specification;
import by.bsuir.exchange.repository.specification.UserByEmailSqlSpecification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionManager implements CommandHandler {
    private static HttpSessionManager instance;

    public static HttpSessionManager getInstance() throws ManagerInitializationException {
        if (instance == null){
            SqlRepository<UserBean> userRepository;
            try {
                userRepository = new UserSqlRepository();
            } catch (RepositoryInitializationException e) {
                throw new ManagerInitializationException(e);
            }
            instance = new HttpSessionManager(userRepository);
        }
        return instance;
    }

    private SqlRepository<UserBean> repository;

    private HttpSessionManager(SqlRepository<UserBean> repository){
        this.repository = repository;
    }

    public boolean login(HttpServletRequest request, CredentialBean credential) throws ManagerOperationException {
        String email = credential.getEmail();
        String password = credential.getPassword();
        Specification userLoginSpecification = new UserByEmailSqlSpecification(email);
        try{
            Optional<UserBean> userOption = repository.find(userLoginSpecification);
            if (!userOption.isPresent()){
                return false;
            }
            UserBean user = userOption.get();
            if (user.getPassword().equals(password)){
                HttpSession session = request.getSession();
                String roleAttribute = SessionAttributesNameProvider.getProperty(SessionAttributesNameProvider.ROLE);
                session.setAttribute(roleAttribute, user.getRole());
                return true;
            }else{
                return false;
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
    }

    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
        boolean status;
        switch (command){
            case LOGIN: {
                CredentialBean credential = (CredentialBean) request.getAttribute("credential");
                status = login(request, credential);
                break;
            }
            default: {
                throw new ManagerOperationException("Unexpected command");
            }
        }
        return status;
    }
}
