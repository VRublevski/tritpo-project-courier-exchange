package by.bsuir.exchange.controller;

import by.bsuir.exchange.command.Command;
import by.bsuir.exchange.command.exception.CommandOperationException;
import by.bsuir.exchange.command.factory.CommandFactory;
import by.bsuir.exchange.provider.ConfigurationProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet implements Servlet {
    public Controller(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        String page;
        try {
            page = command.execute(request, response);
        } catch (CommandOperationException e) {
            page = ConfigurationProvider.getProperty(ConfigurationProvider.ERROR_PAGE_PATH);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
