package org.sara.sarageneticalgorithmsplugin.operators.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.ISelection;
import org.sara.interfaces.algorithms.ga.model.IPopulation;

public class TournamentSelection implements ISelection {

    @Override
    public void select(IPopulation population, double rate) {
        int popSize = population.size();
        int countSelections = (int) (popSize * rate);
        
        List<ISpecimen> offspring = new ArrayList();
        List<ISpecimen> discartedParents = new ArrayList();
        List<ISpecimen> parentPop = population.getAllSpecimens(false);

        Collections.shuffle(parentPop);

        while (offspring.size() < (popSize / 2)) {
            if(parentPop.isEmpty())
                break;
            
            ISpecimen s1 = parentPop.get(0);
            ISpecimen s2 = parentPop.get(parentPop.size() - 1);

            if (s1.getFitness() > s2.getFitness()) {
                offspring.add(s1);
                discartedParents.add(s2);
            } else if (s1.getFitness() < s2.getFitness()) {
                offspring.add(s2);
                discartedParents.add(s1);
            } else {
                discartedParents.add(s1);
                discartedParents.add(s2);
            }

            parentPop.remove(s1);
            parentPop.remove(s2);
        }

        while (offspring.size() < countSelections) {
            offspring.add(discartedParents.get(new Random().nextInt(discartedParents.size())));
        }
        
        population.clearSpecimens();
        population.addSpecimens(offspring, false);
    }
}
