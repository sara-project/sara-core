package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

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
            this.criterias.put(this.criterias.size() + 1, c);
        });
    }
    
    public CriteriaManager() {
        this.criterias = new HashMap<>();
    }

    @Override
    public void addCriteria(ICriteria criteria) {
        this.criterias.put(criterias.size() + 1 ,criteria);
    }

    @Override
    public void removeCriteria(int index) {
        this.criterias.remove(index);
    }

    public float processFilter(IChromosome chromosome) {
        this.fitness = 0;
        
        this.criterias.values().forEach(c -> {
            this.fitness += c.execute(chromosome);
        });

        return fitness;
    }
    
    private float fitness;
    private final Map<Integer, ICriteria> criterias;
}
