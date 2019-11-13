package by.bsuir.exchange.command.factory;

import by.bsuir.exchange.command.Command;
import by.bsuir.exchange.command.CommandEnum;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static final String COMMAND = "command";

    public static Command getCommand(HttpServletRequest request){
        String action = request.getParameter(COMMAND);
        CommandEnum commandEnum;
        if (action == null || action.isEmpty()){
            commandEnum = CommandEnum.EMPTY;
        }else{
            commandEnum = CommandEnum.valueOf(action.toUpperCase());
        }
        return commandEnum.getCurrentCommand();
    }
}
