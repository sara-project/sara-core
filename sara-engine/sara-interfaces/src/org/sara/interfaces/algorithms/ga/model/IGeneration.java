package org.sara.interfaces.algorithms.ga.model;

public interface IGeneration {

    public int getNumber();

    public IPopulation getPopulation(boolean clone);
}
