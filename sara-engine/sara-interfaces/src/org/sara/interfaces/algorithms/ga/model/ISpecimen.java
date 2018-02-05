package org.sara.interfaces.algorithms.ga.model;

import java.util.List;

public interface ISpecimen extends Cloneable {

    public float getFitness();

    public IChromosome getRandomChromosome();

    public IChromosome[] getChromossomes();

    public IChromosome getChromossome(int index);

    public Object clone();

    public void setChromosome(IChromosome chromosome, int index);

    public List<IGene> getAllGenes();
}
