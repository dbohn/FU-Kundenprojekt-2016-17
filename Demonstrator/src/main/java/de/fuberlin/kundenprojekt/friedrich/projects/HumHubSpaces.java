package de.fuberlin.kundenprojekt.friedrich.projects;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubApiUtil;

/**
 * Create a new space in HumHub.
 *
 * @author Team Friedrich
 */
public class HumHubSpaces {

    private String host;
    private String bcsToken;

    public HumHubSpaces(String host, String bcsToken) {
        this.host = host;
        this.bcsToken = bcsToken;
    }

    /**
     * Creates a new Space in HumHub by the given data.
     *
     * @param id          The guid, that should be assigned to the space
     * @param name        The name that is displayed in the Space-Selector
     * @param description The description that could also be queried
     * @param user        The user, that should be assigned as the originator. Must be known to HumHub
     * @return true if the space has been created, false otherwise
     * @throws UnirestException if the request to HumHub failed
     */
    public boolean create(Long id, String name, String description, User user) throws UnirestException {
        HttpResponse<String> response = HumHubApiUtil.post(this.host, "/bcs/spaces/create", this.bcsToken)
                .field("guid", id.toString())
                .field("name", name)
                .field("description", description)
                .field("user_id", user.getId())
                .asString();

        return response.getStatus() == 200;
    }
}
