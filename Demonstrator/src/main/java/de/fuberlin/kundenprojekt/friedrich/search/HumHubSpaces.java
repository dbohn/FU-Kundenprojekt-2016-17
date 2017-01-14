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
 * Created by hanna on 14.01.2017.
 */
public class HumHubSpaces {
    private String host;
    private String bcsToken;

    public HumHubSpaces(String host, String bcsToken) {
        this.host = host;
        this.bcsToken = bcsToken;
    }

    public List<SpaceEntry> fetchSpaces(User user) {
        List<SpaceEntry> spacesList = new ArrayList<>();
        try {
            HttpRequest request = HumHubApiUtil.get(host, "/bcs/search/space", bcsToken)
                    .queryString("bcs_id", user.getId());

            HttpResponse<JsonNode> response = request.asJson();

            JSONArray searchRes = response.getBody().getObject().getJSONArray("message");

            for (int i = 0; i < searchRes.length(); i++) {
                JSONObject message = searchRes.getJSONObject(i);
                SpaceEntry entry = extractSpaceEntry(message);
                spacesList.add(entry);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return spacesList;
    }

    private SpaceEntry extractSpaceEntry (JSONObject message) {
        return new SpaceEntry(Integer.parseInt(message.getString("id")), message.getString("name"), message.getString("description"));
    }
}
