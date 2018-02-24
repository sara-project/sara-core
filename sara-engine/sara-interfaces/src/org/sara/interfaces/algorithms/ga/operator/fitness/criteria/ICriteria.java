package org.sara.interfaces.algorithms.ga.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;

public interface ICriteria {

    public float execute(IChromosome chromosome);
}
