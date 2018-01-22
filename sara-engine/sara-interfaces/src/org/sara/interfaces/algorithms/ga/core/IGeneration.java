package org.sara.interfaces.algorithms.ga.core;

import org.sara.interfaces.algorithms.ga.population.IPopulation;

public interface IGeneration {
    public int getNumber();
    public IPopulation getPopulation();
}
