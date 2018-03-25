package org.sara.sarageneticalgorithmsplugin.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.sara.sarageneticalgorithmsplugin.ga.factory.RandomPopulationFactory;
import org.sara.interfaces.ICore;
import org.sara.interfaces.ga.factory.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.IGAEngine;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.ICrossover;
import org.sara.interfaces.algorithms.ga.operator.IFitness;
import org.sara.interfaces.algorithms.ga.operator.IGALightSwitch;
import org.sara.interfaces.algorithms.ga.operator.IMutation;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.interfaces.algorithms.ga.operator.ISelection;
import org.sara.interfaces.model.GAConfiguration;
import org.sara.interfaces.model.InfoSolution;
import org.sara.sarageneticalgorithmsplugin.ga.factory.ExistingSolutionPopulationFactory;
import org.sara.sarageneticalgorithmsplugin.operator.crossover.RandomCrossover;
import org.sara.sarageneticalgorithmsplugin.operator.crossover.TwoPointCrossover;
import org.sara.sarageneticalgorithmsplugin.operator.crossover.UniformCrossover;
import org.sara.sarageneticalgorithmsplugin.operator.fitness.IFBACAFitness;
import org.sara.sarageneticalgorithmsplugin.ga.model.Generation;
import org.sara.sarageneticalgorithmsplugin.operator.crossover.GreatestCrossover;
import org.sara.sarageneticalgorithmsplugin.operator.mutation.SwapRoomMutation;
import org.sara.sarageneticalgorithmsplugin.operators.selection.RankingSelection;
import org.sara.sarageneticalgorithmsplugin.operator.galightswitch.GALightSwitch;
import org.sara.sarageneticalgorithmsplugin.operator.mutation.RandomMutation;
import org.sara.sarageneticalgorithmsplugin.operator.mutation.SwapHalfRoomMutation;
import org.sara.sarageneticalgorithmsplugin.operators.selection.RandomSelection;
import org.sara.sarageneticalgorithmsplugin.operators.selection.TournamentSelection;

public class EvolutionaryCycle implements IGAEngine {

    public EvolutionaryCycle() {
        System.out.println("Engine EvolutionaryCycle was created.");

        ISelection[] selections = {new RankingSelection(), new TournamentSelection()};
        ICrossover[] crossovers = {new TwoPointCrossover(), new UniformCrossover(), new GreatestCrossover()};
        IMutation[] mutations = {new SwapRoomMutation(), new SwapHalfRoomMutation()};
        
        System.out.println("\n------------------");
        System.out.println("Genetic Operators");
        System.out.println("\t- Selections: " + selections.length + " ["+RankingSelection.class.getSimpleName()+", "+ TournamentSelection.class.getSimpleName()+"]");
        System.out.println("\t- Crossovers: " + selections.length + " ["+TwoPointCrossover.class.getSimpleName()+", "+GreatestCrossover.class.getSimpleName()+", "+ UniformCrossover.class.getSimpleName()+"]");
        System.out.println("\t- Mutations: " + selections.length + " ["+SwapRoomMutation.class.getSimpleName()+", "+ SwapHalfRoomMutation.class.getSimpleName()+"]");
        System.out.println();

        this.gaConfiguration = ICore.getInstance().getModelController().getGaConfiguration();
        this.gaConfiguration.setGaLightSwitch(new GALightSwitch(gaConfiguration.getMaxGeneration()));

        this.gaConfiguration.setSelection(new RandomSelection(selections));
        this.gaConfiguration.setCrossover(new RandomCrossover(crossovers));
        this.gaConfiguration.setMutation(new RandomMutation(mutations));
        this.gaConfiguration.setFitness(new IFBACAFitness(true));
    }

