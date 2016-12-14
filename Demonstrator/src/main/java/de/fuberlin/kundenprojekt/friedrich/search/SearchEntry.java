package de.fuberlin.kundenprojekt.friedrich.search;

/**
 * Created by hanna on 09.12.2016.
 */
public class SearchEntry {
    //Herausfinden, wie SearchEntry zusammengesetzt ist
    public long id;
    private String message;
    private String type;
    private String url;

    public SearchEntry(String message, String type, String url){
        this.message = message;
        this.type = type;
        this.url = url;
    }

    public String getMessage(){
        return message;
    }

    public String getType(){
        return type;
    }

    public String getUrl(){
        return url;
    }
}
