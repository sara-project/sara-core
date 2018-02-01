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
import org.sara.sarageneticalgorithmsplugin.crossover.TwoPointCrossover;
import org.sara.sarageneticalgorithmsplugin.fitness.IFBACAFitness;
import org.sara.sarageneticalgorithmsplugin.ga.models.Generation;
import org.sara.sarageneticalgorithmsplugin.mutation.SwapGene;
import org.sara.sarageneticalgorithmsplugin.operators.galightswitch.BestSelection;
import org.sara.sarageneticalgorithmsplugin.operators.galightswitch.GALightSwitch;

public class EvolutionaryCycle implements IGAEngine{
    
    public EvolutionaryCycle() {
       System.out.println("Engine EvolutionaryCycle was created.");
    }
    
    public void startCycle() { 
        IPopulation population;
        this.gaConfiguration = ICore.getInstance().getModelController().getGaConfiguration();
        
        //Assigns the genetic operators that will be used
        this.gaConfiguration.setGaLightSwitch(new GALightSwitch(gaConfiguration.getMaxGeneration()));
        this.gaConfiguration.setSelection(new BestSelection()); //needs review
        this.gaConfiguration.setCrossover(new TwoPointCrossover()); //needs review
        this.gaConfiguration.setMutation(new SwapGene()); //needs review
        this.gaConfiguration.setFitness(new IFBACAFitness()); //needs review
        System.out.println("WARRNIG: Operators need to be reviewed... (SaraGeneticAlgorithmsPlugin)");

        //Get the current operators
        IFitness fitness = this.gaConfiguration.getFitness();
        ISelection selection = this.gaConfiguration.getSelection();
        ICrossover crossover = this.gaConfiguration.getCrossover();
        IMutation mutation = this.gaConfiguration.getMutation();
        IGALightSwitch terminate = this.gaConfiguration.getGaLightSwitch();
        IPopulationFactory populationFactory = PopulationFactory.getInstance();
        
        System.out.println("The evolutionary cyle was started.");
        int genNumber = 1;
        population = populationFactory.makePopulation();
        do {
            fitness.evaluate(population);
            //Código abaixo simula a geração dos descendentes
            population = populationFactory.makePopulation();
        
        } while (!terminate.stop(new Generation(genNumber++, population)));
        System.out.println();
    }
    
    private GAConfiguration gaConfiguration;

    @Override
    public void startGA() {
        this.startCycle();
    }
}
