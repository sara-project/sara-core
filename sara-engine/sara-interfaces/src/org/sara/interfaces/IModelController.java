package org.sara.interfaces;

import java.util.ArrayList;
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
        this.requirements = new HashMap<>();
        this.rooms = new HashMap<>();
        this.schedules = new HashMap<>();
        this.schoolClass = new HashMap<>();
        this.slots = new HashMap<>();

        this.gaConfiguration = new GAConfiguration();
    }

    public Map<String, Requirement> getRequirements() {
        Map<String, Requirement> clone = new HashMap<>();
        this.requirements.values().forEach((r) -> clone.put(r.getID(), (Requirement) r.clone()));

        return clone;
    }

    public void setRequirements(Map<String, Requirement> requirements) {
        Map<String, Requirement> clone = new HashMap<>();
        requirements.values().forEach((r) -> clone.put(r.getID(), (Requirement) r.clone()));
        this.requirements = clone;
    }

    public Map<String, Room> getRooms() {
        Map<String, Room> clone = new HashMap<>();
        this.rooms.values().forEach((r) -> clone.put(String.valueOf(r.getID()), (Room) r.clone()));
        return clone;
    }

    public void setRooms(HashMap<String, Room> rooms) {
        Map<String, Room> clone = new HashMap<>();
        rooms.values().forEach((r) -> clone.put(String.valueOf(r.getID()), (Room) r.clone()));
        this.rooms = rooms;
    }

    public Map<String, Schedule> getSchedules() {
        Map<String, Schedule> clone = new HashMap<>();
        this.schedules.values().forEach((s) -> clone.put(String.valueOf(s.getID()), (Schedule) s.clone()));
        return clone;
    }

    public void setSchedules(HashMap<String, Schedule> schedules) {
        Map<String, Schedule> clone = new HashMap<>();
        schedules.values().forEach((s) -> clone.put(String.valueOf(s.getID()), (Schedule) s.clone()));
        this.schedules = clone;
    }

    public Map<String, SchoolClass> getSchoolClass() {
        Map<String, SchoolClass> clone = new HashMap<>();
        this.schoolClass.values().forEach((s) -> clone.put(String.valueOf(s.getID()), (SchoolClass) s.clone()));
        return clone;
    }

    public void setSchoolClass(HashMap<String, SchoolClass> schoolClass) {
        Map<String, SchoolClass> clone = new HashMap<>();
        schoolClass.values().forEach((s) -> clone.put(String.valueOf(s.getID()), (SchoolClass) s.clone()));
        this.schoolClass = clone;
    }

    public Map<String, ClassSchedule> getClassSchedule() {
        Map<String, ClassSchedule> clone = new HashMap<>();
        this.classSchedules.values().forEach((cs) -> clone.put(String.valueOf(cs.getID()), (ClassSchedule) cs.clone()));
        return clone;
    }
    
    public void setClassSchedule(HashMap<String, ClassSchedule> classSchedules) {
        Map<String, ClassSchedule> clone = new HashMap<>();
        classSchedules.values().forEach((cs) -> clone.put(String.valueOf(cs.getID()), (ClassSchedule) cs.clone()));
        this.classSchedules = clone;
    }

    public Map<String, Slot> getSlots() {
        Map<String, Slot> clone = new HashMap<>();
        this.slots.values().forEach((s) -> clone.put(String.valueOf(s.getID()), (Slot) s.clone()));
        return clone;
    }

    public void setSlots(HashMap<String, Slot> slots) {
        Map<String, Slot> clone = new HashMap<>();
        slots.values().forEach((s) -> clone.put(String.valueOf(s.getID()), (Slot) s.clone()));
        this.slots = clone;
    }
    
    public GAConfiguration getGaConfiguration() {
        return this.gaConfiguration;
    }

    public void setGaConfiguration(GAConfiguration gaConfiguration) {
        this.gaConfiguration = gaConfiguration;
    }

    public Map<Integer, List<ClassSchedule>> separateClassScheduleByDay(final List<ClassSchedule> classSchedules) {
        Map<Integer, List<ClassSchedule>> classSchedulesByDay = new HashMap<>();

        classSchedules.forEach((classSchedule) -> {
            if (!classSchedulesByDay.containsKey(classSchedule.getSchedule().getDay())) {
                List<ClassSchedule> list = new ArrayList<>();
                list.add((ClassSchedule) classSchedule.clone());
                classSchedulesByDay.put(classSchedule.getSchedule().getDay(), list);
            } else {
                classSchedulesByDay.get(classSchedule.getSchedule().getDay()).add((ClassSchedule) classSchedule.clone());
            }
        });
        return classSchedulesByDay;
    }

    public Map<Integer, List<ClassSchedule>> ClassScheduleBySchedule(final List<ClassSchedule> classSchedules) {
        Map<Integer, List<ClassSchedule>> classSchedulesBySchedule = new HashMap<>();

        classSchedules.forEach((classSchedule) -> {
            if (!classSchedulesBySchedule.containsKey(classSchedule.getSchedule().getID())) {
                List<ClassSchedule> list = new ArrayList<>();
                list.add((ClassSchedule) classSchedule.clone());
                classSchedulesBySchedule.put(classSchedule.getSchedule().getID(), list);
            } else {
                classSchedulesBySchedule.get(classSchedule.getSchedule().getID()).add((ClassSchedule) classSchedule.clone());
            }
        });
        return classSchedulesBySchedule;
    }

    public Map<Integer, List<Schedule>> separateSchedulesByDay(final List<Schedule> schedules) {
        Map<Integer, List<Schedule>> schedulesByDay = new HashMap<>();

        schedules.forEach((schedule) -> {
            if (!schedulesByDay.containsKey(schedule.getDay())) {
                List<Schedule> list = new ArrayList<>();
                list.add((Schedule) schedule);
                schedulesByDay.put(schedule.getDay(), list);
            } else {
                schedulesByDay.get(schedule.getDay()).add((Schedule) schedule);
            }
        });
        return schedulesByDay;
    }

    public Map<Integer, List<Slot>> separateSlotsByDay(final List<Slot> slots) {
        Map<Integer, List<Slot>> slotsByDay = new TreeMap<>();

        slots.forEach((slot) -> {
            if (!slotsByDay.containsKey(slot.getSchedule().getDay())) {
                List<Slot> list = new ArrayList<>();
                list.add((Slot) slot.clone());
                slotsByDay.put(slot.getSchedule().getDay(), list);
            } else {
                slotsByDay.get(slot.getSchedule().getDay()).add((Slot) slot.clone());
            }
        });
        return slotsByDay;
    }

    public Map<Integer, List<Slot>> separateSlotsByTimeInterval(final List<Slot> slots) {
        Map<Integer, List<Slot>> slotsByTimeInterval = new TreeMap<>();

        slots.forEach((slot) -> {
            if (!slotsByTimeInterval.containsKey(slot.getSchedule().getTimeInterval())) {
                List<Slot> list = new ArrayList<>();
                list.add((Slot) slot.clone());
                slotsByTimeInterval.put(slot.getSchedule().getTimeInterval(), list);
            } else {
                slotsByTimeInterval.get(slot.getSchedule().getTimeInterval()).add((Slot) slot.clone());
            }
        });

        return slotsByTimeInterval;
    }

    public Map<Integer, List<ClassSchedule>> separateClassSchedulesByTimeInterval(final List<ClassSchedule> classSchedules) {
        Map<Integer, List<ClassSchedule>> classScheduleByTimeInterval = new TreeMap<>();

        classSchedules.forEach((classSchedule) -> {
            if (!classScheduleByTimeInterval.containsKey(classSchedule.getSchedule().getTimeInterval())) {
                List<ClassSchedule> list = new ArrayList<>();
                list.add((ClassSchedule) classSchedule.clone());
                classScheduleByTimeInterval.put(classSchedule.getSchedule().getTimeInterval(), list);
            } else {
                classScheduleByTimeInterval.get(classSchedule.getSchedule().getTimeInterval()).add((ClassSchedule) classSchedule.clone());
            }
        });
        return classScheduleByTimeInterval;
    }

    public Map<Integer, List<ClassSchedule>> separateClassSchedulesByClass(final List<ClassSchedule> classSchedules) {
        Map<Integer, List<ClassSchedule>> classScheduleByClass = new HashMap<>();

        classSchedules.forEach((classSchedule) -> {
            if (!classScheduleByClass.containsKey(classSchedule.getSchoolClass().getID())) {
                List<ClassSchedule> list = new ArrayList<>();
                list.add((ClassSchedule) classSchedule.clone());
                classScheduleByClass.put(classSchedule.getSchoolClass().getID(), list);
            } else {
                classScheduleByClass.get(classSchedule.getSchoolClass().getID()).add((ClassSchedule) classSchedule.clone());
            }
        });
        return classScheduleByClass;
    }

    public Map<Integer, List<Slot>> separateSlotsByRoom(final List<Slot> slots) {
        Map<Integer, List<Slot>> slotsByRoom = new HashMap<>();

        slots.forEach((slot) -> {
            if (!slotsByRoom.containsKey(slot.getRoom())) {
                List<Slot> list = new ArrayList<>();
                list.add((Slot) slot.clone());
                slotsByRoom.put(slot.getRoom(), list);
            } else {
                slotsByRoom.get(slot.getRoom()).add((Slot) slot.clone());
            }
        });
        return slotsByRoom;
    }

    public Schedule getNextSchedule(final Schedule schedule) {
        for (Schedule sc : this.schedules.values()) {
            if (sc.equals(new Schedule(0, schedule.getDay(), schedule.getTimeInterval() + 1)))
                return (Schedule) sc.clone();
        }

        return null;
    }

    public ClassSchedule getNextClassSchedule(final ClassSchedule classSchedule) {
        Schedule next = this.getNextSchedule(classSchedule.getSchedule());
        ClassSchedule nextClassSchedule = (ClassSchedule) classSchedule.clone();
        nextClassSchedule.changeSchedule(next);

        for (ClassSchedule cs : this.classSchedules.values()) {
            if (cs.equals(nextClassSchedule))
                return (ClassSchedule) cs.clone();
        }

        return null;
    }

    protected GAConfiguration gaConfiguration;
    protected Map<String, Requirement> requirements;
    protected Map<String, Room> rooms;
    protected Map<String, Schedule> schedules;
    protected Map<String, SchoolClass> schoolClass;
    protected Map<String, ClassSchedule> classSchedules;
    protected Map<String, Slot> slots;
}
