package org.sara.sarageneticalgorithmsplugin.operator.crossover;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.ICrossover;

public class RandomCrossover extends ICrossover {

    public RandomCrossover(ICrossover[] crossoverModes) {
        this.randomCrossoverModes = crossoverModes;
        this.currentMode = crossoverModes[(new Random().nextInt(this.randomCrossoverModes.length))];
    }
    
    public void changeMode() {
        this.currentMode = this.randomCrossoverModes[(new Random().nextInt(this.randomCrossoverModes.length))];
    }

    @Override
    public void crossover(ISpecimen parentA, ISpecimen parentB) {
        this.currentMode.crossover(parentA, parentB);
    }
    
    private ICrossover currentMode;
    private final ICrossover[] randomCrossoverModes;
}
