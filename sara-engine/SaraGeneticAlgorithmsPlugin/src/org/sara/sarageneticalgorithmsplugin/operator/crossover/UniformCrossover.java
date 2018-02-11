package org.sara.sarageneticalgorithmsplugin.operator.crossover;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.ICrossover;

public class UniformCrossover extends ICrossover {

    @Override
    public void crossover(ISpecimen parentA, ISpecimen parentB) {
        int qntChromossomesParentA = parentA.getChromossomes(false).length;

        for (int i = 0; i < qntChromossomesParentA; i++) {

            if (new Random().nextInt(2) == 0) {
                IChromosome aux = parentA.getChromossome(i, false);
                parentA.setChromosome(parentB.getChromossome(i, false), i);
                parentB.setChromosome(aux, i);
            } else {
                IChromosome aux = parentB.getChromossome(i, false);
                parentB.setChromosome(parentA.getChromossome(i, false), i);
                parentA.setChromosome(aux, i);
            }
        }
    }
}
