package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;

public class BestUseSpaceCriteria extends ICriteria {
    
    public BestUseSpaceCriteria() {
        this(false);
    }

    public BestUseSpaceCriteria(boolean required) {
        super(required);
    }

    @Override
    public float execute(IChromosome chromosome) {
        int totalExactAmount = 0; //Quantidade de Turmas que possuem o mesmo tamanho da Sala
        int totalUnusedPlaces = 0; //Quantidade de "Lugares" que não foram utilizados pelas salas
        int totalUsedPlaces = 0;
        int totalOverload = 0; //Qunatidade de Turmas que são maiores do que a Sala
        int totalUsed = 0;
        
        for(IGene gene : chromosome.getGenes(false)) {
            Slot slot = (Slot) gene.getAllele(false);
            if(!slot.isEmpty()) {
                totalUsed++;
                totalUsedPlaces += slot.getRoom().getCapacity();
                int result = slot.useOfRoom(slot.getSchoolClass());
                
                if(result == 0)
                    totalExactAmount++;
                else if(result > 0)
                    totalUnusedPlaces += result;
                else
                    totalOverload++;
            }
        }
       
        if(totalOverload > 0) {
            this.required = totalOverload > 0;
            return 0;
        }

        return Float.sum((float)(0.4 * ((float)totalExactAmount / (float)totalUsed)), (float)(0.8 * ((float)totalUnusedPlaces / (float)totalUsedPlaces)));
    }
}
