package org.sara.sarageneticalgorithmsplugin.operator.mutation;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.IMutation;

public class RandomMutation extends IMutation {

    public RandomMutation(IMutation[] mutationModes) {
        this.mutationModes = mutationModes;
    }

    @Override
    public IChromosome mutateChromosome(IChromosome chromosome) {
        return this.mutationModes[new Random().nextInt(this.mutationModes.length)].mutateChromosome(chromosome);
    }

    private final IMutation[] mutationModes;
}
