package org.sara.sarageneticalgorithmsplugin.ga;

import java.util.ArrayList;
import java.util.List;
import org.sara.sarageneticalgorithmsplugin.ga.factory.RandomPopulationFactory;
import org.sara.interfaces.ICore;
import org.sara.interfaces.ga.factory.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.IGAEngine;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
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
import org.sara.sarageneticalgorithmsplugin.operator.crossover.GreatestCrossover;
import org.sara.sarageneticalgorithmsplugin.operator.mutation.SwapMutation;
import org.sara.sarageneticalgorithmsplugin.operators.selection.RankingSelection;
import org.sara.sarageneticalgorithmsplugin.operator.galightswitch.GALightSwitch;
import org.sara.sarageneticalgorithmsplugin.operator.mutation.RandomMutation;
import org.sara.sarageneticalgorithmsplugin.operators.selection.RandomSelection;
import org.sara.sarageneticalgorithmsplugin.operators.selection.TournamentSelection;

public class EvolutionaryCycle implements IGAEngine {

    public EvolutionaryCycle() {
        System.out.println("Engine EvolutionaryCycle was created.");

        ISelection[] selections = {new RankingSelection(), new TournamentSelection()};
        ICrossover[] crossovers = {new TwoPointCrossover(), new UniformCrossover(), new GreatestCrossover()};
        IMutation[] mutations = {new SwapMutation()};

        this.gaConfiguration = ICore.getInstance().getModelController().getGaConfiguration();
        this.gaConfiguration.setGaLightSwitch(new GALightSwitch(gaConfiguration.getMaxGeneration()));

        this.gaConfiguration.setSelection(new RandomSelection(selections));
        this.gaConfiguration.setCrossover(new RandomCrossover(crossovers));
        this.gaConfiguration.setMutation(new RandomMutation(mutations));
        this.gaConfiguration.setFitness(new IFBACAFitness());
    }

    @Override
    public IGALightSwitch startGA() {
        return this.startCycle();
    }

    private IGALightSwitch startCycle() {
        IPopulation population;

        //Get the current operators
        IFitness fitness = this.gaConfiguration.getFitness();
        ISelection selection = this.gaConfiguration.getSelection();
        ICrossover crossover = this.gaConfiguration.getCrossover();
        IMutation mutation = this.gaConfiguration.getMutation();
        IGALightSwitch terminate = this.gaConfiguration.getGaLightSwitch();
        IPopulationFactory populationFactory = RandomPopulationFactory.getInstance();

        int genNumber = 1;

        population = populationFactory.makePopulation();

        do {
            //Calcula o fitness de cada indivíduo
            fitness.evaluate(population); //<==== FAZER
            
            
            List<ISpecimen> elite = new ArrayList<>();
            //Garante o Elitismo (Uma parte dos melhores indivíduos dos genitores
            elite.addAll(population.getBetterSpecimens((int) (population.size() * this.gaConfiguration.getElitismProbability()), true));
            
            //Seleciona os genitores (parents)
            selection.select(population, this.gaConfiguration.getSelectProbability());

            //Cruza os genitores e gera os descendentes (offSpring)
            ((RandomCrossover) crossover).changeMode();
            crossover.makeOffspring(population);

            //Gera a mutação em cima dos genitores
            mutation.mutate(population, this.gaConfiguration.getMutationProbability());
            
            
            //Atualiza População
            population.sortByFitness();
            population.removeLastSpecimen(elite.size());
            population.addSpecimens(elite, true);
            elite.clear();
        } while (!terminate.stop(new Generation(genNumber++, population)));

        return terminate;
    }

    private final GAConfiguration gaConfiguration;
}