package org.sara.interfaces.algorithms.ga.fitness.criterias;

import org.sara.interfaces.algorithms.ga.model.IChromosome;

public interface ICriteria {

    public float execute(IChromosome chromosome);
}
