package by.bsuir.exchange.chain;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.bean.DeliveryBean;
import by.bsuir.exchange.bean.OfferBean;
import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.checker.PermissionChecker;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.manager.*;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.RequestAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.validator.ActorValidator;
import by.bsuir.exchange.validator.OfferValidator;
import by.bsuir.exchange.validator.UserValidator;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

public class ChainFactory { //Load on servlet initialization


    /*Chains*/
    private static CommandHandler emptyChain;

    /*Bean creators*/
    private static CommandHandler userBeanCreator;
    private static CommandHandler actorBeanCreator;
    private static CommandHandler offerBeanCreator;
    private static CommandHandler deliveryBeanCreator;

    /*Branches*/
    private static CommandHandler sessionBranch;
    private static CommandHandler actorBranch;

    /*Managers*/
    private static CommandHandler sessionManager;
    private static CommandHandler clientManager;
    private static CommandHandler courierManager;
    private static CommandHandler deliveryManager;
    private static CommandHandler offerManager;


    /*Transactional*/
    private static CommandHandler registerTransaction;
    private static CommandHandler finishDeliveryTransaction;
    private static CommandHandler deleteUserTransactional;

    /*Validators*/
    private static CommandHandler userBeanValidator;
    private static CommandHandler actorBeanValidator;
    private static CommandHandler offerBeanValidator;

    /*Checkers*/
    private static CommandHandler permissionChecker;
    private static CommandHandler isCourierSession;
    private static CommandHandler isCourierRequest;

    static {
        initCheckers();
        initValidators();
        initBeanCreators();
        try {
            initManagers();
            initBranches();     //FIXME
            initTransactional();
        } catch (ManagerInitializationException e) {
            e.printStackTrace();
        }
    }

