package org.sara.interfaces.algorithms.ga.core.events;

import org.sara.interfaces.algorithms.ga.population.IPopulation;

public interface IGeneration {
    public int getGenNumber();
    public IPopulation getPopulation();  
}
