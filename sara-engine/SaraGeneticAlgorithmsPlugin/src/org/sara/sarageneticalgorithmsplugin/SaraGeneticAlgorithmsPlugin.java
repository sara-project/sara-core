package org.sara.sarageneticalgorithmsplugin;

import org.sara.interfaces.ICore;
import org.sara.interfaces.IPlugin;
import org.sara.interfaces.abstractfactories.ICRAssignmentFactory;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;
import org.sara.interfaces.algorithms.ga.core.IGAManager;
import org.sara.interfaces.algorithms.ga.core.IGAParameters;
import org.sara.interfaces.algorithms.ga.IGAEngine;

public class SaraGeneticAlgorithmsPlugin extends ICRAssignmentFactory implements IPlugin
{
    @Override
    public void initialize() {
        IGAManager gaManager = new GAManager();
        IGAParameters gaParameters = new GAParamters();
        IGAEngine engine = new GAEngine(gaManager, gaParameters);
        
        ICore.getInstance().getProjectController().setGAEngine(engine);
    }

    @Override
    public IChromosome CreateChromosome() {
        return null;
    }

    @Override
    public IPopulation CreatePopulation() {
        return null;
    }
}