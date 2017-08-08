package org.sara.sarageneticalgorithmsplugin.events;


import java.util.EventObject;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public class NewGenerationEvent extends EventObject{
    
    /** Creates a new instance of NewGenerationEvent */
    public NewGenerationEvent(Generation source) {
        super(source);
    }
    
    public IPopulation getPopulation(){
        return ((Generation) this.source).getPopulation();
    }
    
    public int getGenNumber(){
        return ((Generation) this.source).getGenNumber();
    }    
    
}
