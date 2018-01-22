package org.sara.sarageneticalgorithmsplugin.core;

import org.sara.sarageneticalgorithmsplugin.defaultoptions.chromosome.IFBAPopulation;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.abstractfactories.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.population.IPopulation;


public class PopulationFactory implements IPopulationFactory{
    public PopulationFactory() {
    }
    
    @Override
    public IPopulation makePopulation(int size){
        IFBAPopulation population = new IFBAPopulation();
        for(int i = 0; i < size ; i++)
            population.addChromosome(chromosome.getRandom());
        return population;
    };
    
    private IChromosome chromosome;
}