    @Override
    public InfoSolution startCycle() {
        IPopulation population;

        //Get the current operators
        IFitness fitness = this.gaConfiguration.getFitness();
        ISelection selection = this.gaConfiguration.getSelection();
        ICrossover crossover = this.gaConfiguration.getCrossover();
        IMutation mutation = this.gaConfiguration.getMutation();
        IGALightSwitch terminate = this.gaConfiguration.getGaLightSwitch();
        IPopulationFactory populationFactory = RandomPopulationFactory.getInstance();
        List<ISpecimen> elite = new ArrayList<>();

        int genNumber = 0;
        long timeOfGenerateInitialPopulation;
        double averageTimeOfFitness;
        double averageTimeOfSelection = 0;
        double averageTimeOfCrossover = 0;
        double averageTimeOfMutation = 0;
        double averageTimeOfRefreshPopulation = 0;
        Date startDate, endDate;
        
        startDate = new Date();
        //Generates initial population
            population = populationFactory.makePopulation();
        //End
        endDate = new Date();
        timeOfGenerateInitialPopulation = endDate.getTime() - startDate.getTime();
        
        startDate = new Date();
        //Calculates the fitness of each specimen
            fitness.evaluate(population);
        //End
        endDate = new Date();
        averageTimeOfFitness = (endDate.getTime() - startDate.getTime());
        
        startDate = new Date();
            population.sortByFitness();
        endDate = new Date();
        averageTimeOfRefreshPopulation = (endDate.getTime() - startDate.getTime());
        
        System.gc();
        while (!terminate.stop(new Generation(++genNumber, population))) {
            //Selects the parents
            startDate = new Date();
                selection.select(population, this.gaConfiguration.getSelectProbability());
                //Ensures Elitism (A part of the best specimens of the parents)
                if(this.gaConfiguration.getElitismProbability() > 0) {
                    elite.clear();
                    elite.addAll(population.getBestSpecimens((int) (population.size() * this.gaConfiguration.getElitismProbability()), true));
                    population.addSpecimens(elite, false);
                }
            //End
            endDate = new Date();
            averageTimeOfSelection =+ (endDate.getTime() - startDate.getTime());
            
            startDate = new Date();
            //Cross the parents and generates the offspring
                crossover.makeOffspring(population);
            //End 
            endDate = new Date();
            averageTimeOfCrossover =+ (endDate.getTime() - startDate.getTime());
            
            //Generates parent-based mutation
            startDate = new Date();
                mutation.mutate(population, this.gaConfiguration.getMutationProbability(), false);
            //End
            endDate = new Date();
            averageTimeOfMutation =+ (endDate.getTime() - startDate.getTime());
            
            startDate = new Date();
            //Calculates the fitness of each specimen
                fitness.evaluate(population);
            //End
            endDate = new Date();
            averageTimeOfFitness =+ (endDate.getTime() - startDate.getTime());
            
            
            startDate = new Date();
            //Updates population
                population.sortByFitness();
                population.sizeAdjustment();
            //End
            endDate = new Date();
            averageTimeOfRefreshPopulation =+ (endDate.getTime() - startDate.getTime());

            System.gc();
        }
        
        int maxGenNumber = this.gaConfiguration.getPopulationNumber();

        //recording the execution metadata
        InfoSolution info = new InfoSolution();
        info.setBestSolution(terminate.getBestSolution());
        info.setFitnessTimeLine(terminate.getFitnessTimeLine());
        info.setAverageTimeOfFitness(averageTimeOfFitness / maxGenNumber);
        info.setTimeOfGenerateInitialPopulation(timeOfGenerateInitialPopulation / maxGenNumber);
        info.setAverageTimeOfCrossover(averageTimeOfCrossover / maxGenNumber);
        info.setAverageTimeOfSelection(averageTimeOfSelection / maxGenNumber);
        info.setAverageTimeOfMutation(averageTimeOfMutation / maxGenNumber);
        info.setAverageTimeOfRefreshPopulation(averageTimeOfRefreshPopulation / maxGenNumber);
        info.setFitnessOfTheBestSolution(terminate.getBestFitness());

        return info;
    }

    @Override
    public InfoSolution evalSolution(Collection chromossomes) {
        if (!(chromossomes instanceof Collection)) {
            System.err.printf("Genetic Load is invalid!");
            System.exit(1);
        }

        List specimen = new ArrayList<>((Collection) chromossomes);
        IFitness fitness = this.gaConfiguration.getFitness();
        long timeOfGenerateInitialPopulation;
        long averageTimeOfFitness;
        Date startDate, endDate;
        InfoSolution info = new InfoSolution();
        
        startDate = new Date();
        //Generates population based only on current solution
            ExistingSolutionPopulationFactory.getInstance().setSpecimenData(specimen);
            IPopulation population = ExistingSolutionPopulationFactory.getInstance().makePopulation();
        //End
        endDate = new Date();
        timeOfGenerateInitialPopulation = endDate.getTime() - startDate.getTime(); 

        //Calculates the fitness of the current specimen 
            ICore.getInstance().getModelController().getGaConfiguration().setPopulationNumber(1);
            fitness.evaluate(population);
        //End
        endDate = new Date();
        averageTimeOfFitness = endDate.getTime() - startDate.getTime();
        
        info.setAverageTimeOfFitness(averageTimeOfFitness);
        info.setTimeOfGenerateInitialPopulation(timeOfGenerateInitialPopulation);
        info.setBestSolution(Arrays.asList(population.getBestSpecimen(true).getChromossomes(false)));
        info.setFitnessOfTheBestSolution(population.getBestSpecimen(false).getFitness());

        return info;
    }
    
    private final GAConfiguration gaConfiguration;
}
