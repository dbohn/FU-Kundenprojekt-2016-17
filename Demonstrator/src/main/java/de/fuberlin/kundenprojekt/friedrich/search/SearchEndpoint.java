package de.fuberlin.kundenprojekt.friedrich.search;

import de.fuberlin.kundenprojekt.friedrich.endpoints.BaseServlet;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Team Friedrich
 */
@WebServlet("/search")
public class SearchEndpoint extends BaseServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchTerm = req.getParameter("searchTerm");
        List<String> limitedSpaces= getParameterList("selectSpace",req);
        List<String> types = getParameterList("typeList", req);

        User user = user(req);

        HumHubSearch hs = new HumHubSearch(Configuration.getHost(),Configuration.getBcsToken());
        hs.fetchSearchResults(user, searchTerm,"All");


        List<SearchEntry> searchRes = hs.getSearchResults();
        List<SpaceEntry> spaceRes = hs.getSpaceResults();

        req.setAttribute("searchRes", searchRes);
        req.setAttribute("term", searchTerm);
        req.setAttribute("types", types);
        req.setAttribute("spaceRes", spaceRes);

        req.getRequestDispatcher("WEB-INF/search.jsp").forward(req, resp);
    }

}