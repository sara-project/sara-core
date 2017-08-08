package org.sara.interfaces.algorithms.ga.crossover;

import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public interface ICrossover {
    public IPopulation makeOffspring(IPopulation population, int size);    
}
