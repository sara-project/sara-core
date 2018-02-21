package org.sara.interfaces.algorithms.ga.model;

import java.util.List;

public interface ISpecimen extends Cloneable {

    public boolean isBetterThan(ISpecimen other);
    public float getFitness();
    public IChromosome getRandomChromosome(boolean clone);
    public IChromosome[] getChromossomes(boolean clone);
    public IChromosome getChromossome(int index, boolean clone);
    public Object clone();
    public void setChromosome(IChromosome chromosome, int index);
    public List<IGene> getAllGenes(boolean clone);
}
