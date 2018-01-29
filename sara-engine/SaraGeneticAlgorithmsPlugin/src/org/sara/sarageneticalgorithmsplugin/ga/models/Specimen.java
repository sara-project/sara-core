package org.sara.sarageneticalgorithmsplugin.ga.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.sara.interfaces.ICore;
import org.sara.interfaces.IModelController;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Slot;

public class Specimen {
    public Specimen(Object geneticLoad) {
        Slot[] slots = null;
        IModelController modelControl = ICore.getInstance().getModelController();
        
        if(geneticLoad instanceof Collection)
            slots = ((Collection<Slot>) geneticLoad).toArray(new Slot[0]);
        else
            System.err.printf("Genetic Load is invalid!");
        
        HashMap<Integer, List<Slot>> slotsByDay = (HashMap<Integer, List<Slot>>) modelControl.separateSlotsByDay(slots);
        this.chromossomes = new Chromosome[slotsByDay.size()];
        
        this.pullClassSchedule = (HashMap<Integer, List<ClassSchedule>>) modelControl.separateClassScheduleByDay(modelControl.getClassSchedule().values().toArray(new ClassSchedule[0]));
        
        int i = 0;
        for(Integer day : slotsByDay.keySet()) {
            Collections.shuffle(slotsByDay.get(day));
            Collections.shuffle(this.pullClassSchedule.get(day));
            
            this.chromossomes[i++] = new Chromosome(day, slotsByDay.get(day), this.pullClassSchedule.get(day));
        }
        this.fill();
        this.pullClassSchedule.clear();
    }
    
   
    
    public IChromosome getRandomChromosome() {
        int limit = this.chromossomes.length;
        return this.chromossomes[(new Random()).nextInt(limit)];
    }
    
    private void fill() {
        for(Chromosome chromossome : this.chromossomes) 
            chromossome.fill();
    }

    private final Chromosome[] chromossomes;
    private final HashMap<Integer, List<ClassSchedule>> pullClassSchedule;
}
