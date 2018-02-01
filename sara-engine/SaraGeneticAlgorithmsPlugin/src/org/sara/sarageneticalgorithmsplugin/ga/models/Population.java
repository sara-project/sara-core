package org.sara.sarageneticalgorithmsplugin.ga.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.sara.interfaces.algorithms.ga.ISpecimen;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.population.IPopulation;

public class Population implements IPopulation {
    public Population(int limit) {
        specimens = new ArrayList<>();
        this.limit = limit;
    }
    
    public ISpecimen getFirstSpecimen() {
        if(this.specimens.isEmpty())
            return null;
        
        return this.specimens.get(0);
    }
    
    public ISpecimen getLastSpecimen() {
        if(this.specimens.isEmpty())
            return null;
        
        return this.specimens.get(this.specimens.size());
    }
    
    public ISpecimen getRandomSpecimen() {
        return this.specimens.get(new Random().nextInt(this.getSize()));
    }

    @Override
    public List<ISpecimen> getAllChromosomes() {
         return  this.specimens;
    }

    public synchronized void addSpecimen(ISpecimen specimen) {
        if(!this.isFull())
            this.specimens.add(specimen);
    }
    
    public synchronized void addSpecimen(List<ISpecimen> specimens) {
        this.specimens.addAll(specimens);
    }
    
    public synchronized boolean isFull() {
        return limit <= this.specimens.size();
    }
    
    public int getSize() {
        return this.specimens.size();
    }
    
    @Override
    public void sortByFitness() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public IChromosome getBestChromosome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void removeSpecimen(int index) {
        this.specimens.remove(index);
    }

    private List<ISpecimen> specimens;
    private final int limit;
}
