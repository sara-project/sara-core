package org.sara.sarageneticalgorithmsplugin;

import org.sara.sarageneticalgorithmsplugin.ga.GAEngine;
import org.sara.interfaces.ICore;
import org.sara.interfaces.IPlugin;
import org.sara.interfaces.abstractfactories.ICRAssignmentFactory;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.population.IPopulation;
import org.sara.interfaces.algorithms.ga.IGAEngine;

public class SaraGeneticAlgorithmsPlugin extends ICRAssignmentFactory implements IPlugin
{
    @Override
    public void initialize() {
        IGAEngine engine = new GAEngine();
        
        
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