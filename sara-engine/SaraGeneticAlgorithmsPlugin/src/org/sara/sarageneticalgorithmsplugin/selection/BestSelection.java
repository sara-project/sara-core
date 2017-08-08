package org.sara.sarageneticalgorithmsplugin.selection;

import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.sarageneticalgorithmsplugin.defaultoptions.chromosome.DefaultPopulation;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public class BestSelection implements ISelection {

    @Override
    public IPopulation select(IPopulation population, float rate) {
        int countSelections = (int) (population.size() * rate);
        IPopulation newPopulation = new DefaultPopulation();
        population.sortByFitness();
        for (int i = 0; i < countSelections; i++) {
            newPopulation.addChromosome(population.getChromosome(i));
        }
        return newPopulation;
    }
}
