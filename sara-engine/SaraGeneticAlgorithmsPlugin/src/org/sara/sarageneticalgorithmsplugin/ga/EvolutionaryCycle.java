package org.sara.sarageneticalgorithmsplugin.ga;

import org.sara.interfaces.ICore;
import org.sara.interfaces.abstractfactories.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.IGAEngine;
import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.galightswitch.IGALightSwitch;
import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.population.IPopulation;
import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.interfaces.model.GAConfiguration;
import org.sara.sarageneticalgorithmsplugin.ga.models.Generation;

public class EvolutionaryCycle implements IGAEngine{
    
    public EvolutionaryCycle() {
       this.gaConfiguration = ICore.getInstance().getModelController().getGaConfiguration();
       System.out.println("Engine EvolutionaryCycle was created.");
    }
    
    public void startCycle() {
        System.out.println("The evolutionary cyle was started.");
        int genNumber = 0;
        
        IPopulation population;

        //Get the current operators
        IFitness fitness = this.gaConfiguration.getFitness();
        ISelection selection = this.gaConfiguration.getSelection();
        ICrossover crossover = this.gaConfiguration.getCrossover();
        IMutation mutation = this.gaConfiguration.getMutation();
        IGALightSwitch terminate = this.gaConfiguration.getGaLightSwitch();
        IPopulationFactory populationFactory = PopulationFactory.getInstance();
       
        do {
            population = populationFactory.makePopulation();
        
        } while (!terminate.stop(new Generation(++genNumber, population)));
    }
    
    private final GAConfiguration gaConfiguration;

    @Override
    public void startGA() {
        this.startCycle();
    }
}
