package de.fuberlin.kundenprojekt.friedrich.search;

import de.fuberlin.kundenprojekt.friedrich.BaseServlet;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Team Friedrich
 */
@WebServlet("/search")
public class SearchEndpoint extends BaseServlet {

    /**
     * Display the search form
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/search.jsp").forward(req, resp);
    }

    /**
     * Handle an incoming search request
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchTerm = req.getParameter("searchTerm");
        List<String> limitedSpaces= getParameterList("selectSpace",req);
        List<String> types = getParameterList("typeList", req);

        User user = user(req);

        HumHubSearch hs = new HumHubSearch(Configuration.getHost(),Configuration.getBcsToken());

        List<SearchEntry> searchRes = hs.fetchSearchResults(user, searchTerm,limitedSpaces.get(0));
        List<SpaceEntry> spaceRes = hs.fetchSpaceResults();

        req.setAttribute("searchRes", searchRes);
        req.setAttribute("term", searchTerm);
        req.setAttribute("types", types);
        req.setAttribute("spaceRes", spaceRes);
        req.setAttribute("selectSpace", limitedSpaces);

        req.getRequestDispatcher("WEB-INF/search.jsp").forward(req, resp);
    }

}