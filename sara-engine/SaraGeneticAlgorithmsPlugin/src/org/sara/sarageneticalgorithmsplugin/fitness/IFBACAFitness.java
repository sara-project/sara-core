package org.sara.sarageneticalgorithmsplugin.fitness;

import java.util.*;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.sarageneticalgorithmsplugin.criteria.CriteriaManager;

public class IFBACAFitness extends AbstractFitnessFunction{
    CriteriaManager filter;

    
    public IFBACAFitness(ArrayList disciplinas, TreeMap professores){
    }
    
   public void addFilters(){

   }

    @Override
    protected float getFitnessValue(IChromosome chromosome) {
        return filter.processFilter(chromosome);
    }
}    
   