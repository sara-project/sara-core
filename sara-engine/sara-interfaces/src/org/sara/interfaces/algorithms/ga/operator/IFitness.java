package org.sara.interfaces.algorithms.ga.operator;

import org.sara.interfaces.algorithms.ga.model.IPopulation;

public interface IFitness {

    public void evaluate(IPopulation population);
}
