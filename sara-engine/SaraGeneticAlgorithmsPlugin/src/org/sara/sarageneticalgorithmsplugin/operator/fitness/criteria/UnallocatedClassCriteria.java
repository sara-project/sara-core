package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;

public class UnallocatedClassCriteria extends ICriteria {

    public UnallocatedClassCriteria() {
        this(false);
    }

    public UnallocatedClassCriteria(boolean required) {
        super(required);
    }

    @Override
    public float execute(IChromosome chromosome) {
        int totalAllocatedClass = 0;
        int totalClassSchedule = ICore.getInstance().getModelController()
                .getClassScheduleByDay(chromosome.getType()).size();

        totalAllocatedClass = chromosome.getGenes(false).stream().map((gene) -> (Slot) gene.getAllele(false)).filter((slot) -> (!slot.isEmpty())).map((_item) -> 1).reduce(totalAllocatedClass, Integer::sum);

        return (float) totalAllocatedClass / (float) totalClassSchedule;
    }
}
