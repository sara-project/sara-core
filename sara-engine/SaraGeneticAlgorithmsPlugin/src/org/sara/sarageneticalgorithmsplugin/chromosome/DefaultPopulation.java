package org.sara.sarageneticalgorithmsplugin.chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.population.IPopulation;

public class DefaultPopulation implements IPopulation {

    public DefaultPopulation() {
        this.chromosomes = new ArrayList<>();
        this.random = new Random();
    }

    @Override
    public IChromosome getChromosome(int index) {
        return (IChromosome) this.chromosomes.get(index);
    }

    @Override
    public List getAllChromosomes() {
        return this.chromosomes;
    }

    @Override
    public void sortByFitness() {
        IChromosome[] chromoArray = (IChromosome[]) (this.chromosomes.toArray(new IChromosome[0]));
        Arrays.sort(chromoArray, new DefaultChromosomeComparator());
        this.chromosomes.clear();
        this.chromosomes.addAll(Arrays.asList(chromoArray));
    }

    @Override
    public void addChromosome(IChromosome chromosome) {
        chromosomes.add(chromosome);
    }

    @Override
    public int size() {
        return this.chromosomes.size();
    }

    @Override
    public IChromosome getBestChromosome() {
        this.sortByFitness();
        return this.getChromosome(0);
    }

    @Override
    public IChromosome getRandomChromosome() {
        return this.getChromosome(this.random.nextInt(this.size()));
    }
    
    private final List<IChromosome> chromosomes;
    private Random random = new Random();
}
