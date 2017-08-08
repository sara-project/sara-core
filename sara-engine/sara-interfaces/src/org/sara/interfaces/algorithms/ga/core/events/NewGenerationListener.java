package org.sara.interfaces.algorithms.ga.core.events;

import java.util.EventListener;

public interface NewGenerationListener extends EventListener{
    public void notifyNewGeneration(NewGenerationEvent event); 
}
