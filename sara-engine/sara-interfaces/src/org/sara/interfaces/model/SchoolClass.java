package org.sara.interfaces.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SchoolClass implements Cloneable{
    
    public SchoolClass(int id, int size, List<Schedule> schedules) {
        this(id, size);
        this.schedules.add(schedules);
    }
    
    public SchoolClass(int id, int size) {
        this.id = id;
        this.size = size;
        this.schedules =  new SchedulesMap(this);
    }
    
    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
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
    
    public List<Schedule> getSchedulesByDay(int day) {
        List<Schedule> schedulesByDay = this.getAllSchedules();
        
        for(Schedule schedule : schedulesByDay) {
            if(schedule.getDay() != day) {
               schedulesByDay.remove(schedule);
            }
        }
        
        return schedulesByDay;
    }
    
    public  List<ClassSchedule> getAllSchoolClassSchedules() {
        return this.schedules.getAll();
    }
    
    public ClassSchedule getSchoolClassSchedule(Schedule schedule) {
        for(ClassSchedule cs : this.schedules.getAll()) {
            if(cs.getSchedule().equals(schedule))
                return cs;
        }
        
        return null;
    }
    
    public boolean equals(Object o) {
        if(!(o instanceof SchoolClass))
            return false;
        
        SchoolClass other = (SchoolClass) o;
        
        return other.id == this.id;
    }
    
    public void allocate(Schedule schedule) {
        this.schedules.allocate(schedule);
    }
    
    private  List<Schedule> getAllSchedules() {
        return this.schedules.getAllSchedules();
    }
      
    private final int id;
    private final int size;
    private final SchedulesMap schedules;
    
    private class SchedulesMap {
    
        public SchedulesMap (SchoolClass sClass) {
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
            for(ClassSchedule t : this.classTimeTables.values())
                if(t.isAllocated() == allocated)
                    tmp.add(t);
            
            return tmp;
        }
        
        private List<ClassSchedule> getAll() {
            List<ClassSchedule> tmp = new ArrayList<>();
            tmp.addAll(this.classTimeTables.values());
            
            return tmp;
        }

        private List<Schedule> getAllSchedules() {
            List<Schedule> tmp = new ArrayList<>();
            for(ClassSchedule t : this.classTimeTables.values())
                tmp.add(t.getSchedule());
            
            return tmp;
        }
        
        private void fillTimeTables(List<Schedule> schedules) {
            for(Schedule s : schedules)
                this.classTimeTables.put(s.getID(), new ClassSchedule(relationatedClass, s));
        }
        
        private SchoolClass relationatedClass;
        private HashMap<Integer, ClassSchedule> classTimeTables;
    }
}
