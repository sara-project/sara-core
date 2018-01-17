package org.sara.interfaces.algorithms.ga.core;

import org.sara.interfaces.abstractfactories.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.interfaces.algorithms.ga.galightswitch.IGALightSwitch;

public interface IGAManager {

    public IPopulationFactory getPopulationFactory();

    public IFitness getFitness();

    public ISelection getSelection();

    public ICrossover getCrossover();

    public IMutation getMutation();

    public IGALightSwitch getGALightSwitch();
}
