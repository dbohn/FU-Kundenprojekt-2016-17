package de.fuberlin.kundenprojekt.friedrich.search;

/**
 * Representation of a single search result.
 *
 * @author Team Friedrich
 */
public class SearchEntry {
    public long id;
    private String guid;
    private String message;
    private String type;
    private String url;
    private String attributes;
    private String avatarUrl;


    public SearchEntry(String guid, String message, String type, String url, String attributes) {
        this.guid = guid;
        this.message = message;
        this.type = type;
        this.url = url;
        this.attributes = extractAttribute(attributes);
        this.avatarUrl = extractAvatarUrl();
    }

    public String extractAvatarUrl() {
        if (this.getType().equals("User")) {
            return "http://humhub.local:8082/bcs/user/avatar?user_id=" + this.guid;
        } else {
            return "";
        }
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getGuid() {
        return guid;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getAttributes() {
        return attributes;
    }

    /**
     * For a post, this method will extract and format the date.
     *
     * @param attributes The attribute field of the HumHub response
     * @return the date formatted string.
     */
    public String extractAttribute(String attributes) {
        if (this.type.equals("Post")) {
            String[] completeAttributes = attributes.split(" ");
            String[] date = completeAttributes[0].split("-");
            String[] time = completeAttributes[1].split(":");
            String user = completeAttributes[2];
            return user + " am " + date[2] + "." + date[1] + "." + date[0] + " um " + time[0] + ":" + time[1];
        }
        return attributes;
    }
}
