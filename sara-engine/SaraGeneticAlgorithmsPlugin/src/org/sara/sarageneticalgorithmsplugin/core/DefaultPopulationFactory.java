package org.sara.sarageneticalgorithmsplugin.core;

import org.sara.sarageneticalgorithmsplugin.defaultoptions.chromosome.DefaultPopulation;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.abstractfactories.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.population.IPopulation;


public class DefaultPopulationFactory implements IPopulationFactory{
    private IChromosome sample;

    public DefaultPopulationFactory(IChromosome sample) {
        this.sample = sample;
    }
    
    public IPopulation makePopulation(int size){
        DefaultPopulation pop = new DefaultPopulation();
        for(int i = 0; i < size ; i++)
            pop.addChromosome(sample.getRandom());
        return pop;
    };
    
}
