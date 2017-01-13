package de.fuberlin.kundenprojekt.friedrich.endpoints;

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
 * @author Team Friedrich
 */
public class BaseServlet extends HttpServlet {

    protected User user(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    protected void replyAsJson(HttpServletResponse response, String message) throws IOException {
        replyAsJson(response, message, 200);
    }

    protected void replyAsJson(HttpServletResponse response, String message, int status) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);

        PrintWriter out = response.getWriter();
        out.println(message);
        out.close();
    }

    protected void replyAsJsonError(HttpServletResponse resp, String msg) throws IOException {
        String message = "{\"error\":\"" + msg + "\"}";
        int status = 500;

        replyAsJson(resp, message, status);
    }

    protected List<String> getParameterList(String parameter, HttpServletRequest req) {
        List<String> types = new ArrayList<>();

        String[] typesArray = req.getParameterValues(parameter + "[]");

        if (typesArray != null) {
            types = Arrays.asList(typesArray);
        }

        return types;
    }
}
