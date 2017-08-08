package org.sara.sarageneticalgorithmsplugin;

import org.sara.interfaces.IPlugin;
import org.sara.interfaces.abstractfactories.ICRAssignmentFactory;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.chromosome.IPopulation;

public class SaraGeneticAlgorithmsPlugin extends ICRAssignmentFactory implements IPlugin
{
    @Override
    public void initialize() {
        
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