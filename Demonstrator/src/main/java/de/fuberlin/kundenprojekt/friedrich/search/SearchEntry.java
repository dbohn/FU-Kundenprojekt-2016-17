package de.fuberlin.kundenprojekt.friedrich.search;

/**
 * Created by hanna on 09.12.2016.
 */
public class SearchEntry {
    //Herausfinden, wie SearchEntry zusammengesetzt ist
    public long id;
    private String message;
    private String type;

    public SearchEntry(String message, String type){
        this.message = message;
        this.type = type;
    }

    public String getMessage(){
        return message;
    }

    public String getType(){
        return type;
    }
}
