package org.sara.interfaces.algorithms.ga.core.events;

import java.util.EventObject;
import org.sara.interfaces.algorithms.ga.population.IPopulation;

public class NewGenerationEvent extends EventObject {

    public NewGenerationEvent(IGeneration source) {
        super(source);
    }

    public IPopulation getPopulation() {
        return ((IGeneration) this.source).getPopulation();
    }

    public int getGenNumber() {
        return ((IGeneration) this.source).getGenNumber();
    }
}
