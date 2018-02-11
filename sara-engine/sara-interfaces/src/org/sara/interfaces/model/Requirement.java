package org.sara.interfaces.model;

public class Requirement implements Cloneable {

    public Requirement(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }
    
    public String getID() {
        return this.id;
    }
    
    @Override
    public Object clone() {
        return new Requirement(this.id, this.capacity);
    }
    
    private final String id;
    private final int capacity;
}
