package org.sara.sarageneticalgorithmsplugin;

import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulationFactory;
import org.sara.sarageneticalgorithmsplugin.defaultoptions.chromosome.DefaultPopulation;

public class DefaultPopulationFactory implements IPopulationFactory {

    public DefaultPopulationFactory(IChromosome chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public IPopulation makePopulation(int size) {
        DefaultPopulation pop = new DefaultPopulation();
        for (int i = 0; i < size; i++) {
            pop.addChromosome(chromosome.getRandom());
        }
        return pop;
    }
    
    private final IChromosome chromosome;
}
