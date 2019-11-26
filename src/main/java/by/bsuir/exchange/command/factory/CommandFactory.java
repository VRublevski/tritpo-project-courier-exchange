package by.bsuir.exchange.command.factory;

import by.bsuir.exchange.chain.ChainFactory;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.Command;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.command.exception.CommandInitializationException;
import by.bsuir.exchange.provider.ConfigurationProvider;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.RequestAttributesNameProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static by.bsuir.exchange.provider.ConfigurationProvider.*;
import static by.bsuir.exchange.provider.PageAttributesNameProvider.COMMAND;

public class CommandFactory {
    private static final int N_COMMANDS = 9;

    private static Map<String, String> pageConstants;

    private static String[] successPages;
    private static String[] failurePages;

    static {
        pageConstants = new HashMap<>();
        pageConstants.put("login", LOGIN_PAGE_PATH);
        initSuccessPages();
    }

    private static void initSuccessPages(){
        successPages = new String[N_COMMANDS];
        failurePages = new String[N_COMMANDS];

        successPages[CommandEnum.LOGIN.ordinal()] = ConfigurationProvider.getProperty(CABINET_PAGE_PATH);
        failurePages[CommandEnum.LOGIN.ordinal()] = ConfigurationProvider.getProperty(LOGIN_PAGE_PATH);

        successPages[CommandEnum.REGISTER.ordinal()] = ConfigurationProvider.getProperty(EDIT_PROFILE_PAGE_PATH);
        failurePages[CommandEnum.REGISTER.ordinal()] = ConfigurationProvider.getProperty(REGISTER_PAGE_PATH);

        successPages[CommandEnum.UPDATE_PROFILE_COURIER.ordinal()] = ConfigurationProvider.getProperty(CABINET_PAGE_PATH);
        failurePages[CommandEnum.UPDATE_PROFILE_COURIER.ordinal()] = ConfigurationProvider.getProperty(ERROR_PAGE_PATH);

        successPages[CommandEnum.UPDATE_PROFILE_CLIENT.ordinal()] = ConfigurationProvider.getProperty(CABINET_PAGE_PATH);
        failurePages[CommandEnum.UPDATE_PROFILE_CLIENT.ordinal()] = ConfigurationProvider.getProperty(ERROR_PAGE_PATH);

        successPages[CommandEnum.GET_COURIERS.ordinal()] = ConfigurationProvider.getProperty(COURIER_PAGE_PATH);
        failurePages[CommandEnum.GET_COURIERS.ordinal()] = ConfigurationProvider.getProperty(CABINET_PAGE_PATH);

        successPages[CommandEnum.REQUEST_DELIVERY.ordinal()] = ConfigurationProvider.getProperty(COURIER_PAGE_PATH);
        failurePages[CommandEnum.REQUEST_DELIVERY.ordinal()] = ConfigurationProvider.getProperty(CABINET_PAGE_PATH);

        successPages[CommandEnum.FINISH_DELIVERY.ordinal()] = ConfigurationProvider.getProperty(DELIVERIES_PAGE_PATH);
        failurePages[CommandEnum.FINISH_DELIVERY.ordinal()] = ConfigurationProvider.getProperty(CABINET_PAGE_PATH);

        successPages[CommandEnum.GET_DELIVERIES.ordinal()] = ConfigurationProvider.getProperty(DELIVERIES_PAGE_PATH);
        failurePages[CommandEnum.GET_DELIVERIES.ordinal()] = ConfigurationProvider.getProperty(CABINET_PAGE_PATH);

        successPages[CommandEnum.GET_IMAGE.ordinal()] = ConfigurationProvider.getProperty(GET_IMAGE_PATH);
        failurePages[CommandEnum.GET_IMAGE.ordinal()] = ConfigurationProvider.getProperty(ERROR_PAGE_PATH);
    }

    public static Command getCommand(HttpServletRequest request) throws CommandInitializationException {
        String action = request.getParameter(COMMAND);
        CommandEnum commandEnum;
        if (action == null || action.isEmpty()){
            commandEnum = CommandEnum.EMPTY;
        }else{
            commandEnum = CommandEnum.valueOf(action.toUpperCase());
        }
        CommandHandler handler;
        handler = ChainFactory.getChain(commandEnum);
        String successPage;
        String failurePage;
        if (isSamePage(commandEnum)){
            String pageParameter = request.getParameter(PageAttributesNameProvider.PAGE);
            String pagePropertyName = pageConstants.get(pageParameter);
            String pageProperty = ConfigurationProvider.getProperty(pagePropertyName);
            successPage = pageProperty;
            failurePage = pageProperty;
        }else if(isContentRelated(commandEnum)){
            successPage = ConfigurationProvider.getProperty(IMAGE_SERVLET);
            request.setAttribute(RequestAttributesNameProvider.PAGE, successPages[commandEnum.ordinal()]);
            failurePage = failurePages[commandEnum.ordinal()];
        }else{
            successPage = successPages[commandEnum.ordinal()];
            failurePage = failurePages[commandEnum.ordinal()];
        }
        return new Command(handler, commandEnum, successPage, failurePage);
    }

    private static boolean isSamePage(CommandEnum command){
        return command == CommandEnum.SET_LOCALE;
    }

    private static boolean isContentRelated(CommandEnum command){
        return command == CommandEnum.GET_IMAGE || command == CommandEnum.UPDATE_PROFILE_CLIENT || command == CommandEnum.UPDATE_PROFILE_COURIER;
    }
}
