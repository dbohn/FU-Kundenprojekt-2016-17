package de.fuberlin.kundenprojekt.friedrich.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Endpoint to handle logout requests.
 *
 * @author Team Friedrich
 */
@WebServlet("/logout")
public class LogoutEndpoint extends HttpServlet {

    /**
     * Handle an incoming logout request.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        resp.sendRedirect(req.getContextPath());
    }
}
