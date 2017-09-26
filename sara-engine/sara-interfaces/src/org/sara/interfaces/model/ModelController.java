package org.sara.interfaces.model;

import java.util.HashMap;


public class ModelController {
    
    public static ModelController getInstaController() {
        if(instance == null)
            instance = new ModelController();
        return instance;
    }

    private ModelController() {
        requirements = new HashMap<>();
        rooms = new HashMap<>();
        schedules = new HashMap<>();
        schoolClass = new HashMap<>();
        slots = new HashMap<>();
    }
    
    private static ModelController instance;
    private HashMap<Integer, Requirement> requirements;
    private HashMap<Integer, Room> rooms;
    private HashMap<Integer, Schedule> schedules;
    private HashMap<Integer,Slot> schoolClass;
    private HashMap<Integer,Slot> slots;
}
