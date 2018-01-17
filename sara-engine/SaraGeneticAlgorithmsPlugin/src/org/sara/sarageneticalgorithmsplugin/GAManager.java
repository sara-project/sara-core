package org.sara.sarageneticalgorithmsplugin;

import org.sara.interfaces.algorithms.ga.population.IPopulation;
import org.sara.interfaces.abstractfactories.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.core.IGAManager;
import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.selection.ISelection;
import org.sara.interfaces.algorithms.ga.galightswitch.IGALightSwitch;

public class GAManager implements IGAManager{
    
    public GAManager() {
        /*int size = qtdHorario*professores.size();
        this.populationFactory = new DefaultPopulationFactory(
                new CromossomoHorario(size,disciplinas.size()+1));

        this.fitness = new FitnessHorario(disciplinas,professores);
        this.selection = new BestSelection();
        this.crossover =  TwoPointCrossover();
        this.mutation=  new FlipSwapGene();
        this.gaLightSwitch = new TerminateHorario();*/
    }

    @Override
    public IPopulationFactory getPopulationFactory() {
        return this.populationFactory;
    }

    @Override
    public IFitness getFitness() {
        return this.fitness;
    }

    @Override
    public ISelection getSelection() {
        return this.selection;
    }

    @Override
    public ICrossover getCrossover() {
        return this.crossover;
    }

    @Override
    public IMutation getMutation() {
        return this.mutation;
    }

    @Override
    public IGALightSwitch getGALightSwitch() {
        return gaLightSwitch;
    }
    
    private IPopulationFactory populationFactory;
    private IPopulation pop;
    private IFitness fitness;
    private ISelection selection;
    private ICrossover crossover;
    private IMutation mutation;
    private IGALightSwitch gaLightSwitch;
 }