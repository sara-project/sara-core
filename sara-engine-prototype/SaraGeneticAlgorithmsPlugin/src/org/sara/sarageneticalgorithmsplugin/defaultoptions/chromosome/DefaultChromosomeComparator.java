package org.sara.sarageneticalgorithmsplugin.defaultoptions.chromosome;

import java.util.Comparator;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public class DefaultChromosomeComparator implements Comparator{
    
    @Override
    public int compare(Object o1, Object o2) {
        return (int) (((IChromosome) o2).getFitness() - ((IChromosome) o1).getFitness());
    }
}
