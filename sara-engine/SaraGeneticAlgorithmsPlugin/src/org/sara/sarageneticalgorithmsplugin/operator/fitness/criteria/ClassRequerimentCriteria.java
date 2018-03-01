package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;

public class ClassRequerimentCriteria extends ICriteria {

    public ClassRequerimentCriteria() {
        super(false);
    }

        
    public ClassRequerimentCriteria(boolean required) {
        super(required);
    }

    @Override
    public float execute(IChromosome chromosome) {
        return 0;
    }
}