package org.sara.sarageneticalgorithmsplugin;

import javax.swing.event.EventListenerList;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;
import org.sara.interfaces.algorithms.ga.core.IGAManager;
import org.sara.interfaces.algorithms.ga.core.IGAParameters;
import org.sara.interfaces.algorithms.ga.core.ITerminate;
import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.sarageneticalgorithmsplugin.events.Generation;
import org.sara.sarageneticalgorithmsplugin.events.NewGenerationEvent;
import org.sara.sarageneticalgorithmsplugin.events.NewGenerationListener;

public class GAEngine {

    protected EventListenerList listenerList = new EventListenerList();

    public GAEngine(IGAManager gaManager, IGAParameters gaParameters) {
        this.gaManager = gaManager;
        this.gaParameters = gaParameters;
    }

    public void run() {
        int genNumber = 1;
        IFitness fitness = gaManager.getFitness();
        ISelection selection = gaManager.getSelection();
        ICrossover crossover = gaManager.getCrossover();
        IMutation mutation = gaManager.getMutation();
        ITerminate terminate = gaManager.getTerminate();
        population = gaManager.getPopulationFactory().makePopulation(gaParameters.getPopulationSize());
        fitness.evaluate(population);
        this.fireNewGenerationEvent(new NewGenerationEvent(new Generation(genNumber, population)));
        do {
            IPopulation selected = selection.select(population, gaParameters.getSelectionPercent());
            population = crossover.makeOffspring(population, gaParameters.getPopulationSize());
            mutation.mutate(population, gaParameters.getMutationPercent());
            fitness.evaluate(population);
            genNumber++;
            this.fireNewGenerationEvent(new NewGenerationEvent(new Generation(genNumber, population)));
            System.gc();
            Thread.yield();
        } while (!terminate.stop(new Generation(genNumber, population)));

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

    private final IGAManager gaManager;
    private final IGAParameters gaParameters;
    private IPopulation population;
}
