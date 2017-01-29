package de.fuberlin.kundenprojekt.friedrich;

import de.fuberlin.kundenprojekt.friedrich.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Base Servlet that all servlets that somehow require
 * the authenticated user or send JSON responses should extend.
 *
 * @author Team Friedrich
 */
public class BaseServlet extends HttpServlet {

    /**
     * Get the authenticated user.
     *
     * @param request Incoming authenticated request
     * @return The authenticated user
     */
    protected User user(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    /**
     * Send a successful response with a json content type header.
     *
     * @param response The servlet response to write to
     * @param message  The message, already encoded as JSON
     * @throws IOException If unable to write to response
     */
    protected void replyAsJson(HttpServletResponse response, String message) throws IOException {
        replyAsJson(response, message, 200);
    }

    /**
     * Send a response with the given status and a json content type header.
     *
     * @param response The servlet response to write to
     * @param message  The message, already encoded as JSON
     * @param status   The HTTP status code
     * @throws IOException If unable to write to response
     */
    protected void replyAsJson(HttpServletResponse response, String message, int status) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);

        PrintWriter out = response.getWriter();
        out.println(message);
        out.close();
    }

    /**
     * Send a error response with a json content type header.
     *
     * @param resp The servlet response to write to
     * @param msg  The error message, no json here
     * @throws IOException If unable to write to response
     */
    protected void replyAsJsonError(HttpServletResponse resp, String msg) throws IOException {
        String message = "{\"error\":\"" + msg + "\"}";
        int status = 500;

        replyAsJson(resp, message, status);
    }

    /**
     * Parse an array request parameter into a list.
     *
     * @param parameter The request parameter without the [ ]
     * @param req       The request
     * @return List of values, empty if none
     */
    protected List<String> getParameterList(String parameter, HttpServletRequest req) {
        List<String> types = new ArrayList<>();

        String[] typesArray = req.getParameterValues(parameter + "[]");

        if (typesArray != null) {
            types = Arrays.asList(typesArray);
        }

        return types;
    }
}
