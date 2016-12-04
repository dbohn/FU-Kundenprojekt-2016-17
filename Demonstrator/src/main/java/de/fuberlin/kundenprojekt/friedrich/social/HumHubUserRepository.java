package de.fuberlin.kundenprojekt.friedrich.social;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;

import java.time.LocalDateTime;

/**
 * @author Team Friedrich
 */
public class HumHubUserRepository {
    private String baseUrl;
    private String bcsSuperToken;

    public HumHubUserRepository(String bcsSuperToken, String baseUrl) {
        this.baseUrl = baseUrl;
        this.bcsSuperToken = bcsSuperToken;
    }

    public JsonNode add(User usersObject) {

        //read properties out of userObject
        String username = usersObject.username;
        String email = usersObject.email;
        String password = usersObject.password;
        String phone = usersObject.phone;
        String id = usersObject.id;
        //first_name and last_name are part of full_name, separated by a comma
        String [] name = usersObject.full_name.split(",");
        String first_name = name[1].trim();
        String last_name = name[0];

        //response to caller
        JsonNode body = null;

        try {
            HttpResponse<JsonNode> response = Unirest.post(baseUrl + "/bcs/users/add")
                    .header("x-bcs-super-token", bcsSuperToken)
                    .header("accept", "application/json")
                    .field("username", username)
                    .field("email", email)
                    .field("first_name", first_name)
                    .field("last_name", last_name)
                    .field("password", password)
                    .field("phone", phone)
                    .field("bcs_id", id)
                    .asJson();
            body = response.getBody();

            if (response.getStatus() == 200) {
                usersObject.getUserinfo().setLastSyncedAt(LocalDateTime.now());
                UserRepository.storeUser(usersObject);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return body;

    }

}
