package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;

public class ClassRoomExchangeCriteria extends ICriteria {
    
    public ClassRoomExchangeCriteria() {
        super(false);
    }

    @Override
    public float execute(IChromosome chromosome) {
        return 0;
    }
}