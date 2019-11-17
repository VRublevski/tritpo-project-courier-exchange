package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
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

    public boolean login(HttpServletRequest request, UserBean userRequest) throws ManagerOperationException {
        Specification userEmailSpecification = new UserByEmailSqlSpecification(userRequest);
        try{
            Optional<UserBean> userOption = repository.find(userEmailSpecification);
            if (!userOption.isPresent()){
                return false;
            }
            UserBean userFound = userOption.get();
            String actualPassword = userRequest.getPassword();
            String expectedPassword = userFound.getPassword();
            if (actualPassword.equals(expectedPassword)){
                HttpSession session = request.getSession();
                String roleAttribute = SessionAttributesNameProvider.getProperty(SessionAttributesNameProvider.ROLE);
                session.setAttribute(roleAttribute, userFound.getRole());
                return true;
            }else{
                return false;
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
    }

    public boolean register(HttpServletRequest request, UserBean user) throws ManagerOperationException {
        try {
            repository.add(user);
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        HttpSession session = request.getSession();
        session.setAttribute("role", user.getRole().toUpperCase());
        return true;
    }

    public boolean changeLocale(HttpServletRequest request){
        String langProperty = SessionAttributesNameProvider.getProperty(SessionAttributesNameProvider.LANG);
        String langValue = request.getParameter(langProperty);
        String newLang = SessionAttributesNameProvider.getProperty(langValue.toUpperCase());
        HttpSession session = request.getSession();
        session.setAttribute(langProperty, newLang);
        return true;
    }

    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
        boolean status;
        switch (command){
            case LOGIN: {
                String page = PageAttributesNameProvider.LOGIN_PAGE;
                String attributeName = PageAttributesNameProvider.USER_ATTRIBUTE;
                String attribute = PageAttributesNameProvider.getProperty(page, attributeName);
                UserBean user = (UserBean) request.getAttribute(attribute);
                status = login(request, user);
                break;
            }
            case REGISTER: {
                String page = PageAttributesNameProvider.REGISTER_PAGE;
                String attributeName = PageAttributesNameProvider.USER_ATTRIBUTE;
                String attribute = PageAttributesNameProvider.getProperty(page, attributeName);
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
}
