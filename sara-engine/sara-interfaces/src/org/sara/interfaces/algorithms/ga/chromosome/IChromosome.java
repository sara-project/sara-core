package org.sara.interfaces.algorithms.ga.chromosome;

import org.sara.interfaces.algorithms.ga.genes.IGene;

public interface IChromosome extends Cloneable {

    public void setFitness(float value);

    public float getFitness();

    public void resetFitness();

    public int getSize();

    public IGene getGene(int index);

    public IGene[] getGenes();

    public void setGene(IGene gene, int index);

    public void setValidation(boolean valor);

    public boolean getValidation();

    public IChromosome getRandom();

    public Object clone();
}
