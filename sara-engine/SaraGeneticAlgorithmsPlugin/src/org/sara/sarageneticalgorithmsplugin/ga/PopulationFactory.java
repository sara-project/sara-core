package org.sara.sarageneticalgorithmsplugin.ga;

import org.sara.interfaces.ICore;
import org.sara.interfaces.abstractfactories.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.population.IPopulation;
import org.sara.sarageneticalgorithmsplugin.ga.models.Population;

public class PopulationFactory implements IPopulationFactory{
    
    public static PopulationFactory getInstance() {
        if(instance == null)
            instance = new PopulationFactory();
        
        return instance;
    }

    @Override
    public IPopulation makePopulation() {
        int popNumber = ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber();
        Population pop = new Population(popNumber);
        
        while(!pop.isFull()) {
            while (!pop.isFull())
                pop.addSpecimen(SpecimenFactory.makeSpecimen(1));
        }
        
        return pop;
    }
    
    private PopulationFactory() {}
    private static PopulationFactory instance;
}
