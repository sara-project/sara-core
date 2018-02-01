package org.sara.sarageneticalgorithmsplugin.operators.galightswitch;

import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.core.IGeneration;
import org.sara.interfaces.algorithms.ga.galightswitch.IGALightSwitch;

public class GALightSwitch  implements IGALightSwitch{
    
    public GALightSwitch(int maxGenerationNumber) {
        this.maxGenerationNumber = maxGenerationNumber;
    }

    @Override
    public boolean stop(IGeneration generation) {
        ICore.getInstance().getUiController().printProgressBar(generation.getNumber(), this.maxGenerationNumber);
        return generation.getNumber() == maxGenerationNumber;
    }
    
    private final int maxGenerationNumber;
}
