package org.sara.interfaces.algorithms.ga.mutation;

import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public interface IMutation {
    public void mutate(IPopulation population, float rate);
}
