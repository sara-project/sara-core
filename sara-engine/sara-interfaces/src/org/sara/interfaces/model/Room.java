package org.sara.interfaces.model;

import java.util.ArrayList;
import java.util.List;

public class Room implements Cloneable {
    
    public Room(int id, int capacity, int area, List<Requirement> requirements) {
        this.id = id;
        this.capacity = capacity;
        this.area = area;
        this.specifications = requirements;
    }

    public Room(int id, int capacity, int area) {
        this(id, capacity, area, new ArrayList<>());
    }

    public boolean thisFits(SchoolClass schoolClass) {
        return schoolClass.thisFits(capacity);
    }
    
    public int useOfRoom(SchoolClass schoolClass) {
        return schoolClass.howBig(capacity);
    }

    public int getID() {
        return this.id;
    }
    
    public int getArea() {
        return this.area;
    }

    public int getCapacity() {
        return this.capacity;
    }
    
    public List<Requirement> getSpecifications(boolean clone) {
        if(!clone)
            return this.specifications;
        
        List<Requirement> tmp = new ArrayList<>();
        this.specifications.forEach(r -> tmp.add((Requirement) r.clone()));
        return tmp;
    }
    
    @Override
    public Object clone() {
        return new Room(id, capacity, area, this.getSpecifications(true));
    }
    
    public boolean containsRequirements(Requirement requirement) {
        return this.specifications.contains(requirement);
    }
    
    public boolean hasAccessibilityRequirement() {
        return this.specifications.stream().anyMatch((s) -> (s.getType() == 2));
    }

    private final int id;
    private final int area;
    private final int capacity;
    private final List<Requirement> specifications;
}
