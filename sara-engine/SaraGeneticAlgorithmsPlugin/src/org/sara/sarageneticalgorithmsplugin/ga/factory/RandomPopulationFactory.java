package org.sara.sarageneticalgorithmsplugin.ga.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.sara.interfaces.ICore;
import org.sara.interfaces.ga.factory.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.sarageneticalgorithmsplugin.ga.model.Population;

public class RandomPopulationFactory implements IPopulationFactory {

    public static RandomPopulationFactory getInstance() {
        if (instance == null)
            instance = new RandomPopulationFactory();

        return instance;
    }

    @Override
    public IPopulation makePopulation() {
        int popNumber = ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber();
        IPopulation pop = new Population(popNumber);
        specimens = Collections.synchronizedList(new ArrayList<>());

        if (runInParallel) {
            try {
                List<Thread> threads = new ArrayList<>();

                int threadNumbers = ICore.getInstance().getProjectController().AVAILABLE_PROCESSORS;
                int specimenPerThread = popNumber / threadNumbers;

                for (int i = 0; i < threadNumbers; i++) {
                    threads.add(new Thread(() -> {
                        try {
                            specimens.addAll(SpecimenFactory.makeSpecimen(specimenPerThread));
                        } catch (OutOfMemoryError ex) {
                            RandomPopulationFactory.disableParallelExec();
                            System.out.println("An unexpected error was detected. The process of make a population will be restarted");
                            System.gc();
                        }
                    }));
                }

                threads.forEach((t) -> {
                    t.start();
                });

                for (Thread t : threads) {
                    t.join();
                }

            } catch (InterruptedException ex) {
                RandomPopulationFactory.disableParallelExec();
                System.out.println("An unexpected error was detected. The process of make a population will be restarted");
               
                return this.makePopulation();
            }

            // Limpa possíveis Specimens nulos gerados por mal sicronia das Threads;
            List<ISpecimen> beRemoved = new ArrayList<>();
            specimens.stream().filter((specimen) -> (specimen == null)).forEachOrdered((specimen) -> {
                beRemoved.add(specimen);
            });

            specimens.removeAll(beRemoved);
            pop.addSpecimens(specimens, false);
            specimens = null;
        }
        try {
            while (!pop.isFull())
                pop.addSpecimens(SpecimenFactory.makeSpecimen(1), false);
        } catch (OutOfMemoryError ex) {
            RandomPopulationFactory.disableParallelExec();
            System.out.println("An unexpected error was detected. The process of make a population will be restarted");
            System.gc();
        }
        
        return pop;
    }

    public static void enableParallelExec() {
        runInParallel = true;
    }

    public static void disableParallelExec() {
        runInParallel = false;
    }

    private RandomPopulationFactory() {
        enableParallelExec();
    }
    private static boolean runInParallel;
    private static RandomPopulationFactory instance;
    private static List<ISpecimen> specimens;
}
