package by.bsuir.exchange.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    private static final String DEFAULT_LOCALE = "en_EN";

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        if (session.getAttribute("lang") == null){
            session.setAttribute("lang", DEFAULT_LOCALE);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}