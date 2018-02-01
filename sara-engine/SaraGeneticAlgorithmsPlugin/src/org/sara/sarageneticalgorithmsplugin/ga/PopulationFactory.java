package org.sara.sarageneticalgorithmsplugin.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sara.interfaces.ICore;
import org.sara.interfaces.abstractfactories.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.population.IPopulation;
import org.sara.sarageneticalgorithmsplugin.ga.models.Population;

public class PopulationFactory implements IPopulationFactory {
    
    public static PopulationFactory getInstance() {
        if(instance == null)
            instance = new PopulationFactory();
        
        return instance;
    }

    @Override
    public IPopulation makePopulation() {
        int popNumber = ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber();
        Population pop = new Population(popNumber);
        if(runInParallel) {
            try {
                List<Thread> threads = new ArrayList<>();

                while(!pop.isFull()) {
                    for(Thread t : threads)
                        t.join();

                    threads = new ArrayList<>();

//                    Multi Threaded v1
//                    for(int i = 0; i < ICore.getInstance().getProjectController().AVAILABLE_PROCESSORS + 1; i++) {
//                        threads.add(new Thread(() -> {
//                            if(!pop.isFull())
//                                pop.addSpecimen(SpecimenFactory.makeSpecimen(1));
//                        }));
//                    }
                    
//                    Multi Threaded v2 (uma thread para cada individuo
                    for(int i = 0; i < ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber(); i++) {
                        threads.add(new Thread(() -> {
                            if(!pop.isFull())
                                pop.addSpecimen(SpecimenFactory.makeSpecimen(1));
                        }));
                    }


                    for(Thread t : threads) {
                        t.start();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(PopulationFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            while(!pop.isFull())
                pop.addSpecimen(SpecimenFactory.makeSpecimen(1));
        }
        
        return pop;
    }
    
    public static void enableParallelExec() {
        runInParallel = true;
    }
    
    public static void disableParallelExec() {
        runInParallel = false;
    }
    
    private PopulationFactory() {
        disableParallelExec();
    }
    private static boolean runInParallel;
    private static PopulationFactory instance;
}
