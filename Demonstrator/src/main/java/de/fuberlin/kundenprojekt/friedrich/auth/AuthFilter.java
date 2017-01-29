package de.fuberlin.kundenprojekt.friedrich.auth;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter is responsible to check, if the user is authenticated.
 *
 * @author Team Friedrich
 */
@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        String loginURL = request.getContextPath() + "/login";

        // Exclude static assets from protection
        if (request.getServletPath().matches(".*(css|jpg|png|gif|js)$")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if ((session != null && session.getAttribute("user") != null) || request.getRequestURI().equals(loginURL)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(loginURL);
        }
    }

    @Override
    public void destroy() {

    }
}
