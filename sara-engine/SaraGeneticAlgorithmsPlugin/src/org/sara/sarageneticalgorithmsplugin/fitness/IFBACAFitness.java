package org.sara.sarageneticalgorithmsplugin.fitness;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.ISpecimen;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.population.IPopulation;
import org.sara.sarageneticalgorithmsplugin.criteria.CriteriaManager;

public class IFBACAFitness implements IFitness{
    
    public IFBACAFitness(){ 
        this.filter = new CriteriaManager();
        System.out.println("OS FILTROS PRECISAM SER ADICIONADOS!");
    }
    
    @Override
    public void evaluate(IPopulation population) {
        for(ISpecimen specimen : population.getAllChromosomes())
            this.evaluate(specimen);
    }
    
    protected float calculateFitness(ISpecimen specimens) {
       return new Random().nextFloat(); //filter.processFilter(chromosome);
    }
    
    private void evaluate(ISpecimen specimen) {
        specimen.setFitness(this.calculateFitness(specimen));
    }
    private final CriteriaManager filter;
}    
   