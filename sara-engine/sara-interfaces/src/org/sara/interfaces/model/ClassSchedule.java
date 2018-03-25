package org.sara.interfaces.model;

public class ClassSchedule implements Cloneable {

    public ClassSchedule(SchoolClass sClass, Schedule schedule) {
        this(++idCount, sClass, schedule);
    }

    private ClassSchedule(int id, SchoolClass sClass, Schedule schedule) {
        this.id = id;

        this.sClass = sClass;
        this.schedule = schedule;
        this.isAllocated = false;
    }

    public void changeSchedule(Schedule sc) {
        this.schedule = (Schedule) sc.clone();
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
        return new ClassSchedule(this.id, (SchoolClass) this.sClass.clone(), (Schedule) this.schedule.clone());
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
    private final int id;
}