    public static CommandHandler getChain(CommandEnum command) {
        CommandHandler chain;
        switch (command){
            case LOGIN:{
                CommandHandler actorBranch = clientManager.branch(isCourierSession, courierManager);
                chain = permissionChecker.chain(sessionBranch).chain(actorBranch);
                break;
            }
            case LOGOUT: {
                chain = permissionChecker.chain(sessionManager);
                break;
            }
            case REGISTER: {
                chain = permissionChecker.chain(registerTransaction);
                break;
            }
            case UPDATE_PROFILE_COURIER:{
                chain = permissionChecker.chain(offerBeanCreator).chain(offerBeanValidator).chain(actorBranch).chain(offerManager);
                break;
            }
            case UPDATE_PROFILE_CLIENT: {
                chain = permissionChecker.chain(actorBranch);
                break;
            }
            case SET_LOCALE:{
                chain = sessionManager;
                break;
            }
            case GET_USERS: {
                chain = permissionChecker.chain(sessionManager).chain(clientManager).chain(courierManager);
                break;
            }
            case DELETE_USER: {
                chain = deleteUserTransactional;
                break;
            }
            case GET_PROFILE: {  //FIXME check for permissions
                CommandHandler branch = clientManager.branch(isCourierSession, courierManager);
                chain = branch;
                break;
            }
            case GET_COURIERS: {
                chain = permissionChecker.chain(courierManager);
                break;
            }
            case GET_OFFERS: {
                chain = permissionChecker.chain(offerManager).chain(courierManager);
                break;
            }
            case GET_DELIVERIES: {  //FIXME check for permissions
                CommandHandler branch = courierManager.branch(isCourierSession, clientManager);
                chain = permissionChecker.chain(deliveryManager).chain(branch);
                break;
            }
            case GET_IMAGE: {
                chain = permissionChecker;
                break;
            }
            case REQUEST_DELIVERY: {
                chain = permissionChecker.chain(deliveryBeanCreator).chain(clientManager).chain(offerManager).chain(deliveryManager);
                break;
            }
            case FINISH_DELIVERY: {
                chain = finishDeliveryTransaction;
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
            String attribute = RequestAttributesNameProvider.ACTOR_ATTRIBUTE;
            ActorBean bean = (ActorBean) request.getAttribute(attribute);
            return ActorValidator.validate(bean);
        };

        offerBeanValidator = (request, command) -> {
            String attribute = RequestAttributesNameProvider.OFFER_ATTRIBUTE;
            OfferBean  bean = (OfferBean) request.getAttribute(attribute);
            return OfferValidator.validate(bean);
        };
    }

    private static void initCheckers(){
        permissionChecker = (request, command) -> {
            String attribute = SessionAttributesNameProvider.ROLE;
            HttpSession session = request.getSession();
            RoleEnum role = (RoleEnum) session.getAttribute(attribute);
            return PermissionChecker.getInstance().checkPermission(role, command);
        };

        isCourierSession = (request, command1) -> {
            HttpSession session = request.getSession();
            RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
            return role == RoleEnum.COURIER;
        };

        isCourierRequest = (request, command) -> {
            UserBean user = (UserBean) request.getAttribute(RequestAttributesNameProvider.USER_ATTRIBUTE);
            String courierRole = RoleEnum.COURIER.toString();
            String actualRole = user.getRole().toUpperCase();
            return courierRole.equals(actualRole);
        };
    }


    private static void createEmptyChain() {
        emptyChain = (request, command) -> false;
    }

    private static void initSessionBranch() {
        sessionBranch = userBeanCreator.chain(userBeanValidator).chain(sessionManager);
    }

    private static void initActorBranch() {
        CommandHandler branch = clientManager.branch(isCourierSession, courierManager);
        actorBranch = actorBeanCreator.chain(actorBeanValidator).chain(branch);
    }



    private static void initManagers() throws ManagerInitializationException {
        sessionManager = new HttpSessionManager();
        clientManager = new ActorManager(RoleEnum.CLIENT);
        courierManager = new ActorManager(RoleEnum.COURIER);
        deliveryManager = new DeliveryManager();
        offerManager = new OfferManager();
    }


    private static void initTransactional() {
        CommandHandler clientRegisterTransactional = (request, command1) -> {
            HttpSessionManager sessionManager = new HttpSessionManager();
            ActorManager actorManager = new ActorManager(RoleEnum.CLIENT);
            AbstractManager<UserBean> combination = sessionManager.combine(actorManager);
            boolean status = combination.handle(request, command1);
            combination.closeManager();
            return status;
        };
        CommandHandler courierRegisterTransactional = (request, command1) -> {
            HttpSessionManager sessionManager = new HttpSessionManager();
            ActorManager actorManager = new ActorManager(RoleEnum.COURIER);
            AbstractManager<UserBean> combination = sessionManager.combine(actorManager);
            boolean status = combination.handle(request, command1);
            combination.closeManager();
            return status;
        };
        CommandHandler registerBranch = clientRegisterTransactional.branch(isCourierRequest, courierRegisterTransactional);
        registerTransaction = userBeanCreator
                            .chain(userBeanValidator)
                            .chain(actorBeanCreator)
                            .chain(actorBeanValidator)
                            .chain(registerBranch);

        CommandHandler deliveryTransaction = (request, command) -> {
            DeliveryManager deliveryManager = new DeliveryManager();
            ActorManager clientManager = new ActorManager(RoleEnum.CLIENT);
            ActorManager courierManager = new ActorManager(RoleEnum.COURIER);
            AbstractManager<DeliveryBean> deliveryClientCombination = deliveryManager.combine(clientManager);
            AbstractManager<DeliveryBean> deliveryActorCombination = deliveryClientCombination.combine(courierManager);
            boolean status = deliveryActorCombination.handle(request, command);
            deliveryActorCombination.closeManager();
            return status;
        };
        finishDeliveryTransaction = deliveryBeanCreator.chain(offerManager).chain(deliveryTransaction);

        CommandHandler clientDeleteTransactional = (request, command) -> {
            HttpSessionManager userManager = new HttpSessionManager();
            ActorManager clientManager = new ActorManager(RoleEnum.CLIENT);
            DeliveryManager deliveryManager = new DeliveryManager();
            ImageManager imageManager = new ImageManager();
            AbstractManager<UserBean> userClientCombination = userManager.combine(clientManager);
            AbstractManager<UserBean> deliveryCombination = userClientCombination.combine(deliveryManager);
            AbstractManager<UserBean> fullCombination = deliveryCombination.combine(imageManager);
            boolean status = fullCombination.handle(request, command);
            fullCombination.closeManager();
            return status;
        };

        CommandHandler courierDeleteTransactional = (request, command) -> {
            HttpSessionManager userManager = new HttpSessionManager();
            ActorManager clientManager = new ActorManager(RoleEnum.COURIER);
            DeliveryManager deliveryManager = new DeliveryManager();
            OfferManager offerManager = new OfferManager();
            ImageManager imageManager = new ImageManager();
            AbstractManager<UserBean> userClientCombination = userManager.combine(clientManager);
            AbstractManager<UserBean> deliveryCombination = userClientCombination.combine(deliveryManager);
            AbstractManager<UserBean> offerCombination = deliveryCombination.combine(offerManager);
            AbstractManager<UserBean> fullCombination = offerCombination.combine(imageManager);
            boolean status = fullCombination.handle(request, command);
            fullCombination.closeManager();
            return status;
        };

        CommandHandler deleteUserBranch = clientDeleteTransactional.branch(isCourierRequest, courierDeleteTransactional);

        deleteUserTransactional = permissionChecker.chain(userBeanCreator).chain(deleteUserBranch);
    }

    private static void initBranches() {
        initSessionBranch();
        initActorBranch();
    }


    private static void initBeanCreators() {
        userBeanCreator = (request, command1) -> {
            UserBean user = new UserBean();
            return getBeanCreator(user, RequestAttributesNameProvider.USER_ATTRIBUTE).handle(request, command1);
        };

        actorBeanCreator = (request, command1) -> {
            ActorBean actor = new ActorBean();
            return getBeanCreator(actor, RequestAttributesNameProvider.ACTOR_ATTRIBUTE).handle(request, command1);
        };

        offerBeanCreator = (request, command) -> {
            OfferBean offer = new OfferBean();
            return getBeanCreator(offer, RequestAttributesNameProvider.OFFER_ATTRIBUTE).handle(request, command);
        };


        deliveryBeanCreator = (request, command) -> {
            DeliveryBean delivery = new DeliveryBean();
            return getBeanCreator(delivery, RequestAttributesNameProvider.DELIVERY_ATTRIBUTE).handle(request, command);
        };
    }
}
