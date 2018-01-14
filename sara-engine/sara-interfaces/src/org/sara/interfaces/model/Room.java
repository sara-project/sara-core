package org.sara.interfaces.model;


public class Room {
    
    public Room (int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }
    
    public boolean thisFits(SchoolClass schoolClass) {
        return schoolClass.thisFits(capacity);
    }

    private final int id;
    private final int capacity;
}
