package org.sara.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.GAConfiguration;
import org.sara.interfaces.model.Requirement;
import org.sara.interfaces.model.Room;
import org.sara.interfaces.model.Schedule;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.Slot;


public abstract class IModelController {
    
    public IModelController() {
        requirements = new HashMap<>();
        rooms = new HashMap<>();
        schedules = new HashMap<>();
        schoolClass = new HashMap<>();
        slots = new HashMap<>();
        
        this.gaConfiguration = new GAConfiguration();
    }

    public Map<String, Requirement> getRequirements() {
        return (Map<String, Requirement>) this.requirements.clone();
    }

    public void setRequirements(HashMap<String, Requirement> requirements) {
        this.requirements = requirements;
    }

    public Map<String, Room> getRooms() {
        return (Map<String, Room>) rooms.clone();
    }

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    public Map<String, Schedule> getSchedules() {
        return (Map<String, Schedule>) this.schedules.clone();
    }

    public void setSchedules(HashMap<String, Schedule> schedules) {
        this.schedules = schedules;
    }

    public HashMap<String, SchoolClass> getSchoolClass() {
        return (HashMap<String, SchoolClass>) this.schoolClass.clone();
    }

    public void setSchoolClass(HashMap<String, SchoolClass> schoolClass) {
        this.schoolClass = schoolClass;
    }
    
    public void setClassSchedule(HashMap<String, ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
    }
    
    public Map<String, ClassSchedule> getClassSchedule() {
        return (HashMap<String, ClassSchedule>) this.classSchedules.clone();
    }

    public Map<String, Slot> getSlots() {
        return (Map<String, Slot>) this.slots.clone();
    }

    public void setSlots(HashMap<String, Slot> slots) {
        this.slots = slots;
    }
    
    public Map<Integer, List<ClassSchedule>> separateClassScheduleByDay(List<ClassSchedule> classSchedules) {
        HashMap<Integer, List<ClassSchedule>> classSchedulesByDay = new HashMap<>();
        
        for (ClassSchedule classSchedule : classSchedules) {
            if(!classSchedulesByDay.containsKey(classSchedule.getSchedule().getDay())) {
                List<ClassSchedule> list = new ArrayList<>();
                list.add(classSchedule);
                classSchedulesByDay.put(classSchedule.getSchedule().getDay(), list);
            }
            else
                classSchedulesByDay.get(classSchedule.getSchedule().getDay()).add(classSchedule);
        }
        return (Map<Integer, List<ClassSchedule>>) classSchedulesByDay.clone();
    }
    
    public Map<Integer, List<ClassSchedule>> ClassScheduleBySchedule(List<ClassSchedule> classSchedules) {
        HashMap<Integer, List<ClassSchedule>> classSchedulesBySchedule = new HashMap<>();
        
        for (ClassSchedule classSchedule : classSchedules) {
            if(!classSchedulesBySchedule.containsKey(classSchedule.getSchedule().getID())) {
                List<ClassSchedule> list = new ArrayList<>();
                list.add(classSchedule);
                classSchedulesBySchedule.put(classSchedule.getSchedule().getID(), list);
            }
            else
                classSchedulesBySchedule.get(classSchedule.getSchedule().getID()).add(classSchedule);
        }
        return (Map<Integer, List<ClassSchedule>>) classSchedulesBySchedule.clone();
    }

    public Map<Integer, List<Schedule>> separateSchedulesByDay(List<Schedule> schedules) {
        HashMap<Integer, List<Schedule>> schedulesByDay = new HashMap<>();
        
        for (Schedule schedule : schedules) {
            if(!schedulesByDay.containsKey(schedule.getDay())) {
                List<Schedule> list = new ArrayList<>();
                list.add(schedule);
                schedulesByDay.put(schedule.getDay(), list);
            }
            else
                schedulesByDay.get(schedule.getDay()).add(schedule);
        }
        return (Map<Integer, List<Schedule>>) schedulesByDay.clone();
    }

    public Map<Integer, List<Slot>> separateSlotsByDay(List<Slot> slots) {
        TreeMap<Integer, List<Slot>> slotsByDay = new TreeMap<>();
        
        for (Slot slot : slots) {
            if(!slotsByDay.containsKey(slot.getSchedule().getDay())) {
                List<Slot> list = new ArrayList<>();
                list.add(slot);
                slotsByDay.put(slot.getSchedule().getDay(), list);
            }
            else
                slotsByDay.get(slot.getSchedule().getDay()).add(slot);
        }
        return (Map<Integer, List<Slot>>) slotsByDay.clone();
    }
    
