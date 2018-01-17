package org.sara.interfaces;

import java.util.HashMap;
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

    public HashMap<String, Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(HashMap<String, Requirement> requirements) {
        this.requirements = requirements;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    public HashMap<String, Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(HashMap<String, Schedule> schedules) {
        this.schedules = schedules;
    }

    public HashMap<String, SchoolClass> getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(HashMap<String, SchoolClass> schoolClass) {
        this.schoolClass = schoolClass;
    }

    public HashMap<String, Slot> getSlots() {
        return slots;
    }

    public void setSlots(HashMap<String, Slot> slots) {
        this.slots = slots;
    }

    public GAConfiguration getGaConfiguration() {
        return gaConfiguration;
    }
    
    public void setGaConfiguration(GAConfiguration gaConfiguration) {
        this.gaConfiguration = gaConfiguration;
    }

    protected GAConfiguration gaConfiguration;
    protected HashMap<String, Requirement> requirements;
    protected HashMap<String, Room> rooms;
    protected HashMap<String, Schedule> schedules;
    protected HashMap<String,SchoolClass> schoolClass;
    protected HashMap<String,Slot> slots;
}
