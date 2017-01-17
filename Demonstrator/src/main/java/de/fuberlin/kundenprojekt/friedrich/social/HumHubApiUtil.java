package de.fuberlin.kundenprojekt.friedrich.social;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * Provides handy methods to create a request to HumHub.
 *
 * @author Team Friedrich
 */
public class HumHubApiUtil {

    /**
     * Create A GetRequest to the given endpoint with the provided token and
     * append the necessary headers.
     *
     * @param base     Base URL to HumHub
     * @param endpoint Endpoint called at HumHub
     * @param token    The BCS Super Token
     * @return The request, ready to append the query string
     */
    public static GetRequest get(String base, String endpoint, String token) {
        return Unirest.get(base + endpoint)
                .header("x-bcs-super-token", token)
                .header("accept", "application/json");
    }

    /**
     * Create A Post Request to the given endpoint with the provided token and
     * append the necessary headers.
     *
     * @param base     Base URL to HumHub
     * @param endpoint Endpoint called at HumHub
     * @param token    The BCS Super Token
     * @return The request, ready to append the query string
     */
    public static HttpRequestWithBody post(String base, String endpoint, String token) {
        return Unirest.post(base + endpoint)
                .header("x-bcs-super-token", token)
                .header("accept", "application/json");
    }
}
