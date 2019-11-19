package by.bsuir.exchange.chain;

import by.bsuir.exchange.bean.ClientBean;
import by.bsuir.exchange.bean.CourierBean;
import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.logic.PermissionChecker;
import by.bsuir.exchange.manager.ClientManager;
import by.bsuir.exchange.manager.CourierManager;
import by.bsuir.exchange.manager.HttpSessionManager;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.validator.ClientValidator;
import by.bsuir.exchange.validator.CourierValidator;
import by.bsuir.exchange.validator.UserValidator;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

public class ChainFactory { //Load on servlet initialization


    /*Chains*/
    private static CommandHandler emptyChain;

    private static CommandHandler sessionBranch;
    private static CommandHandler clientBranch;
    private static CommandHandler courierBranch;

    /*Managers*/

    /*BeanCreators*/

    /*Validators*/
    private static CommandHandler userBeanValidator;
    private static CommandHandler clientBeanValidator;
    private static CommandHandler courierBeanValidator;

    /*Permissions checkers*/
    private static CommandHandler permissionChecker;

    static {
        initPermissionCheckers();
        initValidators();
        try {
            initBranches();     //FIXME
        } catch (ManagerInitializationException e) {
            e.printStackTrace();
        }
    }


    public static CommandHandler getChain(CommandEnum command) throws ManagerInitializationException {
        CommandHandler chain;
        switch (command){
            case LOGIN:
            case REGISTER: {
                CommandHandler isCourier = (request, command1) -> {
                    HttpSession session = request.getSession();
                    RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
                    return role == RoleEnum.COURIER;
                };
                CommandHandler branch = clientBranch.branch(isCourier, courierBranch);
                chain = permissionChecker.chain(sessionBranch).chain(branch);
                break;
            }
            case SET_LOCALE:{
                chain = HttpSessionManager.getInstance();
                break;
            }
            case GET_COURIERS: {
                chain = CourierManager.getInstance();
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

    private static <T> CommandHandler getBeanCreator(T bean, String attribute){
        return (request, command) -> {
            try {
                BeanUtils.populate(bean, request.getParameterMap());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            request.setAttribute(attribute, bean);
            return true;
        };
    }

    private static void initValidators(){
        userBeanValidator = (request, command) -> {
            String attribute = PageAttributesNameProvider.USER_ATTRIBUTE;
            UserBean bean = (UserBean) request.getAttribute(attribute);
            return UserValidator.validate(bean);
        };

        clientBeanValidator = (request, command) -> {
            String attribute = PageAttributesNameProvider.CLIENT_ATTRIBUTE;
            ClientBean bean = (ClientBean) request.getAttribute(attribute);
            return ClientValidator.validate(bean);
        };

        courierBeanValidator = (request, command) -> {
            String attribute = PageAttributesNameProvider.COURIER_ATTRIBUTE;
            CourierBean bean = (CourierBean) request.getAttribute(attribute);
            return CourierValidator.validate(bean);
        };
    }

    private static void initPermissionCheckers(){
        permissionChecker = (request, command) -> {
            String attribute = SessionAttributesNameProvider.ROLE;
            HttpSession session = request.getSession();
            RoleEnum role = (RoleEnum) session.getAttribute(attribute);
            return PermissionChecker.getInstance().checkPermission(role, command);
        };
    }


    private static void createEmptyChain() {
        emptyChain = (request, command) -> false;
    }

    private static void initSessionBranch() throws ManagerInitializationException {
        String attribute = PageAttributesNameProvider.USER_ATTRIBUTE;
        CommandHandler beanCreator = (request, command) -> {
            UserBean user = new UserBean();
            return getBeanCreator(user, attribute).handle(request, command);
        };
        HttpSessionManager manager = HttpSessionManager.getInstance();
        sessionBranch = beanCreator.chain(userBeanValidator).chain(manager);
    }

    private static void initClientBranch() throws ManagerInitializationException {
        String attribute = PageAttributesNameProvider.CLIENT_ATTRIBUTE;
        CommandHandler beanCreator = (request, command) -> {
            ClientBean user = new ClientBean();
            return getBeanCreator(user, attribute).handle(request, command);
        };
        ClientManager manager = ClientManager.getInstance();
        clientBranch = beanCreator.chain(clientBeanValidator).chain(manager);
    }

    private static void initCourierBranch() throws ManagerInitializationException {
        String attribute = PageAttributesNameProvider.COURIER_ATTRIBUTE;
        CommandHandler beanCreator = (request, command) -> {
            CourierBean user = new CourierBean();
            return getBeanCreator(user, attribute).handle(request, command);
        };
        CourierManager manager = CourierManager.getInstance();
        courierBranch = beanCreator.chain(courierBeanValidator).chain(manager);
    }


    private static void initBranches() throws ManagerInitializationException {
        initSessionBranch();
        initClientBranch();
        initCourierBranch();
    }
}
