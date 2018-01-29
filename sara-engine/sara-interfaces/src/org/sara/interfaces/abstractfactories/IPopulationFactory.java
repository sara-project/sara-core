package org.sara.interfaces.abstractfactories;

import org.sara.interfaces.algorithms.ga.population.IPopulation;

public interface IPopulationFactory {
    public IPopulation makePopulation();
}
