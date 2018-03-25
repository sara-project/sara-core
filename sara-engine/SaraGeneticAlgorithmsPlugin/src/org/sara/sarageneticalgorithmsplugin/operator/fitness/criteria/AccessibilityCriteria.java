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
        int totalHasAccessibilityRequirement = 0;

        for (IGene gene : chromosome.getGenes(false)) {
            Slot slot = (Slot) gene.getAllele(false);
            if (!slot.isEmpty()) {

                if (slot.getSchoolClass().hasAccessibilityRequirement()) {
                    totalHasAccessibilityRequirement++;

                    if (slot.getRoom().hasAccessibilityRequirement()) {
                        totalMeetsRequirement++;
                    } else if (!slot.getRoom().hasAccessibilityRequirement()) {
                        totalDoesntMeetsRequirement++;
                    }
                }
            }
        }

        if (totalHasAccessibilityRequirement == 0 || totalMeetsRequirement == 0 && totalDoesntMeetsRequirement == 0) {
            return 1f;
        } else {
            float grade = Float.sum((float) (((float) totalMeetsRequirement / (float) totalHasAccessibilityRequirement)), (float) (-0.5 * ((float) totalDoesntMeetsRequirement / (float) totalHasAccessibilityRequirement)));
            return grade < 0 ? 0 : grade;
        }
    }
}
