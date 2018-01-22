package org.sara.sarageneticalgorithmsplugin.selection;

import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.sarageneticalgorithmsplugin.defaultoptions.chromosome.IFBAPopulation;
import org.sara.interfaces.algorithms.ga.population.IPopulation;

public class BestSelection implements ISelection {

    @Override
    public IPopulation select(IPopulation population, double rate) {
        int countSelections = (int) (population.getSize() * rate);
        
        IPopulation newPopulation = new IFBAPopulation();
        
        population.sortByFitness();
        
        for (int i = 0; i < countSelections; i++) {
            newPopulation.addChromosome(population.getChromosome(i));
        }
        return newPopulation;
    }
}
