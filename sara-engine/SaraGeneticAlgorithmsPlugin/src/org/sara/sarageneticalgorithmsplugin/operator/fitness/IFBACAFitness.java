package org.sara.sarageneticalgorithmsplugin.operator.fitness;

import org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria.CriteriaManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.IFitness;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria.AccessibilityCriteria;
import org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria.BestUseSpaceCriteria;
import org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria.ClassRoomExchangeCriteria;
import org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria.DuplicateAllocationCriteria;
import org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria.UnallocatedClassCriteria;

public final class IFBACAFitness implements IFitness {

    public IFBACAFitness(boolean multithreaded) {
        this.setMultithreaded(multithreaded);
        this.criteriaManager = new CriteriaManager();
        //Constraints filters
        this.criteriaManager.addCriteria(new DuplicateAllocationCriteria(true)); //C2

        //Quality filters
        this.criteriaManager.addCriteria(new UnallocatedClassCriteria()); //Q1
        this.criteriaManager.addCriteria(new AccessibilityCriteria()); //Q2
        this.criteriaManager.addCriteria(new BestUseSpaceCriteria());//Q3 (and C3 too)
        this.criteriaManager.addCriteria(new ClassRoomExchangeCriteria()); //Q4 e Q5
        //this.criteriaManager.addCriteria(new ClassRequerimentCriteria()); //Q6 e Q7
    }

    @Override
    public void evaluate(IPopulation population) {
        int popNumber = ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber();
        List<Thread> threads = new ArrayList<>();
        
        final int threadNumbers = ICore.getInstance().getProjectController().AVAILABLE_PROCESSORS;
        final int specimenPerThread = popNumber / threadNumbers;
        
        //Quando o número da população é menor do que o número de Threads, não é necessário utiliza-las
        if(!this.isMultithreaded() || popNumber < ICore.getInstance().getProjectController().AVAILABLE_PROCESSORS) {
            population.getAllSpecimens(false).forEach(specimen -> this.evaluate(specimen));
            this.criteriaManager.clearAll();
            return;
        }

        for (int i = 0; i < threadNumbers; i++) {
            int start = i * specimenPerThread;
            int end = specimenPerThread * (i + 1);

            threads.add(new Thread(() -> {
                for (int j = start; j < end; j++) {
                    this.evaluate(population.getAllSpecimens(false).get(j));
                }
            }));
        }

        threads.forEach((t) -> {
            t.start();
        });

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(IFBACAFitness.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.criteriaManager.clearAll();
    }
    
    public boolean isMultithreaded() {
        return multithreaded;
    }

    public void setMultithreaded(boolean multithreaded) {
        this.multithreaded = multithreaded;
    }

    protected float calculateFitness(IChromosome chromosome) {
        return criteriaManager.processFilter(chromosome);
    }

    private void evaluate(ISpecimen specimen) {
        for (IChromosome c : specimen.getChromossomes(false)) {
            c.setFitness(this.calculateFitness(c));
        }
    }

    private final CriteriaManager criteriaManager;
    private boolean multithreaded;
}
