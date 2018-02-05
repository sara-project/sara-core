package org.sara.interfaces.algorithms.ga.operator;

import org.sara.interfaces.algorithms.ga.model.IPopulation;

public interface ISelection {

    public IPopulation select(IPopulation population, double rate);
}
