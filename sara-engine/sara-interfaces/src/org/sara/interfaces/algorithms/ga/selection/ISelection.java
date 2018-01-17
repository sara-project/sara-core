package org.sara.interfaces.algorithms.ga.selection;

import org.sara.interfaces.algorithms.ga.population.IPopulation;

public interface ISelection {
    public IPopulation select(IPopulation population, double percent);
}
