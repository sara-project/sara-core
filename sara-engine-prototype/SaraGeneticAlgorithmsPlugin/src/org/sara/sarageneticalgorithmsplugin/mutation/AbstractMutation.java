package org.sara.sarageneticalgorithmsplugin.mutation;

import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public abstract class AbstractMutation implements IMutation {

    @Override
    public void mutate(IPopulation population, float rate) {
        int qtdeMutations = (int) (population.size() * rate);
        for (int i = 0; i < qtdeMutations; i++) {
            IChromosome chromosome = population.getRandomChromosome();
            this.mutate(chromosome);
        }
    }

    protected abstract void mutate(IChromosome chromosome);
}
