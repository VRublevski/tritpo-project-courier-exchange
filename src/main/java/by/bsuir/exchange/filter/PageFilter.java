package by.bsuir.exchange.filter;

import by.bsuir.exchange.provider.ConfigurationProvider;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.module.Configuration;

@WebFilter(urlPatterns = {"/jsp/couriers.jsp", "/jsp/deliveries.jsp", "/jsp/offers.jsp", "/jsp/profile.jsp",
        "/jsp/editProfile.jsp", "/jsp/register.jsp", "/jsp/login.jsp"})
public class PageFilter implements Filter{
    private static final String COURIERS_JSP = "/jsp/couriers.jsp";
    private static final String DELIVERIES_JSP = "/jsp/deliveries.jsp";
    private static final String OFFERS_JSP = "/jsp/offers.jsp";
    private static final String PROFILE_JSP = "/jsp/profile.jsp";
    private static final String EDIT_PROFILE_JSP = "/jsp/editProfile.jsp";
    private static final String REGISTER_JSP = "/jsp/register.jsp";
    private static final String LOGIN_JSP = "/jsp/login.jsp";
    private static final String ERROR_JSP = "/jsp/error.jsp";

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        String oldServlet = req.getServletPath();

        String newServlet;
        switch (oldServlet){
            case COURIERS_JSP : {
                newServlet = "/controller?command=get_couriers";
                break;
            }
            case DELIVERIES_JSP : {
                newServlet = "/controller?command=get_deliveries";
                break;
            }
            case OFFERS_JSP : {
                newServlet = "/controller?command=get_offers";
                break;
            }
            case PROFILE_JSP: {
                newServlet = "/controller?command=get_profile";
                break;
            }
            case EDIT_PROFILE_JSP:{
                newServlet = ConfigurationProvider.getProperty(ConfigurationProvider.EDIT_PROFILE_PAGE_PATH);
                break;
            }
            case REGISTER_JSP: {
                newServlet = ConfigurationProvider.getProperty(ConfigurationProvider.REGISTER_PAGE_PATH);
                break;
            }
            case LOGIN_JSP: {
                newServlet = ConfigurationProvider.getProperty(ConfigurationProvider.LOGIN_PAGE_PATH);
                break;
            }
            default:{
                newServlet = ERROR_JSP;
                break;
            }
        }
        RequestDispatcher dispatcher = servletRequest.getServletContext()
                .getRequestDispatcher(newServlet);
        dispatcher.forward(req, resp);
    }
}
