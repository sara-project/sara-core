package org.sara.interfaces.model;

public class Slot implements Cloneable {

    public Slot(int id, Schedule schedule, Room room) {
        this.id = id;
        this.schedule = (Schedule) schedule.clone();
        this.room = room;
    }

    public int getID() {
        return this.id;
    }

    public boolean isEmpty() {
        return this.sClass == null;
    }

    public void toEmpty() {
        this.sClass = null;
    }

    public boolean fill(SchoolClass sClass) {
        if (this.isValid(sClass)) {
            sClass.allocate(this.getSchedule());
            this.sClass = (SchoolClass) sClass.clone();
            this.sClass.allocate(this.getSchedule());
            return true;
        }
        return false;
    }

    public boolean hasSameSchedule(SchoolClass sClass) {
        return sClass.hasSameSchedule(this.schedule);
    }

    public boolean hasSameSchedule(Schedule schedule) {
        return this.schedule == schedule;
    }

    public boolean isValid(SchoolClass sClass) {
        return this.hasSameSchedule(sClass) && this.room.thisFits(sClass) && this.isEmpty();
    }

    public boolean isValid() {
        return this.isValid(this.sClass);
    }

    public SchoolClass getSchoolClass() {
        return this.sClass != null? (SchoolClass) this.sClass.clone() : null;
    }
    
    public Room getRoom() {
        return this.room;
    }
    
    public int useOfRoom(SchoolClass schoolClass) {
        return schoolClass.howBig(this.room.getCapacity());
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    @Override
    public Object clone() {
        Slot clone = new Slot(id, (Schedule) schedule.clone(), (Room) room.clone());
        clone.sClass = this.sClass != null? (SchoolClass) this.sClass.clone() : null;
        return clone;
    }

    private final int id;
    private final Room room;
    private final Schedule schedule;
    private SchoolClass sClass;
}
