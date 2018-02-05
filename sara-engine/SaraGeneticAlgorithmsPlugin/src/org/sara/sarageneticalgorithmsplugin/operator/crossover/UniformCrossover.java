package org.sara.sarageneticalgorithmsplugin.operator.crossover;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.ICrossover;

public class UniformCrossover extends ICrossover {

    @Override
    public void crossover(ISpecimen parentA, ISpecimen parentB) {
        int qntChromossomesParentA = parentA.getChromossomes().length;
        int[] mask = new int[qntChromossomesParentA];

        for (int i = 0; i < mask.length; i++) {
            mask[i] = new Random().nextInt(2);

            if (mask[i] == 0) {
                IChromosome aux = parentA.getChromossome(i);
                parentA.setChromosome(parentB.getChromossome(i), i);
                parentB.setChromosome(aux, i);
            } else {
                IChromosome aux = parentB.getChromossome(i);
                parentB.setChromosome(parentA.getChromossome(i), i);
                parentA.setChromosome(aux, i);
            }
        }
    }
}
