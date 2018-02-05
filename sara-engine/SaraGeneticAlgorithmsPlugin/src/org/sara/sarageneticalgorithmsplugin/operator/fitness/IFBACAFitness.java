package org.sara.sarageneticalgorithmsplugin.operator.fitness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.IFitness;
import org.sara.interfaces.algorithms.ga.model.IPopulation;

public class IFBACAFitness implements IFitness {

    public IFBACAFitness() {
        this.filter = new CriteriaManager();
        System.out.println("OS FILTROS PRECISAM SER ADICIONADOS!");
    }

    @Override
    public void evaluate(IPopulation population) {
        int popNumber = ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber();
        List<Thread> threads = new ArrayList<>();

        final int threadNumbers = ICore.getInstance().getProjectController().AVAILABLE_PROCESSORS;
        final int specimenPerThread = popNumber / threadNumbers;

        for (int i = 0; i < threadNumbers; i++) {
            int start = i * specimenPerThread;
            int end = specimenPerThread * (i + 1);

            threads.add(new Thread(() -> {
                for (int j = start; j < end; j++) {
                    IFBACAFitness.this.evaluate(population.getAllSpecimens().get(j));
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
    }

    protected float calculateFitness(IChromosome chromosome) {
        return (float) (new Random().nextFloat() + 1.0);  //filter.processFilter(chromosome);
    }

    private void evaluate(ISpecimen specimen) {
        for (IChromosome c : specimen.getChromossomes()) {
            c.setFitness(this.calculateFitness(c));
        }
    }

    private final CriteriaManager filter;
}
