package by.bsuir.exchange.command.impl;

import by.bsuir.exchange.bean.CredentialBean;
import by.bsuir.exchange.command.Command;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.command.exception.CommandOperationException;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.logic.PermissionChecker;
import by.bsuir.exchange.manager.HttpSessionManager;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.ConfigurationProvider;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandOperationException {
        String successPage = ConfigurationProvider.getProperty(ConfigurationProvider.HOME_PAGE_PATH);
        String failurePage = ConfigurationProvider.getProperty(ConfigurationProvider.LOGIN_PAGE_PATH);
        HttpSession session = request.getSession();
        String roleAttribute = SessionAttributesNameProvider.getProperty(SessionAttributesNameProvider.ROLE);
        RoleEnum role = (RoleEnum) session.getAttribute(roleAttribute);
        PermissionChecker checker = PermissionChecker.getInstance();
        boolean granted = checker.checkPermission(role, CommandEnum.LOGIN);
        if (!granted){
            return failurePage;
        }

        CredentialBean credential = new CredentialBean();
        try {
            BeanUtils.populate(credential, request.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        String resultPage;
        try{
            HttpSessionManager sessionManager = HttpSessionManager.getInstance();
            if (sessionManager.login(request, credential)){
                resultPage = successPage;
            }else{
                resultPage= failurePage;
            }
        } catch (ManagerInitializationException | ManagerOperationException e) {
            throw new CommandOperationException(e);
        }
        return resultPage;
    }

}
