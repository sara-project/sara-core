
package org.sara.sarageneticalgorithmsplugin.crossover;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.genes.IGene;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public class TwoPointCrossover extends AbstractCrossover{
    
    /** Creates a new instance of TwoPointCrossover */
    public TwoPointCrossover() {
    }
    
    protected void crossover(IChromosome parentA, IChromosome parentB){
        int pointA = (new Random()).nextInt(parentA.size());
        int pointB = (new Random()).nextInt(parentA.size() - pointA) + pointA;
        for(int i = pointA; i < pointB; i++){
            IGene aux = parentA.getGene(i);
            parentA.setGene(parentB.getGene(i),i);
            parentB.setGene(aux,i);
        }
    }
    
    
}
