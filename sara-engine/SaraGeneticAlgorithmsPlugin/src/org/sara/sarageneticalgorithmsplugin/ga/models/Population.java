package org.sara.sarageneticalgorithmsplugin.ga.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.population.IPopulation;

public class Population implements IPopulation {
    public Population(int limit) {
        specimens = new ArrayList<>();
        this.limit = limit;
    }
    
    public void addSpecimen(Specimen specimen) {
        if(!this.isFull())
            this.specimens.add(specimen);
    }
    
    public void addSpecimen(List<Specimen> specimens) {
        this.specimens.addAll(specimens);
    }
    
    public boolean isFull() {
        return limit <= this.specimens.size();
    }
    
    @Override
    public int getSize() {
        return this.specimens.size();
    }
    
    public Specimen getRandomSpecimen() {
        return this.specimens.get(new Random().nextInt(this.getSize()));
    }
    
    @Override
    public void sortByFitness() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public IChromosome getBestChromosome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private List<Specimen> specimens;
    private final int limit;

    @Override
    public void addChromosome(IChromosome chromosome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IChromosome getChromosome(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IChromosome getRandomChromosome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getAllChromosomes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
