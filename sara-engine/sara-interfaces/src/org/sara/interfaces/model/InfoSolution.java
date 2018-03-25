package org.sara.interfaces.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;

public class InfoSolution {

    public List<Float> getFitnessTimeLine() {
        return fitnessTimeLine;
    }

    public void setFitnessTimeLine(List<Float> fitnessTimeLine) {
        this.fitnessTimeLine = fitnessTimeLine;
    }

    public List<IChromosome> getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(List<IChromosome> bestSolution) {
        this.bestSolution = bestSolution;
    }

    public float getFitnessOfTheBestSolution() {
        return fitnessOfTheBestSolution;
    }

    public void setFitnessOfTheBestSolution(float fitnessOfTheBestSolution) {
        this.fitnessOfTheBestSolution = fitnessOfTheBestSolution;
    }

    public long getTimeOfGenerateInitialPopulation() {
        return timeOfGenerateInitialPopulation;
    }

    public void setTimeOfGenerateInitialPopulation(long timeOfGenerateInitialPopulation) {
        this.timeOfGenerateInitialPopulation = timeOfGenerateInitialPopulation;
    }

    public double getAverageTimeOfFitness() {
        return averageTimeOfFitness;
    }

    public void setAverageTimeOfFitness(double averageTimeOfFitness) {
        this.averageTimeOfFitness = averageTimeOfFitness;
    }

    public double getAverageTimeOfSelection() {
        return averageTimeOfSelection;
    }

    public void setAverageTimeOfSelection(double averageTimeOfSelection) {
        this.averageTimeOfSelection = averageTimeOfSelection;
    }

    public double getAverageTimeOfCrossover() {
        return averageTimeOfCrossover;
    }

    public void setAverageTimeOfCrossover(double averageTimeOfCrossover) {
        this.averageTimeOfCrossover = averageTimeOfCrossover;
    }

    public double getAverageTimeOfMutation() {
        return averageTimeOfMutation;
    }

