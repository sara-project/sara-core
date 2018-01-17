package org.sara.interfaces.algorithms.ga.crossover;

import org.sara.interfaces.algorithms.ga.population.IPopulation;

public interface ICrossover {
    public IPopulation makeOffspring(IPopulation population, int size);    
}
