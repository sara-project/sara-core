package org.sara.interfaces.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class SchoolClass {
    
    public SchoolClass(String id, int size, List<Schedule> schedules) {
        this.id = id;
        this.size = size;
        this.schedules =  new SchedulesMap(schedules);
    }
    
    public boolean hasSameSchedule(Schedule schedule) {
        return this.schedules.info.containsKey(schedule);
    }
    
    public boolean thisFits(int size) {
        return size >= this.size;
    }
    
    public List<Schedule> getUnallocatedSchedules() {
        return getSchedules(false);
    }
    
    public List<Schedule> getAllocatedSchedules() {
        return getSchedules(true);
    }
    
    private  List<Schedule> getSchedules(boolean allocated) {
        List<Schedule> schedulesSet = new ArrayList<>();
        
        for(Schedule schedule : schedules.info.keySet()) {
            if(schedules.info.get(schedule) == allocated)
                schedulesSet.add(schedule);
        }
        
        return schedulesSet;
    }
    
    private final String id;
    private final int size;
    private SchedulesMap schedules;
    
    private class SchedulesMap {
    
        public SchedulesMap (List<Schedule> schedules) {
            info = new HashMap<>();
            for(Schedule schedule : schedules)
                info.put(schedule, false);
        }
        
        public boolean add(List<Schedule> schedules) {
            for(Schedule schedule : schedules)
                if(!info.put(schedule, false))
                    return false;
            
            return true;
        }
        
         public boolean add(Schedule schedule) {
            return info.put(schedule, false);
        }
        
        HashMap<Schedule, Boolean> info;
    }
}
