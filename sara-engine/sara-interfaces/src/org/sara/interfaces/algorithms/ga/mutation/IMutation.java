package org.sara.interfaces.algorithms.ga.mutation;

import org.sara.interfaces.algorithms.ga.population.IPopulation;

public interface IMutation {
    public void mutate(IPopulation population, double rate);
}
