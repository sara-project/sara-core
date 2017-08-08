package org.sara.sarageneticalgorithmsplugin.events;

import org.sara.interfaces.algorithms.ga.core.IGeneration;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public class Generation implements IGeneration{
    
    private int genNumber;
    private IPopulation population;
    
    /** Creates a new instance of Generation */
    public Generation(int genNumber, IPopulation population) {
        this.setGenNumber(genNumber);
        this.setPopulation(population);
    }

    public int getGenNumber() {
        return genNumber;
    }

    private void setGenNumber(int genNumber) {
        this.genNumber = genNumber;
    }

    public IPopulation getPopulation() {
        return population;
    }

    private void setPopulation(IPopulation population) {
        this.population = population;
    }
}
