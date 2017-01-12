package de.fuberlin.kundenprojekt.friedrich.search;

/**
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

    public String extractAttribute(String attributes) {
        if (this.type.equals("Post")) {
            String[] completeAttributes = attributes.split(" ");
            String[] date = completeAttributes[0].split("-");
            String[] time = completeAttributes[1].split(":");
            String user = completeAttributes[2];
            return user + " am " + date[2] + "." + date[1] + "." + date[0] + " um " + time[0] + ":" + time[1] ;
        }
        return attributes;
    }
}
