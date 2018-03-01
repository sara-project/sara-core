package org.sara.interfaces.algorithms.ga.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;

public abstract class ICriteria {

    abstract public float execute(IChromosome chromosome);
    
    public final boolean isRequired() {
        return this.required;
    }
    
    public ICriteria(boolean required) {
        this.required = required;
    }
    
    protected final boolean required;
}
