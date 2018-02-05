package org.sara.sarageneticalgorithmsplugin.operator.crossover;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.ICrossover;

public class RandomCrossover extends ICrossover {

    public RandomCrossover(ICrossover[] crossoverModes) {
        this.randomCrossoverModes = crossoverModes;
    }

    @Override
    public void crossover(ISpecimen parentA, ISpecimen parentB) {
        this.randomCrossoverModes[(new Random().nextInt(this.randomCrossoverModes.length))].crossover(parentA, parentB);
    }

    private final ICrossover[] randomCrossoverModes;
}
