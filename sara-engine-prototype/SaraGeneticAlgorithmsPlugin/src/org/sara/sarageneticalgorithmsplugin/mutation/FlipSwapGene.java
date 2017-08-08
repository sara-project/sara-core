package org.sara.sarageneticalgorithmsplugin.mutation;

import java.util.Random;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

/**
 *
 * @author Frederic Barboza
 */
public class FlipSwapGene extends AbstractMutation{
    
    private AbstractMutation[] mutations;
    
    /** Creates a new instance of FlipSwapGene */
    public FlipSwapGene() {
        mutations = new AbstractMutation[2];
        this.mutations[0] = new FlipGene();
        this.mutations[1] = new SwapGene();
    }
    
    protected void mutate(IChromosome chromosome) {
        Random random = new Random();
        this.mutations[random.nextInt(2)].mutate(chromosome);//random.nextInt(2)
    }    
    
}
