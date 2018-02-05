package org.sara.sarageneticalgorithmsplugin.ga;

import java.util.ArrayList;
import java.util.List;
import org.sara.sarageneticalgorithmsplugin.ga.factory.PopulationFactory;
import org.sara.interfaces.ICore;
import org.sara.interfaces.ga.factory.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.IGAEngine;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.ICrossover;
import org.sara.interfaces.algorithms.ga.operator.IFitness;
import org.sara.interfaces.algorithms.ga.operator.IGALightSwitch;
import org.sara.interfaces.algorithms.ga.operator.IMutation;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.interfaces.algorithms.ga.operator.ISelection;
import org.sara.interfaces.model.GAConfiguration;
import org.sara.interfaces.model.Slot;
import org.sara.sarageneticalgorithmsplugin.operator.crossover.RandomCrossover;
import org.sara.sarageneticalgorithmsplugin.operator.crossover.TwoPointCrossover;
import org.sara.sarageneticalgorithmsplugin.operator.crossover.UniformCrossover;
import org.sara.sarageneticalgorithmsplugin.operator.fitness.IFBACAFitness;
import org.sara.sarageneticalgorithmsplugin.ga.model.Generation;
import org.sara.sarageneticalgorithmsplugin.operator.mutation.SwapMutation;
import org.sara.sarageneticalgorithmsplugin.operators.selection.BestSelection;
import org.sara.sarageneticalgorithmsplugin.operator.galightswitch.GALightSwitch;
import org.sara.sarageneticalgorithmsplugin.operator.mutation.RandomMutation;
import org.sara.sarageneticalgorithmsplugin.operators.selection.RandomSelection;
import org.sara.sarageneticalgorithmsplugin.operators.selection.RankingSelection;

public class EvolutionaryCycle implements IGAEngine {

    public EvolutionaryCycle() {
        System.out.println("Engine EvolutionaryCycle was created.");

        ISelection[] selections = {new BestSelection(), new RankingSelection()};
        ICrossover[] crossovers = {new TwoPointCrossover(), new UniformCrossover()};
        IMutation[] mutations = {new SwapMutation()};

        this.gaConfiguration = ICore.getInstance().getModelController().getGaConfiguration();
        this.gaConfiguration.setGaLightSwitch(new GALightSwitch(gaConfiguration.getMaxGeneration()));

        this.gaConfiguration.setSelection(new RandomSelection(selections));
        this.gaConfiguration.setCrossover(new RandomCrossover(crossovers));
        this.gaConfiguration.setMutation(new RandomMutation(mutations));
        this.gaConfiguration.setFitness(new IFBACAFitness());
    }

    @Override
    public List<Slot> startGA() {
        return this.startCycle();
    }

    private List<Slot> startCycle() {
        IPopulation population;

        //Get the current operators
        IFitness fitness = this.gaConfiguration.getFitness();
        ISelection selection = this.gaConfiguration.getSelection();
        ICrossover crossover = this.gaConfiguration.getCrossover();
        IMutation mutation = this.gaConfiguration.getMutation();
        IGALightSwitch terminate = this.gaConfiguration.getGaLightSwitch();
        IPopulationFactory populationFactory = PopulationFactory.getInstance();

        int genNumber = 1;

        population = populationFactory.makePopulation();
        ISpecimen bestSpecimen = (ISpecimen) population.getRandomSpecimen().clone();
        for (IChromosome c : bestSpecimen.getChromossomes()) {
            c.setFitness(c.getFitness() + 10);
        }

        do {
            //Calcula o fitness de cada indivíduo
            fitness.evaluate(population); //<==== FAZER

            if (Float.max(population.getBestSpecimen().getFitness(), bestSpecimen.getFitness()) == population.getBestSpecimen().getFitness()) {
                bestSpecimen = (ISpecimen) population.getBestSpecimen().clone();
            }

            //Seleciona os genitores
            IPopulation parents = selection.select(population, this.gaConfiguration.getSelectProbability());

            //Cruza os genitores e gera os descendentes
            IPopulation offSpring = crossover.makeOffspring(parents);

            //Gera a mutação em cima dos genitores
            mutation.mutate(offSpring, this.gaConfiguration.getMutationProbability());

            //Atualiza a geração
            population = offSpring;
        } while (!terminate.stop(new Generation(genNumber++, population)));

        List<Slot> slots = new ArrayList<>();
        bestSpecimen.getAllGenes().forEach((gene) -> slots.add((Slot) gene));
        return slots;
    }

    private final GAConfiguration gaConfiguration;
}
