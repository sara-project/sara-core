package org.sara.sarageneticalgorithmsplugin.operators.galightswitch;

import org.sara.interfaces.algorithms.ga.core.IGeneration;
import org.sara.interfaces.algorithms.ga.galightswitch.IGALightSwitch;

public class GALightSwitch  implements IGALightSwitch{
    
    public GALightSwitch(int maxGenerationNumber) {
        this.maxGenerationNumber = maxGenerationNumber;
    }

    @Override
    public boolean stop(IGeneration generation) {
        return generation.getNumber() == maxGenerationNumber;
    }
    
    private final int maxGenerationNumber;
}
