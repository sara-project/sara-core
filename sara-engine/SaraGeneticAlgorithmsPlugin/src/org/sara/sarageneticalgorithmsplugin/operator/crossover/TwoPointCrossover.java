package org.sara.sarageneticalgorithmsplugin.operator.crossover;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.ICrossover;

public class TwoPointCrossover extends ICrossover {

    @Override
    public void crossover(ISpecimen parentA, ISpecimen parentB) {
        int qntChromossomesParentA = parentA.getChromossomes().length;
        int pointA = (new Random()).nextInt(qntChromossomesParentA);
        int pointB = (new Random()).nextInt(qntChromossomesParentA - pointA) + pointA;

        for (int i = pointA; i < pointB; i++) {
            IChromosome aux = parentA.getChromossome(i);
            parentA.setChromosome(parentB.getChromossome(i), i);
            parentB.setChromosome(aux, i);
        }
    }
}
