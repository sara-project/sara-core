package org.sara.interfaces.model;


public class Slot {
    
    public Slot(int id, Schedule schedule, Room room) {
        this.id = id;
        this.schedule = schedule;
        this.room = room;
    }
    
    public boolean isEmpty() {
    
        return sClass == null;
    }
    
    public void toEmpty() {
        this.sClass = null;
    }
    
    public void fill(SchoolClass sClass) {
        this.sClass = sClass;
    }
    
    public boolean hasSameSchedule(SchoolClass sClass) {
        return sClass.hasSameSchedule(this.schedule) ;
    }
    
    public boolean hasSameSchedule(Schedule schedule) {
        return this.schedule == schedule;
    }
    
    public boolean isValid() {
        boolean c1 = hasSameSchedule(this.sClass);
        boolean c2 = room.thisFits(sClass);

        return c1 && c2;
    }
    
    private final int id;
    private final Room room;
    private final Schedule schedule;
    private SchoolClass sClass;
}
