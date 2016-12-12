package de.fuberlin.kundenprojekt.friedrich.search;

/**
 * Created by hanna on 09.12.2016.
 */
public class SearchEntry {
    //Herausfinden, wie SearchEntry zusammengesetzt ist
    public long id;
    private String message;

    public SearchEntry(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
