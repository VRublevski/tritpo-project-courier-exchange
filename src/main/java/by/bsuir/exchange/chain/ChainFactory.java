package by.bsuir.exchange.chain;

import by.bsuir.exchange.bean.CredentialBean;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.logic.PermissionChecker;
import by.bsuir.exchange.manager.HttpSessionManager;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.validator.CredentialValidator;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

public class ChainFactory { //Load on servlet initialization
    /*Chains*/
    private static CommandHandler sessionChain;
    private static CommandHandler emptyChain;

    /*Bean creators*/
    private static CommandHandler credentialBeanCreator;

    /*Validators*/
    private static CommandHandler credentialValidator;

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
            case LOGIN: {
                if (sessionChain == null){
                    createSessionChain();
                }
                chain = sessionChain;
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
        credentialBeanCreator = (request, command) -> {
            CredentialBean bean = new CredentialBean();
            String page = PageAttributesNameProvider.LOGIN_PAGE;
            String attributeProperty = PageAttributesNameProvider.CREDENTIAL_ATTRIBUTE;
            String attribute = PageAttributesNameProvider.getProperty(page, attributeProperty);
            try {
                BeanUtils.populate(bean, request.getParameterMap());
                request.setAttribute(attribute, bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();        //FIXME should be logged
                return false;
            }
            return true;
        };
    }

    private static void initValidators(){
        credentialValidator = (request, command) -> {
            String page = PageAttributesNameProvider.LOGIN_PAGE;
            String attributeProperty = PageAttributesNameProvider.CREDENTIAL_ATTRIBUTE;
            String attribute = PageAttributesNameProvider.getProperty(page, attributeProperty);
            CredentialBean bean = (CredentialBean) request.getAttribute(attribute);
            return CredentialValidator.validate(bean);
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
        sessionChain = credentialBeanCreator.chain(credentialValidator).chain(permissionChecker).chain(manager);
    }
}
