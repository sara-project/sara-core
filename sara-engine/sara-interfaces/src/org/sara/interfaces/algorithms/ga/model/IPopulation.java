package org.sara.interfaces.algorithms.ga.model;

import java.util.List;

public interface IPopulation extends Cloneable {

    public int size();

    public Object clone();

    public void sortByFitness();

    public ISpecimen getSpecimen(int index, boolean clone);

    public ISpecimen getFirstSpecimen(boolean clone);

    public ISpecimen getLastSpecimen(boolean clone);

    public void removeLastSpecimen(int quantity);

    public ISpecimen getRandomSpecimen(boolean clone);

    public List<ISpecimen> getAllSpecimens(boolean clone);

    public ISpecimen getBestSpecimen(boolean clone);

    public List<ISpecimen> getBestSpecimens(int quantity, boolean clone);

    public void removeSpecimen(ISpecimen specimen);

    public void addSpecimens(List<ISpecimen> specimens, boolean clone);

    public void addSpecimen(ISpecimen specimen, boolean clone);

    public boolean isFull();

    public void clearSpecimens();

    public void sizeAdjustment();
}
