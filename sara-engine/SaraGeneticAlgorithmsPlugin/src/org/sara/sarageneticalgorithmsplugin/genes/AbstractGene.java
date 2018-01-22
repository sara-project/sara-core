package org.sara.sarageneticalgorithmsplugin.genes;

import org.sara.interfaces.algorithms.ga.genes.IGene;

public abstract class AbstractGene {
    
    @Override
    public abstract Object clone();

    @Override
    public String toString() {
        return this.getAllele().toString();
    }
 
    public abstract IGene getRandom();

    public AbstractGene(Object allele) {
        this.setAllele(allele);
    }

    public void setAllele(Object value) {
        this.allele = value;
    }

    public Object getAllele() {
        return this.allele;
    }

    private Object allele;
}
