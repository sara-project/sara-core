package org.sara.sarageneticalgorithmsplugin.mutation;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public class FlipGene extends AbstractMutation {

    @Override
    protected void mutate(IChromosome chromosome) {
        Random random = new Random();
        int loci = random.nextInt(chromosome.getSize());
        chromosome.setGene(chromosome.getGene(loci).getRandom(), loci);
    }
}
