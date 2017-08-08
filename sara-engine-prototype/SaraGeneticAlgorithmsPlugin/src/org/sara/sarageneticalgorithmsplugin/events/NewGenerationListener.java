package org.sara.sarageneticalgorithmsplugin.events;

import java.util.EventListener;

public interface NewGenerationListener extends EventListener{

    public void notifyNewGeneration(NewGenerationEvent event);
}
