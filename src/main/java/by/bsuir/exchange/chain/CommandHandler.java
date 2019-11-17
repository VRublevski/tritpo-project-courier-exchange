package by.bsuir.exchange.chain;

import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface CommandHandler {

    boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerInitializationException,
                                                                            ManagerOperationException;

    default CommandHandler chain(CommandHandler other){
        return (request, command) -> CommandHandler.this.handle(request, command) && other.handle(request, command);
    }

    default CommandHandler branch(CommandHandler condition, CommandHandler success){
        return (request, command) -> {
            boolean status = condition.handle(request, command);
            return status? success.handle(request, command):CommandHandler.this.handle(request, command) ;
        };
    }
}
