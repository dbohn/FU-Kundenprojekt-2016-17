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
 * Created by hanna on 09.12.2016.
 */
public class HumHubSearch  {
    private String host;
    private String bcsToken;

    public HumHubSearch( String host, String bcsToken) {
        this.host = host;
        this.bcsToken = bcsToken;
    }

    public List<SearchEntry> fetchSearchResults(User user,String query){
        List<SearchEntry> searchResults = new ArrayList<>();
        try {
            HttpRequest request = HumHubApiUtil.get(host, "/bcs/search/search", bcsToken)
                    .queryString("bcs_id", user.getId())
                    .queryString("query",query);

            System.out.println(request.getUrl());

            HttpResponse<JsonNode> response = request.asJson();

            JSONArray searchRes = response.getBody().getObject().getJSONArray("message");
            System.out.println(response.getBody().toString());
            for (int i = 0; i < searchRes.length(); i++) {
                JSONObject message = searchRes.getJSONObject(i);
                SearchEntry searchEntry = extractSearchEntry(message);
                System.out.println(searchEntry.getMessage());
                searchResults.add(searchEntry);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
    private SearchEntry extractSearchEntry (JSONObject message) {
        return new SearchEntry(message.getString("message"), message.getString("type"), message.getString("url"));
    }


}