package org.sara.interfaces.algorithms.ga.core;

import org.sara.interfaces.algorithms.ga.chromosome.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.selection.ISelection;

public interface IGAManager {

    public IPopulationFactory getPopulationFactory();

    public IFitness getFitness();

    public ISelection getSelection();

    public ICrossover getCrossover();

    public IMutation getMutation();

    public ITerminate getTerminate();
}
