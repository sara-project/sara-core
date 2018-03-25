package org.sara.sarageneticalgorithmsplugin.operator.crossover;

import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.ICrossover;

public class GreatestCrossover extends ICrossover {

    @Override
    public void crossover(ISpecimen parentA, ISpecimen parentB) {
        int size = parentA.getChromossomes(false).length;

        for (int i = 0; i < size; i++) {
            IChromosome a = parentA.getChromossome(i, false);
            IChromosome b = parentB.getChromossome(i, false);

            if (Float.compare(a.getFitness(), b.getFitness()) < 0) {
                parentA.setChromosome(b, i);
                parentB.setChromosome(a, i);
            }
        }
    }
}
