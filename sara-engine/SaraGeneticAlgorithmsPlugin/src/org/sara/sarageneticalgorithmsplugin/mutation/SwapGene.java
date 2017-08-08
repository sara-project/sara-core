package org.sara.sarageneticalgorithmsplugin.mutation;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.genes.IGene;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public class SwapGene extends AbstractMutation {

    @Override
    protected void mutate(IChromosome chromosome) {
        Random random = new Random();
        int lociA = random.nextInt(chromosome.getSize());
        int lociB = random.nextInt(chromosome.getSize());
        IGene aux = chromosome.getGene(lociA);
        chromosome.setGene(chromosome.getGene(lociB), lociA);
        chromosome.setGene(aux, lociB);
    }
}
