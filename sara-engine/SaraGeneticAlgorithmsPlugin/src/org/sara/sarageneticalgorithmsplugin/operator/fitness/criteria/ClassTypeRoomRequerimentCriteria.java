package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;

public class ClassTypeRoomRequerimentCriteria extends ICriteria {

    public ClassTypeRoomRequerimentCriteria() {
        super(false);
    }

    public ClassTypeRoomRequerimentCriteria(boolean required) {
        super(required);
    }

    @Override
    public float execute(IChromosome chromosome) {
        int totalMeetsRequirement = 0;
        int totalDoesntMeetsRequirement = 0;
        int totalHasTypeRoomRequirement = 0;

        for (IGene gene : chromosome.getGenes(false)) {
            Slot slot = (Slot) gene.getAllele(false);
            if (!slot.isEmpty()) {
                if (slot.getSchoolClass().hasTypeRoomRequirement()) {
                    totalHasTypeRoomRequirement++;

                    //increases quality when you meet this condition
                    if (slot.getSchoolClass().getTypeRoomsWanted(false).contains(idLab) && slot.getRoom().getType() == idLab) {
                        totalMeetsRequirement++;
                    }

                    if (slot.getSchoolClass().getTypeRoomsWanted(false).contains(slot.getRoom().getType())) {
                        totalMeetsRequirement++;
                    } else if (!slot.getSchoolClass().getTypeRoomsWanted(false).contains(slot.getRoom().getType())) {
                        totalDoesntMeetsRequirement++;
                    }
                }
            }
        }

        if (totalHasTypeRoomRequirement == 0 || totalMeetsRequirement == 0 && totalDoesntMeetsRequirement == 0) {
            return 1f;
        } else {
            float grade = Float.sum((float) (((float) totalMeetsRequirement / (float) totalHasTypeRoomRequirement)), (float) (-0.5 * ((float) totalDoesntMeetsRequirement / (float) totalHasTypeRoomRequirement)));
            return grade < 0 ? 0 : grade > 1 ? 1 : grade;
        }
    }

    private static int idLab = 3;
}
