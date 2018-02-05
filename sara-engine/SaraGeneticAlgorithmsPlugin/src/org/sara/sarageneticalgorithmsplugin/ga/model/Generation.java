package org.sara.sarageneticalgorithmsplugin.ga.model;

import org.sara.interfaces.algorithms.ga.model.IGeneration;
import org.sara.interfaces.algorithms.ga.model.IPopulation;

public class Generation implements IGeneration {

    public Generation(int generation, IPopulation population) {
        this.generation = generation;
        this.population = population;
    }

    @Override
    public int getNumber() {
        return generation;
    }

    @Override
    public IPopulation getPopulation() {
        return population;
    }

    private final int generation;
    private final IPopulation population;
}
