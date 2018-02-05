package org.sara.sarageneticalgorithmsplugin.operators.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.ISelection;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.sarageneticalgorithmsplugin.ga.model.Population;

public class RankingSelection implements ISelection {

    @Override
    public IPopulation select(IPopulation population, double rate) {
        int popSize = population.size();
        int countSelections = (int) (popSize * rate);

        List<ISpecimen> offspring = new ArrayList();
        IPopulation parentPop = (IPopulation) population.clone();
        List<ISpecimen> discartedParents = new ArrayList();

        Collections.shuffle(parentPop.getAllSpecimens());

        while (offspring.size() < (popSize / 2)) {
            ISpecimen s1 = parentPop.getFirstSpecimen();
            ISpecimen s2 = parentPop.getLastSpecimen();

            if (s1.getFitness() > s2.getFitness()) {
                offspring.add(s1);
                discartedParents.add(s2);
            } else if (s1.getFitness() < s2.getFitness()) {
                offspring.add(s2);
                discartedParents.add(s1);
            } else {
                offspring.add(new Random().nextInt(1) == 1 ? s1 : s2);
                discartedParents.add(s1);
                discartedParents.add(s2);
            }

            parentPop.removeSpecimen(s1);
            parentPop.removeSpecimen(s2);
        }

        while (offspring.size() < countSelections) {
            offspring.add(discartedParents.get(new Random().nextInt(discartedParents.size())));
        }

        return new Population(popSize, offspring);
    }
}
