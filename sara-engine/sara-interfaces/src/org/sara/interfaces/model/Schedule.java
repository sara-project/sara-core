package org.sara.interfaces.model;


public class Schedule implements Cloneable {
    
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
    
    @Override
    public Object clone() {
        return new Schedule(this.id, this.day, this.timeInterval);
    }
    
    public int getDay() {
        return this.day;
    }
    
    public int getTimeInterval() {
        return this.timeInterval;
    }

    public int getID() {
        return this.id;
    }
    
    private final int id;
    private final int day;
    private final int timeInterval;
}
