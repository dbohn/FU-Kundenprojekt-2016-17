package de.fuberlin.kundenprojekt.friedrich.search;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hanna on 09.12.2016.
 */

@WebServlet("/search")
public class SearchEndpoint extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("./search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String searchTerm = req.getParameter("searchTerm");
        req.setAttribute("status", "Suche gestartet");
        //Weitergeschickt an search.jsp? Wie dort auslesen?
        req.getRequestDispatcher("./search.jsp").forward(req, resp);
    }

}