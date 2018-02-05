package org.sara.sarageneticalgorithmsplugin.operator.fitness;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.fitness.criterias.ICriteria;

public class CriteriaManager {

    float fitness;
    CriteriaChain filterChain = new CriteriaChain();

    public void addFilter(ICriteria filter) {
        filterChain.addFilter(filter);
    }

    public void removeFilter(int index) {
        filterChain.removeFilter(index);
    }

    public float processFilter(IChromosome chromosome) {
        fitness = 0;
        fitness = filterChain.processFilter(chromosome);

        return fitness;
    }
}
