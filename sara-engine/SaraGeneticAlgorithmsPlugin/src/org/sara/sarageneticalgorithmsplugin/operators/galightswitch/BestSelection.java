package org.sara.sarageneticalgorithmsplugin.operators.galightswitch;

import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.interfaces.algorithms.ga.population.IPopulation;
import org.sara.sarageneticalgorithmsplugin.ga.models.Population;

public class BestSelection implements ISelection {

    @Override
    public IPopulation select(IPopulation population, double rate) {
        int countSelections = (int) (population.getSize() * rate);
        
        IPopulation newPopulation = new Population(countSelections);
        
        population.sortByFitness();
        
        for (int i = 0; i < countSelections; i++) {
            newPopulation.addChromosome(population.getChromosome(i));
        }
        return newPopulation;
    }
}
