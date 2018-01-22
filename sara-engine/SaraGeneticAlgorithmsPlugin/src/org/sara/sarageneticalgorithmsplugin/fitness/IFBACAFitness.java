package org.sara.sarageneticalgorithmsplugin.fitness;

import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.sarageneticalgorithmsplugin.criteria.CriteriaManager;

public class IFBACAFitness extends AbstractFitnessFunction{

    public IFBACAFitness(){
    }

    @Override
    protected float getFitnessValue(IChromosome chromosome) {
        return filter.processFilter(chromosome);
    }
    
    private CriteriaManager filter;
}    
   