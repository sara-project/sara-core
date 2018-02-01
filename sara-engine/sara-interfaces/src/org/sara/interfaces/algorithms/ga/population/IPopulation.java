package org.sara.interfaces.algorithms.ga.population;

import java.util.List;
import org.sara.interfaces.algorithms.ga.ISpecimen;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public interface IPopulation {

    public void sortByFitness();
    public IChromosome getBestChromosome();
    public List<ISpecimen> getAllChromosomes();
}
