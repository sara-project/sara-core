package org.sara.interfaces.algorithms.ga.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;

public abstract class IMutation {

    public void mutate(IPopulation population, double rate) {
        List<ISpecimen> mutatedList = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            ISpecimen mutated = population.getSpecimen(i, true);
            if(this.mutate(mutated, rate))
                mutatedList.add(mutated);
        }
        population.addSpecimens(mutatedList, false);
    }

    protected final boolean mutate(ISpecimen specimen, double rate) {
        boolean hasMutated = false;
        int mutationProbability = (int) (100 * rate);
        for (IChromosome chromossome : specimen.getChromossomes(false)) {
            if(mutationProbability >= ThreadLocalRandom.current().nextInt(1, 100)) {
                this.mutate(specimen.getRandomChromosome(false));
                hasMutated = true;
            }
        }
        
        return hasMutated;
    }

    public abstract void mutate(IChromosome chromosome);
}
