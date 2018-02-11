package org.sara.sarageneticalgorithmsplugin.ga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IPopulation;

public class Population implements IPopulation, Cloneable {

    public Population(int limit) {
        this(limit, new ArrayList<>());
    }

    public Population(int limit, List<ISpecimen> specimens) {
        this.specimens = new ArrayList<>();
        
        specimens.forEach((s -> this.specimens.add((ISpecimen) s.clone())));
        this.limit = limit;
        this.isSorted = false;
    }

    @Override
    public ISpecimen getFirstSpecimen(boolean clone) {
        return this.getSpecimen(0, clone);
    }

    @Override
    public ISpecimen getLastSpecimen(boolean clone) {
        return this.getSpecimen(this.specimens.size() - 1, clone);
    }

    @Override
    public ISpecimen getRandomSpecimen(boolean clone) {
        return this.getSpecimen(new Random().nextInt(this.size()), clone);
    }

    @Override
    public ISpecimen getSpecimen(int index, boolean clone) {
        if (this.specimens.isEmpty())
            return null;
        return clone? (ISpecimen) this.specimens.get(index).clone() : this.specimens.get(index);
    }

    private List<ISpecimen> getAllSpecimensClone() {
        List<ISpecimen> clone = new ArrayList<>();
        this.specimens.forEach(s -> clone.add((ISpecimen) s.clone()));
        return clone;
    }

    @Override
    public List<ISpecimen> getAllSpecimens(boolean clone) {
        return clone? this.getAllSpecimensClone() : this.specimens;
    }

    @Override
    public void addSpecimen(ISpecimen specimen, boolean clone) {
        if (!this.isFull()) {
            this.specimens.add(clone? (ISpecimen) specimen.clone() : specimen);
            this.isSorted = false;
        }
    }

    @Override
    public void addSpecimens(List<ISpecimen> specimens, boolean clone) {
        specimens.forEach(s -> this.addSpecimen(s, clone));
    }

    @Override
    public synchronized boolean isFull() {
        return limit <= this.specimens.size();
    }

    @Override
    public void sortByFitness() {
        if(!this.isSorted) {
            this.specimens.sort((ISpecimen s1, ISpecimen s2) -> Float.compare(s1.getFitness(), s2.getFitness()));
            this.isSorted = true;
        }
    }

    @Override
    public Object clone() {
        Population clone = new Population(this.limit, this.getAllSpecimens(true));
        clone.isSorted = this.isSorted;
        return clone;
    }

    @Override
    public ISpecimen getBestSpecimen(boolean clone) {
        IPopulation pop = clone? (IPopulation) this.clone() : this;
        pop.sortByFitness();
        return pop.getFirstSpecimen(clone);
    }
    
    @Override
    public List<ISpecimen> getBetterSpecimens(int quantity, boolean clone) {
        List<ISpecimen> better = new ArrayList<>();
        this.sortByFitness();
        
        for(int i = 0; i < quantity; i++)
            better.add(this.getSpecimen(i, clone));
        
        return better;
    }
    
    @Override
    public void removeLastSpecimen(int quantity) {
        while(quantity-- > 0) {
            this.specimens.remove(this.size() - 1);
            this.isSorted = false;
        }
    }

    @Override
    public int size() {
        return this.specimens.size();
    }

    public void removeSpecimen(int index) {
        this.specimens.remove(index);
        this.isSorted = false;
    }

    @Override
    public void removeSpecimen(ISpecimen specimen) {
        this.isSorted = !this.specimens.remove(specimen);
    }

    public void removeSpecimen(List<ISpecimen> specimens) {
        this.specimens.removeAll(specimens);
        this.isSorted = false;
    }

    @Override
    public void clearSpecimens() {
        this.specimens.clear();
        this.isSorted = false;
    }

    private final List<ISpecimen> specimens;
    private final int limit;
    private boolean isSorted;
}
