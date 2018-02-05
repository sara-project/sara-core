package org.sara.interfaces.model;

public class ClassSchedule implements Cloneable {

    public ClassSchedule(SchoolClass sClass, Schedule schedule) {
        idCount++;

        this.id = idCount;
        this.sClass = sClass;
        this.schedule = schedule;
        this.isAllocated = false;
    }

    private ClassSchedule(int id, SchoolClass sClass, Schedule schedule) {
        this(sClass, schedule);
        this.id = id;
        idCount--;
    }

    public void changeSchedule(Schedule sc) {
        this.schedule = sc;
    }

    public int getID() {
        return this.id;
    }

    public boolean isAllocated() {
        return this.isAllocated;
    }

    public void allocate() {
        this.isAllocated = true;
    }

    public void deAllocate() {
        this.isAllocated = false;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public SchoolClass getSchoolClass() {
        return this.sClass;
    }

    @Override
    public Object clone() {
        return new ClassSchedule(this.id, this.sClass, this.schedule);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ClassSchedule)) {
            return false;
        }

        ClassSchedule other = (ClassSchedule) o;

        if (other == null || this.schedule == null || other.schedule == null) {
            return false;
        }

        return other.sClass.equals(this.sClass) && other.schedule.equals(this.schedule);
    }

    private boolean isAllocated;
    private final SchoolClass sClass;
    private Schedule schedule;
    private static int idCount = 0;
    private int id;
}
