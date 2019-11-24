package by.bsuir.exchange.chain;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.checker.PermissionChecker;
import by.bsuir.exchange.manager.*;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.validator.ActorValidator;
import by.bsuir.exchange.validator.UserValidator;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

public class ChainFactory { //Load on servlet initialization


    /*Chains*/
    private static CommandHandler emptyChain;

    private static CommandHandler sessionBranch;
    private static CommandHandler actorBranch;

    /*Managers*/
    /*BeanCreators*/

    /*Validators*/
    private static CommandHandler userBeanValidator;
    private static CommandHandler actorBeanValidator;

    /*Permissions checkers*/
    private static CommandHandler permissionChecker;
    private static CommandHandler isCourier;

    static {
        initCheckers();
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
                chain = permissionChecker.chain(sessionBranch).chain(actorBranch);
                break;
            }
            case UPDATE_PROFILE_COURIER:
            case UPDATE_PROFILE_CLIENT: {
                chain = permissionChecker.chain(actorBranch);
                break;
            }
            case SET_LOCALE:{
                chain = HttpSessionManager.getInstance();
                break;
            }
            case GET_COURIERS: {
                CommandHandler manager = ActorManager.getInstance();
                chain = permissionChecker.chain(manager);
                break;
            }
            case GET_DELIVERIES: {  //FIXME check for permissions
                CommandHandler manager = DeliveryManager.getInstance();
                chain = manager;
                break;
            }
            case GET_IMAGE: {
                chain = permissionChecker;
                break;
            }
            case REQUEST_DELIVERY: {
                CommandHandler manager = DeliveryManager.getInstance();
                chain = permissionChecker.chain(manager);
                break;
            }
            case FINISH_DELIVERY: {
                CommandHandler manager = DeliveryManager.getInstance();
                chain = manager;
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

        actorBeanValidator = (request, command) -> {
            String attribute = PageAttributesNameProvider.CLIENT_ATTRIBUTE;
            ActorBean bean = (ActorBean) request.getAttribute(attribute);
            return ActorValidator.validate(bean);
        };
    }

    private static void initCheckers(){
        permissionChecker = (request, command) -> {
            String attribute = SessionAttributesNameProvider.ROLE;
            HttpSession session = request.getSession();
            RoleEnum role = (RoleEnum) session.getAttribute(attribute);
            return PermissionChecker.getInstance().checkPermission(role, command);
        };

        isCourier = (request, command1) -> {
            HttpSession session = request.getSession();
            RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
            return role == RoleEnum.COURIER;
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

    private static void initActorBranch() throws ManagerInitializationException {
        String attribute = PageAttributesNameProvider.CLIENT_ATTRIBUTE;
        CommandHandler beanCreator = (request, command) -> {
            ActorBean user = new ActorBean();
            return getBeanCreator(user, attribute).handle(request, command);
        };
        ActorManager manager = ActorManager.getInstance();
        actorBranch = beanCreator.chain(actorBeanValidator).chain(manager);
    }


    private static void initBranches() throws ManagerInitializationException {
        initSessionBranch();
        initActorBranch();
    }
}
