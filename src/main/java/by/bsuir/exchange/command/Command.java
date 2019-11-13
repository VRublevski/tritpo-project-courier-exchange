package by.bsuir.exchange.command;

import by.bsuir.exchange.command.exception.CommandOperationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandOperationException;
}
