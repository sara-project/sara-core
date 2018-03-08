package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;

public class BestUseSpaceCriteria extends ICriteria {
    
    public BestUseSpaceCriteria() {
        super(false);
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
        int totalEmpty = 0;
        int total = chromosome.getGenes(false).size();
        
        for(IGene gene : chromosome.getGenes(false)) {
            Slot slot = (Slot) gene.getAllele(false);
            if(!slot.isEmpty()) {
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
       
        int totalUsed = total - totalEmpty;

        return totalOverload == 0? (8 * (totalExactAmount / totalUsed)) + (16 * totalUnusedPlaces / totalUsedPlaces) : 0;
    }
}
