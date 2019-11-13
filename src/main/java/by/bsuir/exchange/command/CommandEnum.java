package by.bsuir.exchange.command;

import by.bsuir.exchange.command.impl.EmptyCommand;
import by.bsuir.exchange.command.impl.LoginCommand;

public enum CommandEnum {
    LOGIN{
        {
            this.command = new LoginCommand();
        }
    },
    EMPTY{
        {
            this.command = new EmptyCommand();
        }
    };

    Command command;

    public Command getCurrentCommand(){
        return command;
    }
}
