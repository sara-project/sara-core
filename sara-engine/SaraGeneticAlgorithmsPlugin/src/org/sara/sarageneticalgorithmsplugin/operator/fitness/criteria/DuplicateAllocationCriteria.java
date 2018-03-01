package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import java.util.ArrayList;
import java.util.List;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Slot;

public class DuplicateAllocationCriteria extends ICriteria {
    
    public DuplicateAllocationCriteria() {
        super(false);
    }
    
     public DuplicateAllocationCriteria(boolean required) {
        super(required);
    }
     
    @Override
    public float execute(IChromosome chromosome) {
        //Obtém todas as aulas do mesmo dia do cromossomo
        List<ClassSchedule> cSchedules = new ArrayList<>(ICore.getInstance().getModelController()
                                 .getClassScheduleByDay(chromosome.getType()).values());
        
        //Obtém todos os slots
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
                if(time > 1)
                    return 0;
            }
        }
        return 10;
    }
}