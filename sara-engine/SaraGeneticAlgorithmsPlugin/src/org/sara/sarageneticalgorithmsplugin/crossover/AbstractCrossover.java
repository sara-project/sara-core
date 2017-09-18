package org.sara.sarageneticalgorithmsplugin.crossover;

import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import java.util.Random;
import org.sara.sarageneticalgorithmsplugin.defaultoptions.chromosome.DefaultPopulation;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public abstract class AbstractCrossover implements ICrossover {

    public AbstractCrossover() {
        this.parameterInfos = new GAConfigLoader();
    }

    @Override
    public IPopulation makeOffspring(IPopulation population, int size) {
        DefaultPopulation newPop = new DefaultPopulation();
        Random parents = new Random();

        int x = 0;
        population.sortByFitness();
        while (x < (population.size() * parameterInfos.getNumeroElitismo())) {
            newPop.addChromosome(population.getChromosome(x));
            x++;
        }
        while (newPop.size() < parameterInfos.getPopulationSize()) {
            IChromosome parentA = (IChromosome) population.getRandomChromosome().clone();
            IChromosome parentB = (IChromosome) population.getRandomChromosome().clone();
            this.crossover(parentA, parentB);
            newPop.addChromosome(parentA);
            if (newPop.size() > parameterInfos.getPopulationSize())
                continue;
            newPop.addChromosome(parentB);
        }
        return newPop;
    }

    protected abstract void crossover(IChromosome parentA, IChromosome parentB);

    protected GAConfigLoader parameterInfos;
}
