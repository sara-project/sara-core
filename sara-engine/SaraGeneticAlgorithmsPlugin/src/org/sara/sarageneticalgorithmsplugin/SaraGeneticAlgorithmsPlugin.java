package org.sara.sarageneticalgorithmsplugin;

import org.sara.interfaces.ICore;
import org.sara.interfaces.IPlugin;
import org.sara.interfaces.algorithms.ga.IGAEngine;
import org.sara.sarageneticalgorithmsplugin.ga.EvolutionaryCycle;

public class SaraGeneticAlgorithmsPlugin implements IPlugin {

    @Override
    public void initialize() {
        System.out.println("The SaraGeneticAlgorithmsPlugin was initialized.");
        IGAEngine engine = new EvolutionaryCycle();

        ICore.getInstance().getProjectController().setGAEngine(engine);
    }
}
