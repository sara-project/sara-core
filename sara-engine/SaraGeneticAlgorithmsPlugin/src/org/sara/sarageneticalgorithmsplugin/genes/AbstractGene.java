package org.sara.sarageneticalgorithmsplugin.genes;

import org.sara.interfaces.algorithms.ga.genes.IGene;

public abstract class AbstractGene {

    public abstract IGene getRandom();

    @Override
    public abstract Object clone();

    public AbstractGene(Object allele) {
        this.setAllele(allele);
    }

    public void setAllele(Object value) {
        this.allele = value;
    }

    public Object getAllele() {
        return this.allele;
    }

    @Override
    public String toString() {
        return this.getAllele().toString();
    }
    private Object allele;
}
