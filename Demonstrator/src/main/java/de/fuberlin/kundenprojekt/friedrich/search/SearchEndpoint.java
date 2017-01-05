package de.fuberlin.kundenprojekt.friedrich.search;

import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<String> types = getSelectedTypes(req);

        HumHubSearch hs = new HumHubSearch(Configuration.getHost(),Configuration.getBcsToken());
        List<SearchEntry> searchRes = hs.fetchSearchResults((User) req.getSession().getAttribute("user"),searchTerm);
        req.setAttribute("searchRes", searchRes);
        req.setAttribute("term", searchTerm);
        req.setAttribute("types", types);
        req.getRequestDispatcher("./search.jsp").forward(req, resp);
    }

    private List<String> getSelectedTypes(HttpServletRequest req) {
        List<String> types = new ArrayList<>();

        String[] typesArray = req.getParameterValues("typeList[]");

        if (typesArray != null) {
            types = Arrays.asList(typesArray);
        }
        return types;
    }

}