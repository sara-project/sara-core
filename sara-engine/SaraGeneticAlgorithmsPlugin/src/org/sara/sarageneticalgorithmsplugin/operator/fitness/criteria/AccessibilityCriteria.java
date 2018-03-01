package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;


public class AccessibilityCriteria extends ICriteria {
    
    public AccessibilityCriteria() {
        super(false);
    }

    public AccessibilityCriteria(boolean required) {
        super(required);
    }

    @Override
    public float execute(IChromosome chromosome) {
        int totalMeetsRequirement = 0;
        int totalDoesntMeetsRequirement = 0;
        int totalEmpty = 0;
        int total = chromosome.getGenes(false).size();
        
        for(IGene gene : chromosome.getGenes(false)) {
            Slot slot = (Slot) gene.getAllele(false);
            if(!slot.isEmpty()) {
                if(slot.getSchoolClass().hasAccessibilityRequirement() && slot.getRoom().hasAccessibilityRequirement())
                    totalMeetsRequirement++;
                else if(slot.getSchoolClass().hasAccessibilityRequirement() && !slot.getRoom().hasAccessibilityRequirement())
                    totalDoesntMeetsRequirement++;
            } 
            else
                totalEmpty++;
        }
        
        int totalUsed = total - totalEmpty;
        
        return totalUsed == 0? 0 : (6 * (totalMeetsRequirement / totalUsed)) + (4 * totalDoesntMeetsRequirement / totalUsed);
    }
}