package org.sara.interfaces.algorithms.ga.chromosome;

import java.util.List;

public interface IPopulation {

    public void addChromosome(IChromosome chromosome);

    public IChromosome getChromosome(int index);

    public IChromosome getRandomChromosome();

    public List getAllChromosomes();

    public void sortByFitness();

    public int size();

    public IChromosome getBestChromosome();
}
