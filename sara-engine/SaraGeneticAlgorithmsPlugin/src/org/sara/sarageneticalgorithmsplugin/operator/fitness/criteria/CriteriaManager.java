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
            this.criterias.put(this.criterias.size(), c);
        });
    }
    
    public CriteriaManager() {
        this.criterias = new HashMap<>();
    }

    @Override
    public void addCriteria(ICriteria criteria) {
        this.criterias.put(criterias.size() + 1 , criteria);
    }

    @Override
    public void removeCriteria(int index) {
        this.criterias.remove(index);
    }

    public float processFilter(IChromosome chromosome) {
        this.fitness = 0;
        
        for(Integer c : this.criterias.keySet()) {
            float grade = this.criterias.size() - c * (10 * this.criterias.get(c).execute(chromosome));
            if(this.criterias.get(c).isRequired() && grade == 0) {
                this.fitness = 0;
                break;
            }
            else
                this.fitness += grade;
        }

        return fitness;
    }
    
    private float fitness;
    private final Map<Integer, ICriteria> criterias;
}
