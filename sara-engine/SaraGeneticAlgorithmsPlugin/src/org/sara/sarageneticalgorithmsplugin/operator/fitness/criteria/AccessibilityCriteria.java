package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;


public class AccessibilityCriteria extends ICriteria {
    
    public AccessibilityCriteria() {
        this(false);
    }

    public AccessibilityCriteria(boolean required) {
        super(required);
    }

    @Override
    public float execute(IChromosome chromosome) {
        int totalMeetsRequirement = 0;
        int totalDoesntMeetsRequirement = 0;
        int totalUsed = 0;
        
        for(IGene gene : chromosome.getGenes(false)) {
            Slot slot = (Slot) gene.getAllele(false);
            if(!slot.isEmpty()) {
                totalUsed++;

                if(slot.getSchoolClass().hasAccessibilityRequirement() && slot.getRoom().hasAccessibilityRequirement())
                    totalMeetsRequirement++;
                else if(slot.getSchoolClass().hasAccessibilityRequirement() && !slot.getRoom().hasAccessibilityRequirement())
                    totalDoesntMeetsRequirement++;
            } 
        }
        
        if(totalUsed == 0)
            return 0f;
        
       if(totalMeetsRequirement == 0 && totalDoesntMeetsRequirement == 0)
           return 1f;
       else{
           float grade = Float.sum((float)(((float) totalMeetsRequirement / (float)totalUsed)), (float)(-1.0 * ((float)totalDoesntMeetsRequirement / (float)totalUsed)));
           return grade < 0? 0 : grade;
       }
    }
}