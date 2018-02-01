package org.sara.sarageneticalgorithmsplugin.ga.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import org.sara.interfaces.ICore;
import org.sara.interfaces.IModelController;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Slot;
import org.sara.interfaces.algorithms.ga.ISpecimen;

public class Specimen implements ISpecimen, Cloneable {

    public Specimen(Object geneticLoad) {
        List<Slot> slots = new ArrayList<>();
        IModelController modelControl = ICore.getInstance().getModelController();
        
        if(geneticLoad instanceof Collection)
            slots = (ArrayList<Slot>) geneticLoad;
        else
            System.err.printf("Genetic Load is invalid!");
        
        //Os slots estaram sempre ordenados pelo ID do Dia, facilitando a operação de Crossover
        TreeMap<Integer, List<Slot>> slotsByDay = (TreeMap<Integer, List<Slot>>) modelControl.separateSlotsByDay(slots);
        this.chromossomes = new Chromosome[slotsByDay.size()];
        
        this.pullClassSchedule = (HashMap<Integer, List<ClassSchedule>>) modelControl.separateClassScheduleByDay(new ArrayList<>(modelControl.getClassSchedule().values()));
        
        int i = 0;
        for(Integer day : slotsByDay.keySet()) {
            Collections.shuffle(slotsByDay.get(day));
            Collections.shuffle(this.pullClassSchedule.get(day));
            
            this.chromossomes[i++] = new Chromosome(day, slotsByDay.get(day), this.pullClassSchedule.get(day));
        }
        this.fill();
        this.pullClassSchedule.clear();
    }

    @Override
    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    @Override
    public Object clone() {
        Specimen clone = new Specimen();
        clone.chromossomes = this.chromossomes.clone();
        clone.pullClassSchedule = (HashMap<Integer, List<ClassSchedule>>) this.pullClassSchedule.clone();
        return clone;
    }
    
    public IChromosome getRandomChromosome() {
        int limit = this.chromossomes.length;
        return this.chromossomes[(new Random()).nextInt(limit)];
    }
    
    private void fill() {
        for(Chromosome chromossome : this.chromossomes) 
            chromossome.fill();
    }
    
    private Specimen(){}
    private float fitness;
    private Chromosome[] chromossomes;
    private HashMap<Integer, List<ClassSchedule>> pullClassSchedule;
}
