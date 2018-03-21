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
            
            boolean hasMutated = false;
            int mutationProbability = (int) (100 * rate);
            for (IChromosome chromossome : mutated.getChromossomes(false)) {
                if(mutationProbability >= ThreadLocalRandom.current().nextInt(1, 100)) {
                    chromossome = this.mutateChromosome(mutated.getRandomChromosome(false));
                    chromossome.resetFitness();
                    hasMutated = true;
                }
            }

            if(hasMutated)
                mutatedList.add(mutated);
        }
        population.addSpecimens(mutatedList, false);
    }

    public abstract IChromosome mutateChromosome(IChromosome chromosome);
}
