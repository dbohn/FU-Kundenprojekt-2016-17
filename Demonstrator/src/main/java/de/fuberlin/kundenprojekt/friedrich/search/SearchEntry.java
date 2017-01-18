package de.fuberlin.kundenprojekt.friedrich.search;

/**
 * Representation of a single search result.
 *
 * @author Team Friedrich
 */
public class SearchEntry {
    public long id;
    private String message;
    private String type;
    private String url;
    private String attributes;

    public SearchEntry(String message, String type, String url, String attributes) {
        this.message = message;
        this.type = type;
        this.url = url;
        this.attributes = extractAttribute(attributes);
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
