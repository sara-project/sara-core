package org.sara.interfaces.model;


public class Requirement implements Cloneable{
    
    public Requirement (String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }
    
    private final String id;
    private final int capacity;
}
