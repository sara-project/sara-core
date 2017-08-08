package org.sara.sarageneticalgorithmsplugin.fitness;

import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import java.util.List;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public abstract class AbstractFitnessFunction implements IFitness {

    @Override
    public void evaluate(IPopulation population) {
        List chromosomes = population.getAllChromosomes();
        for (int i = 0; i < chromosomes.size(); i++) {
            IChromosome chromosome = (IChromosome) chromosomes.get(i);
            this.evaluate(chromosome);
        }
    }

    private void evaluate(IChromosome chromosome) {
        float fitness = this.getFitnessValue(chromosome);
        chromosome.setFitness(fitness);
    }

    protected abstract float getFitnessValue(IChromosome chromosome);
}
