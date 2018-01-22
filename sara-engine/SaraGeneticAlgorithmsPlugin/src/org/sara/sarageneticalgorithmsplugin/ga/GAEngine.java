package org.sara.sarageneticalgorithmsplugin.ga;

import javax.swing.event.EventListenerList;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.IGAEngine;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.population.IPopulation;
import org.sara.interfaces.abstractfactories.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.sarageneticalgorithmsplugin.events.Generation;
import org.sara.sarageneticalgorithmsplugin.events.NewGenerationEvent;
import org.sara.sarageneticalgorithmsplugin.events.NewGenerationListener;
import org.sara.interfaces.algorithms.ga.galightswitch.IGALightSwitch;
import org.sara.interfaces.model.GAConfiguration;
import org.sara.sarageneticalgorithmsplugin.chromosome.IFBAChromosome;
import org.sara.sarageneticalgorithmsplugin.core.PopulationFactory;
import org.sara.sarageneticalgorithmsplugin.crossover.TwoPointCrossover;
import org.sara.sarageneticalgorithmsplugin.fitness.IFBACAFitness;
import org.sara.sarageneticalgorithmsplugin.galightswitch.IFBAGALightSwitch;
import org.sara.sarageneticalgorithmsplugin.mutation.SwapGene;
import org.sara.sarageneticalgorithmsplugin.selection.BestSelection;

public class GAEngine implements IGAEngine{

    public GAEngine() {
       this.gaConfiguration = ICore.getInstance().getModelController().getGaConfiguration();
       //Assigns the genetic operators that will be used
       this.gaConfiguration.setGaLightSwitch(new IFBAGALightSwitch(this.gaConfiguration.getMaxGeneration()));
       this.gaConfiguration.setSelection(new BestSelection()); //needs review
       this.gaConfiguration.setCrossover(new TwoPointCrossover()); //needs review
       this.gaConfiguration.setMutation(new SwapGene()); //needs review
       this.gaConfiguration.setFitness(new IFBACAFitness()); //needs review
       
       this.listenerList = new EventListenerList();
    }
    
    @Override
    public void startGA() {
        int genNumber = 1;
        
        IPopulationFactory populationFactory = new PopulationFactory();
        IFitness fitness = this.gaConfiguration.getFitness();
        ISelection selection = this.gaConfiguration.getSelection();
        ICrossover crossover = this.gaConfiguration.getCrossover();
        IMutation mutation = this.gaConfiguration.getMutation();
        IGALightSwitch terminate = this.gaConfiguration.getGaLightSwitch();
        
        population = populationFactory.makePopulation(this.gaConfiguration.getPopulationNumber());
        fitness.evaluate(population);
        this.fireNewGenerationEvent(new NewGenerationEvent(new Generation(genNumber, population)));
        
        do {
            IPopulation selected = selection.select(population, this.gaConfiguration.getSelectProbability());
            population = crossover.makeOffspring(population, this.gaConfiguration.getPopulationNumber());
            mutation.mutate(population, this.gaConfiguration.getMutationProbability());
            fitness.evaluate(population);
            genNumber++;
            this.fireNewGenerationEvent(new NewGenerationEvent(new Generation(genNumber, population)));
            System.gc();
            Thread.yield();
        } while (!terminate.stop(new Generation(genNumber, population)));
        
        this.population.sortByFitness();
        population.getAllChromosomes();
    }

    public IPopulation getPopulation() {
        return this.population;
    }

    public IChromosome getBestSolution() {
        this.population.sortByFitness();
        return this.population.getChromosome(0);
    }

    public void addNewGenerationListener(NewGenerationListener listener) {
        listenerList.add(NewGenerationListener.class, listener);
    }

    public void removeNewGenerationListener(NewGenerationListener listener) {
        listenerList.remove(NewGenerationListener.class, listener);
    }

    public void fireNewGenerationEvent(NewGenerationEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == NewGenerationListener.class) {
                ((NewGenerationListener) listeners[i + 1]).notifyNewGeneration(evt);
            }
        }
    }
    
    protected EventListenerList listenerList;
    private final GAConfiguration gaConfiguration;
    private IPopulation population;
}
