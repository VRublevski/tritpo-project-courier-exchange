package by.bsuir.exchange.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/couriers.jsp", "/jsp/deliveries.jsp"})
public class PageFilter implements Filter{
    private static final String COURIERS_JSP = "/jsp/couriers.jsp";
    private static final String DELIVERIES_JSP = "/jsp/deliveries.jsp";
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
