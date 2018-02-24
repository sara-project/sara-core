package org.sara.interfaces.model;

public class Room implements Cloneable {

    public Room(int id, int capacity, int area) {
        this.id = id;
        this.capacity = capacity;
        this.area = area;
    }

    public boolean thisFits(SchoolClass schoolClass) {
        return schoolClass.thisFits(capacity);
    }
    
    public int useOfRoom(SchoolClass schoolClass) {
        return schoolClass.howBig(capacity);
    }

    public int getID() {
        return this.id;
    }
    
    public int getArea() {
        return this.area;
    }

    public int getCapacity() {
        return this.capacity;
    }
    
    @Override
    public Object clone() {
        return new Room(id, capacity, area);
    }

    private final int id;
    private final int area;
    private final int capacity;
}
