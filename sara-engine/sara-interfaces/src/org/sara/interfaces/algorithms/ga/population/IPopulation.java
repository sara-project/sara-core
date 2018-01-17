package org.sara.interfaces.algorithms.ga.population;

import java.util.List;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public interface IPopulation {

    public void addChromosome(IChromosome chromosome);

    public IChromosome getChromosome(int index);

    public IChromosome getRandomChromosome();

    public List getAllChromosomes();

    public void sortByFitness();

    public int size();

    public IChromosome getBestChromosome();
}
