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
        this.specimens.addAll(specimens);
        this.limit = limit;
    }

    @Override
    public ISpecimen getFirstSpecimen() {
        if (this.specimens.isEmpty()) {
            return null;
        }

        return this.specimens.get(0);
    }

    @Override
    public ISpecimen getLastSpecimen() {
        if (this.specimens.isEmpty()) {
            return null;
        }

        return this.specimens.get(this.specimens.size() - 1);
    }

    @Override
    public ISpecimen getRandomSpecimen() {
        return (ISpecimen) this.specimens.get(new Random().nextInt(this.size())).clone();
    }

    @Override
    public ISpecimen getSpecimen(int index) {
        return this.specimens.get(index);
    }

    @Override
    public List<ISpecimen> getAllSpecimens() {
        return this.specimens;
    }

    @Override
    public void addSpecimen(ISpecimen specimen) {
        if (!this.isFull()) {
            this.specimens.add(specimen);
        }
    }

    @Override
    public void addSpecimens(List<ISpecimen> specimens) {
        specimens.forEach((s) -> {
            this.addSpecimen(s);
        });
    }

    @Override
    public synchronized boolean isFull() {
        return limit <= this.specimens.size();
    }

    @Override
    public void sortByFitness() {
        this.specimens.sort((ISpecimen s1, ISpecimen s2) -> Float.compare(s1.getFitness(), s2.getFitness()));
    }

    @Override
    public Object clone() {
        return (IPopulation) new Population(this.limit, this.specimens);
    }

    @Override
    public ISpecimen getBestSpecimen() {
        IPopulation clone = (IPopulation) this.clone();
        clone.sortByFitness();
        return clone.getFirstSpecimen();
    }

    @Override
    public int size() {
        return this.specimens.size();
    }

    public void removeSpecimen(int index) {
        this.specimens.remove(index);
    }

    @Override
    public void removeSpecimen(ISpecimen specimen) {
        this.specimens.remove(specimen);
    }

    public void removeSpecimen(List<ISpecimen> specimens) {
        this.specimens.removeAll(specimens);
    }

    @Override
    public void clearSpecimens() {
        this.specimens.clear();
    }

    private final List<ISpecimen> specimens;
    private final int limit;
}
