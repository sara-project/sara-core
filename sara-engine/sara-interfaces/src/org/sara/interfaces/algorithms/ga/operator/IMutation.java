package org.sara.interfaces.algorithms.ga.operator;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;

public abstract class IMutation {

    public void mutate(IPopulation population, double rate) {
        int mutationsNumber = (int) (population.size() * rate);

        for (int i = 0; i < mutationsNumber; i++) {
            this.mutate(population.getRandomSpecimen(false));
        }
    }

    protected final void mutate(ISpecimen specimen) {
        this.mutate(specimen.getRandomChromosome(false));
    }

    public abstract void mutate(IChromosome chromosome);
}
