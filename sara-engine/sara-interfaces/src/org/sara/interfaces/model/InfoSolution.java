package org.sara.interfaces.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InfoSolution {

    public List<Float> getFitnessTimeLine() {
        return fitnessTimeLine;
    }

    public void setFitnessTimeLine(List<Float> fitnessTimeLine) {
        this.fitnessTimeLine = fitnessTimeLine;
    }

    public List<Object> getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(List<Object> bestSolution) {
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

    public long getAverageTimeOfFitness() {
        return averageTimeOfFitness;
    }

    public void setAverageTimeOfFitness(long averageTimeOfFitness) {
        this.averageTimeOfFitness = averageTimeOfFitness;
    }

    public long getAverageTimeOfSelection() {
        return averageTimeOfSelection;
    }

    public void setAverageTimeOfSelection(long averageTimeOfSelection) {
        this.averageTimeOfSelection = averageTimeOfSelection;
    }

    public long getAverageTimeOfCrossover() {
        return averageTimeOfCrossover;
    }

    public void setAverageTimeOfCrossover(long averageTimeOfCrossover) {
        this.averageTimeOfCrossover = averageTimeOfCrossover;
    }

    public long getAverageTimeOfMutation() {
        return averageTimeOfMutation;
    }

    public void setAverageTimeOfMutation(long averageTimeOfMutation) {
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

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public int getTotalFilledSlots() {
        return totalFilledSlots;
    }

    public void setTotalFilledSlots(int totalFilledSlots) {
        this.totalFilledSlots = totalFilledSlots;
    }

    public int getTotalEmptySlots() {
        return totalEmptySlots;
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

    public long getAverageTimeOfRefreshPopulation() {
        return averageTimeOfRefreshPopulation;
    }

    public void setAverageTimeOfRefreshPopulation(long averageTimeOfRefreshPopulation) {
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

    public InfoSolution() {
        this.timeOfGenerateInitialPopulation = -1;
        this.averageTimeOfFitness = -1;
        this.averageTimeOfSelection = -1;
        this.averageTimeOfCrossover = -1;
        this.averageTimeOfMutation = -1;
        this.averageTimeOfRefreshPopulation = -1;
        
        this.totalSlots = -1;
        this.totalFilledSlots = -1;
        this.totalEmptySlots = -1;
        this.totalOfAllocatedScheduleClasses = -1;
        this.totalOfUnallocatedScheduleClasses = -1;
        
        this.fitnessByCriterias = new HashMap<>();
    }

    private int totalSlots;
    private int totalFilledSlots;
    private int totalEmptySlots;
    private int totalOfAllocatedScheduleClasses;
    private int totalOfUnallocatedScheduleClasses;
    private long timeOfGenerateInitialPopulation;
    private long averageTimeOfFitness;
    private long averageTimeOfSelection;
    private long averageTimeOfCrossover;
    private long averageTimeOfMutation;
    private long averageTimeOfRefreshPopulation;
    private Date startTime;
    private Date endTime;
    private List<Float> fitnessTimeLine;
    private List<Object> bestSolution;
    private float fitnessOfTheBestSolution;
    private String totalMemoryUsed;
    private HashMap<String, Float> fitnessByCriterias;
}
