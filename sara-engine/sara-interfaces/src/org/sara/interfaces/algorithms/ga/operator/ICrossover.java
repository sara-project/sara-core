package org.sara.interfaces.algorithms.ga.operator;

import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IPopulation;

public abstract class ICrossover implements Cloneable {

    public final IPopulation makeOffspring(IPopulation parents) {
        int popSize = ICore.getInstance().getModelController().getGaConfiguration().getPopulationNumber();
        IPopulation offspring = (IPopulation) parents.clone();
        offspring.clearSpecimens();
        double elitismProbability = ICore.getInstance().getModelController().getGaConfiguration().getElitismProbability();

        parents.sortByFitness();

        //Garante o Elitismo (Uma parte dos melhores indivíduos dos genitores
        for (int i = 0; i < (parents.size() * elitismProbability); i++) {
            offspring.addSpecimen(parents.getSpecimen(i));
        }

        while (offspring.size() < popSize) {
            ISpecimen parentA = (ISpecimen) parents.getRandomSpecimen().clone();
            ISpecimen parentB = (ISpecimen) parents.getRandomSpecimen().clone();

            this.crossover(parentA, parentB);

            offspring.addSpecimen(parentA);
            offspring.addSpecimen(parentB);
        }
        return offspring;
    }

    public abstract void crossover(ISpecimen parentA, ISpecimen parentB);
}
