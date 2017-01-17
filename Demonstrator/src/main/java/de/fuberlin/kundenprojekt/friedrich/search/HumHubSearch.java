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

    public HumHubSearch(String host, String bcsToken) {
        this.host = host;
        this.bcsToken = bcsToken;
    }

    public void fetchSearchResults(User user, String query, String limitedSpaces) {
        try {
            HttpRequest request = HumHubApiUtil.get(host, "/bcs/search/search", bcsToken)
                    .queryString("bcs_id", user.getId())
                    .queryString("query", query)
                    .queryString("space", "All")
                    ;

            HttpResponse<JsonNode> response = request.asJson();

            JSONArray searchRes = response.getBody().getObject().getJSONArray("message");

            for (int i = 0; i < searchRes.length(); i++) {
                JSONObject message = searchRes.getJSONObject(i);
                SearchEntry searchEntry = extractSearchEntry(message);
                searchResults.add(searchEntry);
            }


            HttpRequest spacesRequest = HumHubApiUtil.get(host, "/bcs/spaces", bcsToken);

            HttpResponse<JsonNode> spacesResponse = spacesRequest.asJson();

            JSONArray spacesRes = spacesResponse.getBody().getObject().getJSONArray("message");

            for (int i = 0; i < spacesRes.length(); i++) {
                JSONObject message = spacesRes.getJSONObject(i);
                SpaceEntry spaceEntry = extractSpaceEntry(message);
                spacesList.add(spaceEntry);
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public List<SearchEntry> getSearchResults(){
        return this.searchResults;
    }

    public List<SpaceEntry> getSpaceResults(){
        return this.spacesList;
    }

    private SearchEntry extractSearchEntry(JSONObject message) {
        return new SearchEntry(message.getString("message"), message.getString("type"), message.getString("url"), message.getString("attributes"));
    }

    private SpaceEntry extractSpaceEntry (JSONObject message) {
        return new SpaceEntry(message.getInt("id"), message.getString("name"), message.getString("desc"));
    }
}