    public void setAverageTimeOfMutation(double averageTimeOfMutation) {
        this.averageTimeOfMutation = averageTimeOfMutation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTotalMemoryUsed() {
        return totalMemoryUsed;
    }

    public void setTotalMemoryUsed(String totalMemoryUsed) {
        this.totalMemoryUsed = totalMemoryUsed;
    }

    public int getTotalFilledSlots(List<IChromosome> bestSolution) {
        if(this.totalFilledSlots == -1)
            this.calculateAccessibilityRequirement(bestSolution);

        return totalFilledSlots;
    }

    public void setTotalFilledSlots(int totalFilledSlots) {
        this.totalFilledSlots = totalFilledSlots;
    }

    public int getTotalEmptySlots(List<IChromosome> bestSolution) {
        if(this.totalEmptySlots == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return totalEmptySlots;
    }
    
    public void setTotalScheduleClasses(int totalScheduleClasses) {
        this.totalScheduleClasses = totalScheduleClasses;
    }

    public long getTotalScheduleClasses(List<IChromosome> bestSolution) {
        if(this.totalScheduleClasses == -1)
            this.calculateAccessibilityRequirement(bestSolution);

        return totalScheduleClasses;
    }

    public void setTotalEmptySlots(int totalEmptySlots) {
        this.totalEmptySlots = totalEmptySlots;
    }

    public int getTotalOfAllocatedScheduleClasses() {
        return totalOfAllocatedScheduleClasses;
    }

    public void setTotalOfAllocatedScheduleClasses(int totalOfAllocatedScheduleClasses) {
        this.totalOfAllocatedScheduleClasses = totalOfAllocatedScheduleClasses;
    }

    public int getTotalOfUnallocatedScheduleClasses() {
        return totalOfUnallocatedScheduleClasses;
    }

    public void setTotalOfUnallocatedScheduleClasses(int totalOfUnallocatedScheduleClasses) {
        this.totalOfUnallocatedScheduleClasses = totalOfUnallocatedScheduleClasses;
    }

    public double getAverageTimeOfRefreshPopulation() {
        return averageTimeOfRefreshPopulation;
    }

    public void setAverageTimeOfRefreshPopulation(double averageTimeOfRefreshPopulation) {
        this.averageTimeOfRefreshPopulation = averageTimeOfRefreshPopulation;
    }
    
    public long getExecutionTime() {
        if(this.endTime == null||  this.startTime == null )
            return -1;
        return this.endTime.getTime() - this.startTime.getTime();
    }

    public HashMap<String, Float> getFitnessByCriterias() {
        return fitnessByCriterias;
    }

    public void setFitnessByCriterias(HashMap<String, Float> fitnessByCriterias) {
        this.fitnessByCriterias = fitnessByCriterias;
    }
 
    public int getTotalMeetsAccessibilityRequirement(List<IChromosome> bestSolution) {
        if(this.totalMeetsAccessibilityRequirement == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return this.totalMeetsAccessibilityRequirement;
    }
    
    public int getTotalAccessibilityRequirement(List<IChromosome> bestSolution) {
        if(this.totalAccessibilityRequirement == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return this.totalAccessibilityRequirement;
    }
    
    public int getTotalDoesntMeetsAccessibilityRequirement(List<IChromosome> bestSolution) {      
        if(this.totalDoesntMeetsAccessibilityRequirement == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return this.totalDoesntMeetsAccessibilityRequirement;
    }
    
    public int getTotalDuplicateAllocation(List<IChromosome> bestSolution) {
        if(this.totalDuplicateAllocation == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return this.totalDuplicateAllocation;
    }
    
    public int getAllocatedClassSchedules(List<IChromosome> bestSolution) {
        if(this.allocatedClassSchedules == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return this.allocatedClassSchedules;
    }

    public int getUnallocatedClassSchedules(List<IChromosome> bestSolution) {
        if(this.unallocatedClassSchedules == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return this.unallocatedClassSchedules;
    }
    
    public int getUnusedPlaces(List<IChromosome> bestSolution) {
        if(this.unusedPlaces == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return this.unusedPlaces;
    }
     
    public int getOverLoadRooms(List<IChromosome> bestSolution) {
        if(this.overLoadRooms == -1)
            this.calculateAccessibilityRequirement(bestSolution);
        
        return this.overLoadRooms;
    }
    

    public InfoSolution() {
        this.timeOfGenerateInitialPopulation = -1;
        this.averageTimeOfFitness = -1;
        this.averageTimeOfSelection = -1;
        this.averageTimeOfCrossover = -1;
        this.averageTimeOfMutation = -1;
        this.averageTimeOfRefreshPopulation = -1;
        
        this.totalFilledSlots = -1;
        this.totalEmptySlots = -1;
        this.totalOfAllocatedScheduleClasses = -1;
        this.totalOfUnallocatedScheduleClasses = -1;
        this.totalScheduleClasses = -1;
        this.totalDuplicateAllocation = -1;

        this.fitnessByCriterias = new HashMap<>();

        this.totalMeetsAccessibilityRequirement = -1;
        this.totalDoesntMeetsAccessibilityRequirement = -1;
        this.totalAccessibilityRequirement = -1;
        
        this.allocatedClassSchedules = -1;
        this.unallocatedClassSchedules = -1;
        
        this.unusedPlaces = -1;
        this.overLoadRooms = -1;
    }
    
    private void calculateAccessibilityRequirement(List<IChromosome> bestSolution) {
        int totalMeetsRequirement = 0;
        int totalDoesntMeetsRequirement = 0;
        int _totalFilledSlots = 0;
        int _totalEmptySlots = 0;
        int totalClassSchedule = 0;
        int totalAllocatedClass = 0;
        int _totalDuplicateAllocation = 0;
        int totalUnusedPlaces = 0;
        int totalOverLoadRooms = 0;
        int _totalAccessibilityRequirement = 0;

        HashMap<Integer, ClassSchedule> allocatedClassSchedulesHash = new HashMap();

        for(IChromosome chromosome : bestSolution) {
            List<ClassSchedule> cSchedules =  new ArrayList<>(ICore.getInstance().getModelController()
                                 .getClassScheduleByDay(chromosome.getType()).values());
            
            
            totalClassSchedule += cSchedules.size();
            
            totalAllocatedClass += chromosome.getGenes(false)
                       .stream().map((gene) -> (Slot) gene.getAllele(false))
                       .filter((slot) -> (!slot.isEmpty()))
                       .map((_item) -> 1).reduce(totalAllocatedClass, Integer::sum);
            
             for(IGene gene : chromosome.getGenes(false)) {
                Slot slot = (Slot) gene.getAllele(false);

                if(!slot.isEmpty()) {
                    _totalFilledSlots++;
                    int numberOfAllocatedcSchedules = cSchedules.stream().filter((cs) -> (slot.getSchoolClass().equals(cs.getSchoolClass()) && slot.getSchedule().equals(cs.getSchedule()))).map((_item) -> 1).reduce(_totalDuplicateAllocation, Integer::sum);
                    
                    if(numberOfAllocatedcSchedules > slot.getSchoolClass().getAllSchoolClassSchedules().size())
                         _totalDuplicateAllocation = numberOfAllocatedcSchedules -slot.getSchoolClass().getAllSchoolClassSchedules().size();

                    ClassSchedule cs = slot.getClassSchedule();

                    if(slot.getRoom().getCapacity() >= slot.getSchoolClass().getSize())
                        totalUnusedPlaces += (slot.getRoom().getCapacity() - slot.getSchoolClass().getSize());
                    else
                        totalOverLoadRooms++;
                    
                    if(!allocatedClassSchedulesHash.containsKey(cs.getID()))
                        allocatedClassSchedulesHash.put(cs.getID(), cs);
                    
                    if(slot.getSchoolClass().hasAccessibilityRequirement())
                        _totalAccessibilityRequirement++;

                    if(slot.getSchoolClass().hasAccessibilityRequirement() && slot.getRoom().hasAccessibilityRequirement())
                        totalMeetsRequirement++;
                    else if(slot.getSchoolClass().hasAccessibilityRequirement() && !slot.getRoom().hasAccessibilityRequirement())
                        totalDoesntMeetsRequirement++;
                } else
                    _totalEmptySlots++;
            }
        }
        
        
        this.unusedPlaces = totalUnusedPlaces;
        this.overLoadRooms = totalOverLoadRooms;
        
        this.allocatedClassSchedules = allocatedClassSchedulesHash.size();
        this.unallocatedClassSchedules = totalClassSchedule - allocatedClassSchedules;
        
        this.totalDuplicateAllocation = _totalDuplicateAllocation;
        this.totalScheduleClasses = totalClassSchedule;
        this.totalOfAllocatedScheduleClasses = totalAllocatedClass;
        this.totalOfUnallocatedScheduleClasses = totalClassSchedule - totalAllocatedClass;
        
        this.totalFilledSlots = _totalFilledSlots;
        this.totalEmptySlots = _totalEmptySlots;
        this.totalMeetsAccessibilityRequirement = totalMeetsRequirement;
        this.totalDoesntMeetsAccessibilityRequirement = totalDoesntMeetsRequirement;
        this.totalAccessibilityRequirement = _totalAccessibilityRequirement;
    }
    
    private int totalAccessibilityRequirement;
    private int overLoadRooms;
    private int unusedPlaces;
    private int allocatedClassSchedules;
    private int unallocatedClassSchedules;
    private int totalDuplicateAllocation;
    private int totalMeetsAccessibilityRequirement;
    private int totalDoesntMeetsAccessibilityRequirement;
    private int totalFilledSlots;
    private int totalEmptySlots;
    private int totalScheduleClasses;
    private int totalOfAllocatedScheduleClasses;
    private int totalOfUnallocatedScheduleClasses;
    private long timeOfGenerateInitialPopulation;
    private double averageTimeOfFitness;
    private double averageTimeOfSelection;
    private double averageTimeOfCrossover;
    private double averageTimeOfMutation;
    private double averageTimeOfRefreshPopulation;
    private Date startTime;
    private Date endTime;
    private List<Float> fitnessTimeLine;
    private List<IChromosome> bestSolution;
    private float fitnessOfTheBestSolution;
    private String totalMemoryUsed;
    private HashMap<String, Float> fitnessByCriterias;
}
