package de.fuberlin.kundenprojekt.friedrich.search;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubApiUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team Friedrich
 */
public class HumHubSearch {
    private String host;
    private String bcsToken;
    private List<SearchEntry> searchResults = new ArrayList<>();
    private List<SpaceEntry> spacesList = new ArrayList<>();

    /**
     * Create a new HumHubSearchObject
     *
     * @param host
     * @param bcsToken
     */
    public HumHubSearch(String host, String bcsToken) {
        this.host = host;
        this.bcsToken = bcsToken;
    }

    /**
     * Retrieve a list of all serach results for a query
     *
     * @param user
     * @param query
     * @param limitedSpaces
     * @return
     */
    public List<SearchEntry> fetchSearchResults(User user, String query, String limitedSpaces) {
        try {
            HttpRequest request = HumHubApiUtil.get(host, "/bcs/search/search", bcsToken)
                    .queryString("bcs_id", user.getId())
                    .queryString("query", query)
                    .queryString("space", limitedSpaces);

            HttpResponse<JsonNode> response = request.asJson();

            JSONArray searchRes = response.getBody().getObject().getJSONArray("message");

            for (int i = 0; i < searchRes.length(); i++) {
                JSONObject message = searchRes.getJSONObject(i);
                SearchEntry searchEntry = extractSearchEntry(message);
                searchResults.add(searchEntry);
            }


        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    /**
     * Retrieve a list of all spaces
     *
     * @return
     */
    public List<SpaceEntry> fetchSpaceResults() {

        HttpRequest spacesRequest = HumHubApiUtil.get(host, "/bcs/spaces", bcsToken);
        HttpResponse<JsonNode> spacesResponse = null;

        try {
            spacesResponse = spacesRequest.asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONArray spacesRes = spacesResponse.getBody().getObject().getJSONArray("message");

        for (int i = 0; i < spacesRes.length(); i++) {
            JSONObject message = spacesRes.getJSONObject(i);
            SpaceEntry spaceEntry = extractSpaceEntry(message);
            spacesList.add(spaceEntry);
        }
        return spacesList;
    }


    /**
     * Create a SearchEntry object from a JSON message
     *
     * @param message
     * @return
     */
    private SearchEntry extractSearchEntry(JSONObject message) {
        return new SearchEntry(message.getString("guid"), message.getString("message"), message.getString("type"), message.getString("url"), message.getString("attributes"));
    }

    /**
     * Create a SpaceEntry object from a JSON message
     *
     * @param message
     * @return
     */
    private SpaceEntry extractSpaceEntry(JSONObject message) {
        return new SpaceEntry(message.getInt("id"), message.getString("guid"), message.getString("name"), message.getString("desc"));
    }
}