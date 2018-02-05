package org.sara.interfaces.algorithms.ga.model;

import java.util.List;

public interface IChromosome extends Cloneable {

    public void setFitness(float value);

    public float getFitness();

    public void resetFitness();

    public int size();

    public IGene getGene(int index);

    public List<IGene> getGenes();

    public int groupSize();

    public List<IGene> getGenesByType(int type);

    public List<IGene> getGenesRandomByType();

    public void setGene(IGene gene, int index);

    public Object clone();
}
