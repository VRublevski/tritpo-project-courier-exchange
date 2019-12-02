package by.bsuir.exchange.controller;

import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.command.exception.CommandInitializationException;
import by.bsuir.exchange.command.exception.CommandOperationException;
import by.bsuir.exchange.command.factory.CommandFactory;
import by.bsuir.exchange.command.Command;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.provider.ConfigurationProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@MultipartConfig
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
        String page;
        boolean redirect = false;
        try {
            Command command = CommandFactory.getCommand(request);
            page = command.execute(request, response);
            HttpSession session = request.getSession();
            RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
            if (command.getTag() == CommandEnum.LOGIN && role == RoleEnum.ADMIN){ //or filter?
                page = "/controller?command=get_users";
            }
            redirect = command.isRedirect();
        } catch (CommandOperationException | CommandInitializationException e) {
            page = ConfigurationProvider.getProperty(ConfigurationProvider.ERROR_PAGE_PATH);
        }

        if (redirect){
            String jsp = page.replace("/WEB-INF", "");
            String url = String.format("%s%s", request.getContextPath(), jsp);
            response.sendRedirect(url);
        }else{
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
}