    public Map<Integer, List<Slot>> separateSlotsByTimeInterval(List<Slot> slots) {
        TreeMap<Integer, List<Slot>> slotsByTimeInterval = new TreeMap<>();
        
        for (Slot slot : slots) {
            if(!slotsByTimeInterval.containsKey(slot.getSchedule().getTimeInterval())) {
                List<Slot> list = new ArrayList<>();
                list.add(slot);
                slotsByTimeInterval.put(slot.getSchedule().getTimeInterval(), list);
            }
            else
                slotsByTimeInterval.get(slot.getSchedule().getTimeInterval()).add(slot);
        }

        return (Map<Integer, List<Slot>>) slotsByTimeInterval.clone();
    }
    
    public Map<Integer, List<ClassSchedule>> separateClassSchedulesByTimeInterval(List<ClassSchedule> classSchedules) {
        TreeMap<Integer, List<ClassSchedule>> classScheduleByTimeInterval = new TreeMap<>();
        
        for (ClassSchedule classSchedule : classSchedules) {
            if(!classScheduleByTimeInterval.containsKey(classSchedule.getSchedule().getTimeInterval())) {
                List<ClassSchedule> list = new ArrayList<>();
                list.add(classSchedule);
                classScheduleByTimeInterval.put(classSchedule.getSchedule().getTimeInterval(), list);
            }
            else
                classScheduleByTimeInterval.get(classSchedule.getSchedule().getTimeInterval()).add(classSchedule);
        }
        return (Map<Integer, List<ClassSchedule>>) classScheduleByTimeInterval.clone();
    }
    
    public HashMap<Integer, List<ClassSchedule>> separateClassSchedulesByClass(List<ClassSchedule> classSchedules) {
        HashMap<Integer, List<ClassSchedule>> classScheduleByClass = new HashMap<>();
        
        for (ClassSchedule classSchedule : classSchedules) {
            if(!classScheduleByClass.containsKey(classSchedule.getSchoolClass().getID())) {
                List<ClassSchedule> list = new ArrayList<>();
                list.add(classSchedule);
                classScheduleByClass.put(classSchedule.getSchoolClass().getID(), list);
            }
            else
                classScheduleByClass.get(classSchedule.getSchoolClass().getID()).add(classSchedule);
        }
        return (HashMap<Integer, List<ClassSchedule>>) classScheduleByClass.clone();
    }
    
    public HashMap<Integer, List<Slot>> separateSlotsByRoom(List<Slot> slots) {
        HashMap<Integer, List<Slot>> slotsByRoom = new HashMap<>();

        for (Slot slot : slots) {
            if(!slotsByRoom.containsKey(slot.getRoom())) {
                List<Slot> list = new ArrayList<>();
                list.add(slot);
                slotsByRoom.put(slot.getRoom(), list);
            }
            else
                slotsByRoom.get(slot.getRoom()).add(slot);
        }
        return (HashMap<Integer, List<Slot>>) slotsByRoom.clone();
    }
    
    //Method needs review
    public Schedule getNextSchedule(Schedule schedule) {
        for(Schedule sc : schedules.values())
            if(sc.equals(new Schedule(0, schedule.getDay(), schedule.getTimeInterval() + 1)))
                return (Schedule) sc.clone();
        
        return null;
    }
    
    //Method needs review
    public ClassSchedule getNextClassSchedule(ClassSchedule classSchedule) {
        Schedule next = this.getNextSchedule(classSchedule.getSchedule());
        ClassSchedule nextClassSchedule = (ClassSchedule) classSchedule.clone();
        nextClassSchedule.changeSchedule(next);
        
        for(ClassSchedule cs : this.classSchedules.values()) {
            if(cs.equals(nextClassSchedule))
                return (ClassSchedule) cs.clone();
        }
        
        return null;
    }

    public GAConfiguration getGaConfiguration() {
        return this.gaConfiguration;
    }
    
    public void setGaConfiguration(GAConfiguration gaConfiguration) {
        this.gaConfiguration = gaConfiguration;
    }

    protected GAConfiguration gaConfiguration;
    protected HashMap<String, Requirement> requirements;
    protected HashMap<String, Room> rooms;
    protected HashMap<String, Schedule> schedules;
    protected HashMap<String, SchoolClass> schoolClass;
    protected HashMap<String, ClassSchedule> classSchedules;
    protected HashMap<String,Slot> slots;
}
