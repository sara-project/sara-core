package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;


public class AccessibilityCriteria  implements ICriteria {

    @Override
    public float execute(IChromosome chromosome) {
        int totalMeetsRequirement = 0;
        int totalDoesntMeetsRequirement = 0;
        
        for(IGene gene : chromosome.getGenes(false)) {
            Slot slot = (Slot) gene.getAllele(false);
            if(!slot.isEmpty()) {
                if(slot.getSchoolClass().hasAccessibilityRequirement() && slot.getRoom().hasAccessibilityRequirement())
                    totalMeetsRequirement++;
                else if(slot.getSchoolClass().hasAccessibilityRequirement() && !slot.getRoom().hasAccessibilityRequirement())
                    totalDoesntMeetsRequirement++;
            }
        }
        
        return totalMeetsRequirement + (totalDoesntMeetsRequirement * -1);
    }
}