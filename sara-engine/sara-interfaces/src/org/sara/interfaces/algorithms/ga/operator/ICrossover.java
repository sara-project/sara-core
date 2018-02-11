package org.sara.interfaces.algorithms.ga.operator;

import java.util.Collections;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IPopulation;

public abstract class ICrossover implements Cloneable {

    public final void makeOffspring(IPopulation parents) {
        int popSize = ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber();

        Collections.shuffle(parents.getAllSpecimens(false));

        while (parents.size() < popSize) {
            ISpecimen parentA = parents.getRandomSpecimen(true);
            ISpecimen parentB = parents.getRandomSpecimen(true);

            this.crossover(parentA, parentB);

            parents.addSpecimen(parentA, false);
            parents.addSpecimen(parentB, false);
        }
    }

    public abstract void crossover(ISpecimen parentA, ISpecimen parentB);
}
