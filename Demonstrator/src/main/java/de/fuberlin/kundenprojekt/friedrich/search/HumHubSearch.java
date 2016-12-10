package de.fuberlin.kundenprojekt.friedrich.search;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubApiUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanna on 09.12.2016.
 */
public class HumHubSearch  {
    private String host;
    private String bcsToken;

    public HumHubSearch( String host, String bcsToken) {
        this.host = host;
        this.bcsToken = bcsToken;
    }

    public List<SearchEntry> fetchSearchResults(User user){
        List<SearchEntry> searchResults = new ArrayList<>();
        try {
            HttpResponse<JsonNode> response = HumHubApiUtil.get(host, "/bcs/search", bcsToken)
                    .queryString("bcs_id", user.getId())
                    .asJson();
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
    private SearchEntry extractSearchEntry (JSONObject message) {
        return null;
    }
}