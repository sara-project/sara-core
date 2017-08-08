package org.sara.sarageneticalgorithmsplugin.chromosome;

import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.genes.IGene;

public abstract class AbstractChromosome implements IChromosome {

    protected float fitnessValue;
    protected IGene[] genes;

    public AbstractChromosome(int size) {
        this.genes = new IGene[size];
    }

    @Override
    public void setFitnessValue(float fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    @Override
    public float getFitness() {
        return this.fitnessValue;
    }

    @Override
    public void resetFitnessValue() {
        this.fitnessValue = Float.NaN;
    }

    @Override
    public int size() {
        return this.genes.length;
    }

    @Override
    public IGene getGene(int index) {
        return this.genes[index];
    }

    @Override
    public IGene[] getGenes() {
        return this.genes;
    }

    @Override
    public void setGene(IGene gene, int index) {
        this.genes[index] = gene;
    }

    @Override
    public String toString() {
        String rep = "";
        for (int i = 0; i < this.size(); i++) {
            rep += this.getGene(i) + " ";
        }
        return rep.substring(0, rep.length() - 1);
    }

    @Override
    public Object clone() {
        AbstractChromosome newChromosome = this.getNewInstance();
        for (int i = 0; i < newChromosome.size(); i++) {
            newChromosome.setGene((IGene) (this.getGene(i).clone()), i);
        }
        newChromosome.setFitnessValue(this.getFitness());
        return newChromosome;
    }

    public abstract AbstractChromosome getNewInstance();
}
