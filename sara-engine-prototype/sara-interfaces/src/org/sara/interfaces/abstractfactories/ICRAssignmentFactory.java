package org.sara.interfaces.abstractfactories;

import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public abstract class ICRAssignmentFactory {
    
    public static ICRAssignmentFactory getInstance()
    {
        return instance;
    }
    public abstract  IChromosome CreateChromosome();
    public abstract  IPopulation CreatePopulation();

    // public IEditorAbstractFactory CreateEditorAbstractFactory();
     
    protected ICRAssignmentFactory() {}
    protected static ICRAssignmentFactory instance = null;
}
