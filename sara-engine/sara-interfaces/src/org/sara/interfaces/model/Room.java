package org.sara.interfaces.model;

public class Room implements Cloneable {

    public Room(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public boolean thisFits(SchoolClass schoolClass) {
        return schoolClass.thisFits(capacity);
    }

    public int getID() {
        return this.id;
    }

    @Override
    public Object clone() {
        return new Room(id, capacity);
    }

    private final int id;
    private final int capacity;
}
