package org.sara.sarageneticalgorithmsplugin.mutation;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public class FlipSwapGene extends AbstractMutation {

    public FlipSwapGene() {
        mutations = new AbstractMutation[2];
        this.mutations[0] = new FlipGene();
        this.mutations[1] = new SwapGene();
    }

    @Override
    protected void mutate(IChromosome chromosome) {
        Random random = new Random();
        this.mutations[random.nextInt(2)].mutate(chromosome);
    }

    private AbstractMutation[] mutations;
}
