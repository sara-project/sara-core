package org.sara.interfaces.model;


public class Slot {
    
    public Slot(String id) {
        this.id = id;
    }
    
    public boolean isEmpty() {
    
        return s_class == null;
    }
    
    public void toEmpty() {
        this.s_class = null;
    }
    
    public void fill(SchoolClass s_class) {
        this.s_class = s_class;
    }
    
    private final String id;
    private SchoolClass s_class;
}
