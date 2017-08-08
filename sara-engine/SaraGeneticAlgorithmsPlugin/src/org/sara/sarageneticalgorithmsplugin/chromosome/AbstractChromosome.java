package org.sara.sarageneticalgorithmsplugin.chromosome;

import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.genes.IGene;

public abstract class AbstractChromosome implements IChromosome {

    public AbstractChromosome(int size) {
        this.genes = new IGene[size];
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    @Override
    public void setFitness(float value) {
        this.fitnessValue = value;
    }
    
    @Override
    public float getFitness() {
        return this.fitnessValue;
    }

    @Override
    public int getSize() {
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
    // </editor-fold>
    
    @Override
    public void resetFitness() {
        this.fitnessValue = Float.NaN;
    }

    @Override
    public String toString() {
        String rep = "";
        for (int i = 0; i < this.getSize(); i++) {
            rep += this.getGene(i) + " ";
        }
        return rep.substring(0, rep.length() - 1);
    }

    @Override
    public Object clone() {
        AbstractChromosome newChromosome = this.getNewInstance();
        for (int i = 0; i < newChromosome.getSize(); i++) {
            newChromosome.setGene((IGene) (this.getGene(i).clone()), i);
        }
        newChromosome.setFitness(this.getFitness());
        return newChromosome;
    }

    public abstract AbstractChromosome getNewInstance();

    protected float fitnessValue;
    protected IGene[] genes;
}
