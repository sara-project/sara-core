package org.sara.sarageneticalgorithmsplugin.events;

import org.sara.interfaces.algorithms.ga.core.IGeneration;
import org.sara.interfaces.algorithms.ga.population.IPopulation;

public class Generation implements IGeneration {

    public Generation(int generation, IPopulation population) {
        this.generation = generation;
        this.population = population;
    }

    public int getNumber() {
        return generation;
    }

    public IPopulation getPopulation() {
        return population;
    }

    private final int generation;
    private final IPopulation population;
}
