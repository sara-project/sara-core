package org.sara.sarageneticalgorithmsplugin.operators.selection;

import org.sara.interfaces.algorithms.ga.operator.ISelection;
import org.sara.interfaces.algorithms.ga.model.IPopulation;

public class BestSelection implements ISelection {

    @Override
    public void select(IPopulation population, double rate) {
        int popSize = population.size();
        int countSelections = (int) (popSize * rate);
        
        population.sortByFitness();
        population.removeLastSpecimen(population.size() - countSelections);
    }
}
