
package org.sara.sarageneticalgorithmsplugin.mutation;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public class FlipGene extends AbstractMutation{
    
    protected void mutate(IChromosome chromosome) {
        Random random = new Random();
        int loci = random.nextInt(chromosome.size());
        chromosome.setGene(chromosome.getGene(loci).getRandom(),loci);
    }

    
}
