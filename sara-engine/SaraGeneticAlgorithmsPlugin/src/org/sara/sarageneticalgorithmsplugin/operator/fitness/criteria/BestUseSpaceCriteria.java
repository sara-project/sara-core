package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Slot;

public class BestUseSpaceCriteria implements ICriteria {

    @Override
    public float execute(IChromosome chromosome) {
        int totalExactAmount = 0; //Quantidade de Turmas que possuem o mesmo tamanho da Sala
        int totalUnusedPlaces = 0; //Quantidade de "Lugares" que não foram utilizados pelas salas
        int totalOverload = 0; //Qunatidade de Turmas que são maiores do que a Sala
        
        for(IGene gene : chromosome.getGenes(false)) {
            Slot slot = (Slot) gene.getAllele(false);
            if(!slot.isEmpty()) {
                int result = slot.useOfRoom(slot.getSchoolClass());
                
                if(result == 0)
                    totalExactAmount++;
                else if(result > 0)
                    totalUnusedPlaces += result;
                else
                    totalOverload++;
            }
        }
        
        return (totalExactAmount * 10) + (totalUnusedPlaces * -1) + (-100 * totalOverload);
    }
}
