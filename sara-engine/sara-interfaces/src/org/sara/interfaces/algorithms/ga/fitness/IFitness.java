package org.sara.interfaces.algorithms.ga.fitness;

import org.sara.interfaces.algorithms.ga.population.IPopulation;

public interface IFitness {
    public void evaluate(IPopulation population);
}
