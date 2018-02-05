package org.sara.sarageneticalgorithmsplugin.operator.mutation;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.IMutation;

public class RandomMutation extends IMutation {

    public RandomMutation(IMutation[] mutationModes) {
        this.mutationModes = mutationModes;
    }

    @Override
    public void mutate(IChromosome chromosome) {
        this.mutationModes[new Random().nextInt(this.mutationModes.length)].mutate(chromosome);
    }

    private final IMutation[] mutationModes;
}
