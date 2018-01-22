package org.sara.sarageneticalgorithmsplugin.crossover;

import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import java.util.Random;
import org.sara.interfaces.ICore;
import org.sara.sarageneticalgorithmsplugin.defaultoptions.chromosome.IFBAPopulation;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.population.IPopulation;

public abstract class AbstractCrossover implements ICrossover {

    @Override
    public IPopulation makeOffspring(IPopulation population, int size) {
        IFBAPopulation newPop = new IFBAPopulation();
        Random parents = new Random();

        int x = 0;
        population.sortByFitness();
        while (x < (population.getSize() * ICore.getInstance().getModelController().getGaConfiguration().getElitismProbability())) {
            newPop.addChromosome(population.getChromosome(x));
            x++;
        }
        while (newPop.getSize() < ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber()) {
            IChromosome parentA = (IChromosome) population.getRandomChromosome().clone();
            IChromosome parentB = (IChromosome) population.getRandomChromosome().clone();
            this.crossover(parentA, parentB);
            newPop.addChromosome(parentA);
            if (newPop.getSize() > ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber())
                continue;
            newPop.addChromosome(parentB);
        }
        return newPop;
    }

    protected abstract void crossover(IChromosome parentA, IChromosome parentB);
}
