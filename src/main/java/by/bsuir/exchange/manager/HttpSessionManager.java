package by.bsuir.exchange.manager;

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
import by.bsuir.exchange.repository.impl.UserSqlRepository;
import by.bsuir.exchange.specification.Specification;
import by.bsuir.exchange.specification.user.UserByEmailSqlSpecification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class HttpSessionManager extends AbstractManager<UserBean> implements CommandHandler {

    public HttpSessionManager() throws ManagerInitializationException {
        try{
            this.repository = new UserSqlRepository();
        } catch (RepositoryInitializationException e) {
            throw new ManagerInitializationException(e);
        }
    }

    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
        boolean status;
        switch (command){
            case LOGIN: {
                String attribute = RequestAttributesNameProvider.USER_ATTRIBUTE;
                UserBean user = (UserBean) request.getAttribute(attribute);
                status = login(request, user);
                break;
            }
            case REGISTER: {
                String attribute = RequestAttributesNameProvider.USER_ATTRIBUTE;
                UserBean user = (UserBean) request.getAttribute(attribute);
                status = register(request, user);
                break;
            }
            case SET_LOCALE:{
                status = changeLocale(request);
                break;
            }
            default: {
                throw new ManagerOperationException("Unexpected command");
            }
        }
        return status;
    }

    private boolean login(HttpServletRequest request, UserBean userRequest) throws ManagerOperationException {
        Specification<UserBean, PreparedStatement, Connection> userEmailSpecification = new UserByEmailSqlSpecification(userRequest);
        try{
            Optional<List<UserBean>> userOption = repository.find(userEmailSpecification);
            if (!userOption.isPresent()){
                return false;
            }
            UserBean userFound = userOption.get().get(0);
            String actualPassword = userRequest.getPassword();
            String expectedPassword = userFound.getPassword();
            if (actualPassword.equals(expectedPassword)){
                HttpSession session = request.getSession();
                RoleEnum role = RoleEnum.valueOf(userFound.getRole().toUpperCase());
                session.setAttribute(SessionAttributesNameProvider.ROLE, role);
                request.setAttribute(RequestAttributesNameProvider.USER_ATTRIBUTE, userFound);
                return true;
            }else{
                return false;
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
    }

    private boolean register(HttpServletRequest request, UserBean user) throws ManagerOperationException {
        try {
            repository.add(user);
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        HttpSession session = request.getSession();
        RoleEnum role = RoleEnum.valueOf(user.getRole().toUpperCase());
        session.setAttribute(SessionAttributesNameProvider.ROLE, role);
        return true;
    }

    private boolean changeLocale(HttpServletRequest request){
        String langAttribute = SessionAttributesNameProvider.LANG;
        String newLang = request.getParameter(langAttribute);
        HttpSession session = request.getSession();
        session.setAttribute(langAttribute, newLang);
        return true;
    }
}
