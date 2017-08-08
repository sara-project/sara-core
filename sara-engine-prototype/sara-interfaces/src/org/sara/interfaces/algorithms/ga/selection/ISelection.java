package org.sara.interfaces.algorithms.ga.selection;

import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public interface ISelection {
    public IPopulation select(IPopulation population, float percent);
}
