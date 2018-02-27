package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;

public class UnallocatedClassCriteria implements ICriteria{

    @Override
    public float execute(IChromosome chromosome) {
        int totalAllocatedClass = 0;
        int totalClassSchedule = ICore.getInstance().getModelController()
                                 .getClassScheduleByDay(chromosome.getType()).size();
       
        totalAllocatedClass = chromosome.getGenes(false).stream().map((gene) -> (Slot) gene.getAllele(false)).filter((slot) -> (!slot.isEmpty())).map((_item) -> 1).reduce(totalAllocatedClass, Integer::sum);
        
        //only for test
        if(totalClassSchedule - totalAllocatedClass == 0)
            return 100;
        return (totalClassSchedule - totalAllocatedClass);
    }
}
