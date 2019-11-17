package by.bsuir.exchange.chain;

import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.logic.PermissionChecker;
import by.bsuir.exchange.manager.HttpSessionManager;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.validator.UserValidator;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

public class ChainFactory { //Load on servlet initialization
    /*Chains*/
    private static CommandHandler sessionChain;
    private static CommandHandler emptyChain;

    /*Bean creators*/
    private static CommandHandler userBeanCreator;

    /*Validators*/
    private static CommandHandler userBeanValidator;

    /*Permissions checkers*/
    private static CommandHandler permissionChecker;

    static {
        initBeanCreators();
        initPermissionCheckers();
        initValidators();
    }

    public static CommandHandler getChain(CommandEnum command) throws ManagerInitializationException {
        CommandHandler chain;
        switch (command){
            case LOGIN:
            case REGISTER: {
                if (sessionChain == null){
                    createSessionChain();
                }
                chain = sessionChain;
                break;
            }
            case SET_LOCALE:{
                chain = HttpSessionManager.getInstance();
                break;
            }
            default:{
                createEmptyChain();
                chain = emptyChain;
                break;
            }
        }
        return chain;
    }

    private static void initBeanCreators(){
        userBeanCreator = (request, command) -> {
            UserBean user = new UserBean();
            try {
                BeanUtils.populate(user, request.getParameterMap());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            String page = PageAttributesNameProvider.REGISTER_PAGE;
            String attributeProperty = PageAttributesNameProvider.USER_ATTRIBUTE;
            String attribute = PageAttributesNameProvider.getProperty(page, attributeProperty);
            request.setAttribute(attribute, user);
            return true;
        };
    }

    private static void initValidators(){
        userBeanValidator = (request, command) -> {
            String page = PageAttributesNameProvider.LOGIN_PAGE;
            String attributeProperty = PageAttributesNameProvider.USER_ATTRIBUTE;
            String attribute = PageAttributesNameProvider.getProperty(page, attributeProperty);
            UserBean bean = (UserBean) request.getAttribute(attribute);
            return UserValidator.validate(bean);
        };
    }

    private static void initPermissionCheckers(){
        permissionChecker = (request, command) -> {
            String property = SessionAttributesNameProvider.ROLE;
            String attribute = SessionAttributesNameProvider.getProperty(property);
            HttpSession session = request.getSession();
            RoleEnum role = (RoleEnum) session.getAttribute(attribute);
            return PermissionChecker.getInstance().checkPermission(role, command);
        };
    }

    private static void createEmptyChain() {
        emptyChain = (request, command) -> false;
    }

    private static void createSessionChain() throws ManagerInitializationException {
        HttpSessionManager manager = HttpSessionManager.getInstance();
        sessionChain = userBeanCreator.chain(userBeanValidator).chain(permissionChecker).chain(manager);
    }
}
