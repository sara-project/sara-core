package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import java.util.ArrayList;
import java.util.List;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Slot;

public class DuplicateAllocationCriteria implements ICriteria {

    @Override
    public float execute(IChromosome chromosome) {
        
        
        
        
        int totalOccurrences = 0;
        List<ClassSchedule> cSchedules = new ArrayList<>(ICore.getInstance().getModelController()
                                 .getClassScheduleByDay(chromosome.getType()).values());
        
        List<Slot> slotsFilled = new ArrayList<>();
        chromosome.getGenes(false).stream().map((gene) 
                -> (Slot) gene.getAllele(false)).filter((slot)
                -> (!slot.isEmpty())).forEachOrdered((slot)
                -> slotsFilled.add(slot));
        
        for(ClassSchedule cs : cSchedules) {
            int time = 0;
            for(Slot slot : slotsFilled) {
                if(slot.getSchoolClass().equals(cs.getSchoolClass()) && slot.getSchedule().equals(cs.getSchedule()))
                    time++;
                if(time > 1) {
                    totalOccurrences++;
                }
            }
            if(time > 1)
            {
                System.out.println();
                for(IGene gene : chromosome.getGenes(false)) {
                    Slot slot = (Slot) gene.getAllele(false);
                    if(!slot.isEmpty()){
                        System.out.println("Day: " + slot.getSchedule().getDay());
                        System.out.println("IT: " + slot.getSchedule().getTimeInterval());
                        System.out.println("Class: " + slot.getSchoolClass().getID());
                        System.out.println("Room: " + slot.getRoom().getID());
                        System.out.println("----------------------------");
                    }
                }
                System.out.println();
            }
        }
        return 0;
    }
}