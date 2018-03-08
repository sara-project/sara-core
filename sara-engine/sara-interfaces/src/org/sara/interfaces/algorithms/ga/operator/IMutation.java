package org.sara.interfaces.algorithms.ga.operator;

import java.util.concurrent.ThreadLocalRandom;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;

public abstract class IMutation {

    public void mutate(IPopulation population, double rate) {
        int mutationProbability = (int) (100 * rate);

        for (int i = 0; i < population.size(); i++) {
            if(mutationProbability <= ThreadLocalRandom.current().nextInt(1, 100))
                this.mutate(population.getSpecimen(i, false));
        }
    }

    protected final void mutate(ISpecimen specimen) {
        this.mutate(specimen.getRandomChromosome(false));
    }

    public abstract void mutate(IChromosome chromosome);
}
