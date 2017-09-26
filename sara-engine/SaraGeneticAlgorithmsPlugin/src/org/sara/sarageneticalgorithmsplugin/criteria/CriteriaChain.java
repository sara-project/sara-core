package org.sara.sarageneticalgorithmsplugin.criteria;

import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import java.util.ArrayList;
import java.util.Iterator;
import org.sara.interfaces.algorithms.ga.fitness.criterias.ICriteria;

public class CriteriaChain {

    int count = 0;
    private ArrayList myFilters = new ArrayList();

    protected float processFilter(IChromosome chromosome) {
        count = 0;
        float fitness = 0;
        ICriteria filter;
        Iterator filters = myFilters.iterator();
        while (filters.hasNext()) {
            count++;
            filter = (ICriteria) filters.next();
            fitness += filter.execute(chromosome);
        }
        return fitness;
    }

    protected void addFilter(ICriteria filter) {
    }

    protected void removeFilter(int index) {
        this.myFilters.remove(index);
    }
}
