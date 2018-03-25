package org.sara.interfaces.algorithms.ga.operator.fitness.criteria;

public interface ICriteriaManager {

    public void addCriteria(ICriteria criteria);

    public void removeCriteria(int index);

    public void clearAll();
}
