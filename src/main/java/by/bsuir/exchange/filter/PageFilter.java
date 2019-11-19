package by.bsuir.exchange.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/couriers.jsp"})
public class PageFilter implements Filter{
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        RequestDispatcher dispatcher = servletRequest.getServletContext()
                .getRequestDispatcher("/controller?command=get_couriers");
        dispatcher.forward(req, resp);
    }
}
