package org.sara.interfaces.model;


public class Room {
    
    public Room (String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }
    
    public boolean thisFits(SchoolClass schoolClass) {
        return schoolClass.thisFits(capacity);
    }

    private final String id;
    private final int capacity;
}
