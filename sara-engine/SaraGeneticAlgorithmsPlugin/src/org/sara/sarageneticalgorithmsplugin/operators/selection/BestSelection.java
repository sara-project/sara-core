package org.sara.sarageneticalgorithmsplugin.operators.selection;

import java.util.ArrayList;
import java.util.List;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.ISelection;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.sarageneticalgorithmsplugin.ga.model.Population;

public class BestSelection implements ISelection {

    @Override
    public IPopulation select(IPopulation population, double rate) {
        int popSize = population.size();
        int countSelections = (int) (popSize * rate);

        List<ISpecimen> offspring = new ArrayList();

        population.sortByFitness();

        for (int i = 0; i < countSelections; i++) {
            offspring.add(population.getSpecimen(i));
        }

        return new Population(popSize, offspring);
    }
}
