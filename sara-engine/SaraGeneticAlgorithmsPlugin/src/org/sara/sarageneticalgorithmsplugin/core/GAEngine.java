package org.sara.sarageneticalgorithmsplugin.core;

import org.sara.interfaces.algorithms.ga.core.IGAManager;
import org.sara.interfaces.algorithms.ga.core.IGAParameters;
import javax.swing.event.EventListenerList;
import org.sara.sarageneticalgorithmsplugin.events.Generation;
import org.sara.sarageneticalgorithmsplugin.events.NewGenerationEvent;
import org.sara.sarageneticalgorithmsplugin.events.NewGenerationListener;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;
import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.galightswitch.IGALightSwitch;

public class GAEngine {

    public GAEngine(IGAManager gaConfiguratorIF, IGAParameters gaParameterIF) {
        this.listenerList = new EventListenerList();
        this.gaConfiguratorIF = gaConfiguratorIF;
        this.gaParameterIF = gaParameterIF;
    }

    public void run() {
        int genNumber = 1;
        IFitness fitness = gaConfiguratorIF.getFitness();
        ISelection selection = gaConfiguratorIF.getSelection();
        ICrossover crossover = gaConfiguratorIF.getCrossover();
        IMutation mutation = gaConfiguratorIF.getMutation();
        IGALightSwitch terminate = gaConfiguratorIF.getGALightSwitch();
        pop = gaConfiguratorIF.getPopulationFactory().makePopulation(gaParameterIF.getPopulationSize());
        fitness.evaluate(pop);
        this.fireNewGenerationEvent(new NewGenerationEvent(new Generation(genNumber, pop)));
        do {
            IPopulation selected = selection.select(pop, gaParameterIF.getSelectionPercent());
            pop = crossover.makeOffspring(pop, gaParameterIF.getPopulationSize());
            mutation.mutate(pop, gaParameterIF.getMutationPercent());
            fitness.evaluate(pop);
            genNumber++;
            this.fireNewGenerationEvent(new NewGenerationEvent(new Generation(genNumber, pop)));
            System.gc();
            Thread.yield();
        } while (!terminate.stop(new Generation(genNumber, pop)));
    }

    public IPopulation getPopulation() {
        return this.pop;
    }

    public IChromosome getBestSolution() {
        this.pop.sortByFitness();
        return this.pop.getChromosome(0);
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
    private IGAManager gaConfiguratorIF;
    private IGAParameters gaParameterIF;
    private IPopulation pop;
}
