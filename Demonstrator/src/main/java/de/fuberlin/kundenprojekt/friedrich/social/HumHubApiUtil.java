package de.fuberlin.kundenprojekt.friedrich.social;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * @author Team Friedrich
 */
public class HumHubApiUtil {

    public static GetRequest get(String base, String endpoint, String token) {
        return Unirest.get(base + endpoint)
                .header("x-bcs-super-token", token)
                .header("accept", "application/json");
    }

    public static HttpRequestWithBody post(String base, String endpoint, String token) {
        return Unirest.post(base + endpoint)
                .header("x-bcs-super-token", token)
                .header("accept", "application/json");
    }
}
