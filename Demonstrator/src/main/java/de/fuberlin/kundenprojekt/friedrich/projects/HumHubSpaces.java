package de.fuberlin.kundenprojekt.friedrich.projects;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubApiUtil;

/**
 * @author Team Friedrich
 */
public class HumHubSpaces {

    private String host;
    private String bcsToken;

    public HumHubSpaces(String host, String bcsToken) {
        this.host = host;
        this.bcsToken = bcsToken;
    }

    public boolean create(String name, String description, User user) throws UnirestException {
        HttpResponse<String> response = HumHubApiUtil.post(this.host, "/bcs/spaces/create", this.bcsToken)
                .field("name", name)
                .field("description", description)
                .field("user_id", user.getId())
                .asString();

        return response.getStatus() == 200;
    }
}
