package de.fuberlin.kundenprojekt.friedrich.search;

/**
 * Created by hanna on 14.01.2017.
 */
public class SpaceEntry {
    int id;
    String guid;
    String name;
    String description;

    public SpaceEntry(int id, String guid, String name, String description){
        this.id = id;
        this.guid = guid;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }
    public String getGuid() {
        return guid;
    }
}
