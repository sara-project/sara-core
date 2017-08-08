package org.sara.sarageneticalgorithmsplugin.genes;

import org.sara.interfaces.algorithms.ga.genes.IGene;

public abstract class AbstractGene {
    
    private Object allele;
    
    /** Creates a new instance of CharGene */
    public AbstractGene(Object allele) {
        this.setAllele(allele);
    }
    
    public void setAllele(Object value) {
        this.allele = value;
    }

    public Object getAllele() {
        return this.allele;
    }

    public abstract IGene getRandom();
    
    public abstract Object clone();
    
    public String toString(){
        return this.getAllele().toString();
    }     
}
