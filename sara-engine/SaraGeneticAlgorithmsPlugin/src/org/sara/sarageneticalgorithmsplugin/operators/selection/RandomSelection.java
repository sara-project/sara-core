package org.sara.sarageneticalgorithmsplugin.operators.selection;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.operator.ISelection;
import org.sara.interfaces.algorithms.ga.model.IPopulation;

public class RandomSelection implements ISelection {

    public RandomSelection(ISelection[] selectionModes) {
        this.randomSelctionModes = selectionModes;
    }

    @Override
    public void select(IPopulation population, double rate) {
        this.randomSelctionModes[(new Random().nextInt(this.randomSelctionModes.length))].select(population, rate);
    }

    private final ISelection[] randomSelctionModes;
}
