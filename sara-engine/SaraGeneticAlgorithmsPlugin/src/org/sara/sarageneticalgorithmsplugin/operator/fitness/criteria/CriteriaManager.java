package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteriaManager;

public class CriteriaManager implements ICriteriaManager {

    public CriteriaManager(List<ICriteria> criterias) {
        this.criterias = new HashMap<>();
        criterias.forEach((c) -> {
            this.addCriteria(c);
        });
    }

    public CriteriaManager() {
        this.criterias = new HashMap<>();
    }

    @Override
    public void addCriteria(ICriteria criteria) {
        this.criterias.put(criterias.size(), criteria);
    }

    @Override
    public void removeCriteria(int index) {
        this.criterias.remove(index);
        //updates the weights of the criteria
        Collection aux = this.criterias.values();
        this.criterias.clear();
        aux.forEach((c) -> {
            this.addCriteria((ICriteria) c);
        });
    }

    public float processFilter(IChromosome chromosome) {
        float fitness = 0f;

        for (Integer c : this.criterias.keySet()) {
            float grade = (this.criterias.size() - c) * this.criterias.get(c).execute(chromosome);
            if (this.criterias.get(c).isRequired() && grade == 0) {
                fitness = 0f;
                break;
            } else {
                fitness += grade;
            }
        }

        return fitness;
    }

    @Override
    public void clearAll() {
        this.criterias.values().forEach(c -> c.clear());
    }

    private final Map<Integer, ICriteria> criterias;
}
