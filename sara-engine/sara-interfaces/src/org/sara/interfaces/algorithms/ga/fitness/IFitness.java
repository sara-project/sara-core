package org.sara.interfaces.algorithms.ga.fitness;

import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public interface IFitness {
    public void evaluate(IPopulation population);
}
