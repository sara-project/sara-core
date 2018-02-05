package org.sara.interfaces.algorithms.ga.model;

import java.util.List;

public interface IPopulation extends Cloneable {

    public int size();

    public Object clone();

    public void sortByFitness();

    public ISpecimen getSpecimen(int index);

    public ISpecimen getFirstSpecimen();

    public ISpecimen getLastSpecimen();

    public ISpecimen getRandomSpecimen();

    public List<ISpecimen> getAllSpecimens();

    public ISpecimen getBestSpecimen();

    public void removeSpecimen(ISpecimen specimen);

    public void addSpecimens(List<ISpecimen> specimens);

    public void addSpecimen(ISpecimen specimen);

    public boolean isFull();

    public void clearSpecimens();
}
