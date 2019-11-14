package by.bsuir.exchange.command.factory;

import by.bsuir.exchange.chain.ChainFactory;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.Command;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.command.exception.CommandInitializationException;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.provider.ConfigurationProvider;

import javax.servlet.http.HttpServletRequest;

import static by.bsuir.exchange.provider.ConfigurationProvider.HOME_PAGE_PATH;
import static by.bsuir.exchange.provider.ConfigurationProvider.LOGIN_PAGE_PATH;

public class CommandFactory {
    private static final String COMMAND = "command";
    private static final int N_COMMANDS = 2;

    private static String[] successPages;
    private static String[] failurePages;

    static {
        initSuccessPages();
    }

    private static void initSuccessPages(){
        successPages = new String[N_COMMANDS];
        failurePages = new String[N_COMMANDS];

        successPages[CommandEnum.LOGIN.ordinal()] = ConfigurationProvider.getProperty(HOME_PAGE_PATH);
        failurePages[CommandEnum.LOGIN.ordinal()] = ConfigurationProvider.getProperty(LOGIN_PAGE_PATH);
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
        try {
            handler = ChainFactory.getChain(commandEnum);
        } catch (ManagerInitializationException e) {
            throw new CommandInitializationException(e);
        }
        String successPage = successPages[commandEnum.ordinal()];
        String failurePage = failurePages[commandEnum.ordinal()];
        return new Command(handler, commandEnum, successPage, failurePage);
    }
}
