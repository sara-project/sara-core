package org.sara.interfaces.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SchoolClass implements Cloneable {

    public SchoolClass(int id, int size, List<Schedule> schedules,  List<Requirement> requirements, List<Integer> typeRoomsWanted) {
        this(id, size);
        this.schedules.add(schedules);
        this.requirements.addAll(requirements);
        this.typeRoomsWanted.addAll(typeRoomsWanted);
    }
    
    public SchoolClass(int id, int size, List<Schedule> schedules) {
        this(id, size);
        this.schedules.add(schedules);
    }

    public SchoolClass(int id, int size) {
        this.id = id;
        this.size = size;
        this.schedules = new SchedulesMap(this);
        this.requirements = new ArrayList<>();
        this.typeRoomsWanted = new ArrayList<>();
    }

    public int getID() {
        return this.id;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public void addTypeRoomsWanted(List<Integer> typeRoomsWanted) {
        this.typeRoomsWanted.addAll(typeRoomsWanted);
    }

    public List<Integer> getTypeRoomsWanted(boolean clone) {
         if(!clone)
            return this.typeRoomsWanted;

        List<Integer> tmp = new ArrayList<>();
        this.typeRoomsWanted.forEach(r -> tmp.add(r + 0));
        return this.typeRoomsWanted;
    }

    public void addRequirements(List<Requirement> requirements) {
        this.requirements.addAll(requirements);
    }
    
    public List<Requirement> getRequirements(boolean clone) {
        if(!clone)
            return this.requirements;

        List<Requirement> tmp = new ArrayList<>();
        this.requirements.forEach(r -> tmp.add((Requirement) r.clone()));
        return tmp;
    }

    public boolean hasAccessibilityRequirement() {
        return this.requirements.stream().anyMatch((r) -> (r.getType() == 2));
    }
    
    public boolean hasTypeRoomRequirement() {
        return this.requirements.stream().anyMatch((r) -> (r.getID() == 4));
    }

    public int howBig(int size) {
        return size - this.size;
    }

    public ClassSchedule getSchoolClassSchedule(Schedule schedule, boolean clone) {
        if(this.schedules == null || this.schedules.classTimeTables == null)
            return null;

        for (ClassSchedule cs : this.schedules.classTimeTables.values()) {
            if (cs.getSchedule().equals(schedule)) {
                return clone? (ClassSchedule) cs.clone() : cs;
            }
        }

        return null;
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add((Schedule) schedule.clone());
    }

    public boolean isAllocted(Schedule schedule) {
        ClassSchedule cs = this.schedules.classTimeTables.get(schedule.getID());

        return cs != null && cs.isAllocated();
    }
    public boolean hasSameSchedule(Schedule schedule) {
        return this.schedules.classTimeTables.containsKey(schedule.getID());
    }

    public boolean thisFits(int size) {
        return this.size <= size;
    }

    public List<ClassSchedule> getUnallocatedSchedules() {
        return this.schedules.getAllocated(false);
    }

    public List<ClassSchedule> getAllocatedSchedules() {
        return this.schedules.getAllocated(true);
    }

    public List<Schedule> getSchedulesByDay(int day, boolean clone) {
        List<Schedule> schedulesByDay = this.getAllSchedules(clone);

        schedulesByDay.stream().filter((schedule) -> (schedule.getDay() != day)).forEachOrdered((schedule) -> {
            schedulesByDay.remove(schedule);
        });

        return schedulesByDay;
    }

    public List<ClassSchedule> getAllSchoolClassSchedules() {
        return this.schedules.getAll();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SchoolClass)) {
            return false;
        }

        SchoolClass other = (SchoolClass) o;

        return other.id == this.id;
    }

    public void allocate(Schedule schedule) {
        this.schedules.allocate(schedule);
    }

    @Override
    public Object clone() {
        return new SchoolClass(id, size, schedules.getAllSchedules(true), this.getRequirements(true), this.getTypeRoomsWanted(true));
    }

    private List<Schedule> getAllSchedules(boolean clone) {
        return this.schedules.getAllSchedules(clone);
    }

    private final int id;
    private final int size;
    private final SchedulesMap schedules;
    private final List<Requirement> requirements;
    private final List<Integer> typeRoomsWanted;

    private class SchedulesMap {

        public SchedulesMap(SchoolClass sClass) {
            this.relationatedClass = sClass;
            this.classTimeTables = new HashMap<>();
        }

        public void add(List<Schedule> schedules) {
            this.fillTimeTables(schedules);
        }

        public void add(Schedule schedule) {
            ArrayList<Schedule> tmp = new ArrayList<>();
            tmp.add(schedule);
            this.fillTimeTables(tmp);
        }

        private void allocate(Schedule schedule) {
            this.classTimeTables.get(schedule.getID()).allocate();
        }

        private List<ClassSchedule> getAllocated(boolean allocated) {
            List<ClassSchedule> tmp = new ArrayList<>();
            this.classTimeTables.values().stream().filter((t) -> (t.isAllocated() == allocated)).forEachOrdered((t) -> {
                tmp.add(t);
            });

            return tmp;
        }

        private List<ClassSchedule> getAll() {
            return new ArrayList<>(this.classTimeTables.values());
        }

        private List<Schedule> getAllSchedules(boolean clone) {
            List<Schedule> tmp = new ArrayList<>();
            this.classTimeTables.values().forEach((t) -> tmp.add(clone? (Schedule) t.getSchedule().clone() : t.getSchedule()));

            return tmp;
        }

        private void fillTimeTables(List<Schedule> schedules) {
            schedules.forEach((s) -> this.classTimeTables.put(s.getID(), new ClassSchedule(relationatedClass, s)));
        }

        private final SchoolClass relationatedClass;
        private HashMap<Integer, ClassSchedule> classTimeTables;
    }
}
