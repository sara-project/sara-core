package org.sara.sarageneticalgorithmsplugin;

import org.sara.interfaces.ICore;
import org.sara.interfaces.IPlugin;
import org.sara.interfaces.algorithms.ga.IGAEngine;
import org.sara.interfaces.model.GAConfiguration;
import org.sara.sarageneticalgorithmsplugin.ga.EvolutionaryCycle;
import org.sara.sarageneticalgorithmsplugin.crossover.TwoPointCrossover;
import org.sara.sarageneticalgorithmsplugin.fitness.IFBACAFitness;
import org.sara.sarageneticalgorithmsplugin.operators.galightswitch.GALightSwitch;
import org.sara.sarageneticalgorithmsplugin.mutation.SwapGene;
import org.sara.sarageneticalgorithmsplugin.operators.galightswitch.BestSelection;

public class SaraGeneticAlgorithmsPlugin  implements IPlugin
{
    @Override
    public void initialize() {
        System.out.println("The SaraGeneticAlgorithmsPlugin was initialized.");
        IGAEngine engine = new EvolutionaryCycle();
        
        ICore.getInstance().getProjectController().setGAEngine(engine);
        GAConfiguration gaConfiguration = ICore.getInstance().getModelController().getGaConfiguration();
        
        //Assigns the genetic operators that will be used
        gaConfiguration.setGaLightSwitch(new GALightSwitch(gaConfiguration.getMaxGeneration()));
        gaConfiguration.setSelection(new BestSelection()); //needs review
        gaConfiguration.setCrossover(new TwoPointCrossover()); //needs review
        gaConfiguration.setMutation(new SwapGene()); //needs review
        gaConfiguration.setFitness(new IFBACAFitness()); //needs review
        System.out.println("WARRNIG: Operators need to be reviewed... (SaraGeneticAlgorithmsPlugin)");
    }
}