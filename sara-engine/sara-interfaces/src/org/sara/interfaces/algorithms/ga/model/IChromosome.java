package org.sara.interfaces.algorithms.ga.model;

import java.util.List;

public interface IChromosome extends Cloneable {

    public void setFitness(float value);
    public float getFitness();
    public void resetFitness();
    public int size();
    public IGene getGene(int index, boolean clone);
    public List<IGene> getGenes(boolean clone);
    public int groupSize();
    public List<IGene> getGenesByType(int type, boolean clone);
    public List<IGene> getGenesRandomByType(boolean clone);
    public void setGene(IGene gene, int index);
    public Object clone();
}
