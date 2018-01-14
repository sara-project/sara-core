package org.sara.interfaces.model;


public class Schedule {
    
    public Schedule (int id, int day, int timeInterval) {
        this.id = id;
        this.day = day;
        this.timeInterval = timeInterval;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Schedule))
            return false;
        
        Schedule other = (Schedule) o;
        
        return other.day == this.day && other.timeInterval == this.timeInterval;
    }
    
    
    private final int id;
    private final int day;
    private final int timeInterval;
}
