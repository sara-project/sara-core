package org.sara.sarageneticalgorithmsplugin.operator.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.IMutation;

public class SwapHalfRoomMutation extends IMutation {

    @Override
    public void mutate(IChromosome chromosome) {
        boolean hasNoChange = true;
        List<Object> contentA = new ArrayList<>();
        List<Object> contentB = new ArrayList<>();
        List<IGene> armA;
        List<IGene> armB;
        List<Integer> tabuList = new ArrayList<>();

        do {
            int positionA;
            int positionB;
            
            //Se já testou todas posições, retorna
            if(tabuList.size() >= chromosome.groupSize())
                return;
            
            do {
                positionA = ThreadLocalRandom.current().nextInt(chromosome.groupSize());
            }while(tabuList.contains(positionA));
            tabuList.add(positionA);
            
            if(tabuList.size() >= chromosome.groupSize())
                return;
            do {
                positionB = ThreadLocalRandom.current().nextInt(chromosome.groupSize());
            }while(tabuList.contains(positionB));
            tabuList.add(positionB);
            
            armA = chromosome.getGenesByArm(positionA, false);
            armB = chromosome.getGenesByArm(positionB, false);

            contentA = new ArrayList<>();
            contentB = new ArrayList<>();

            for(int i = 0; i < armA.size(); i++) {
                Object alleleContentA = armA.get(i).getAlleleContent(false);
                Object alleleContentB = armB.get(i).getAlleleContent(false);
                
                contentA.add(alleleContentA);
                contentB.add(alleleContentB);
                
                if(alleleContentA != null || alleleContentB != null)
                    hasNoChange = false;
            }
           
        } while(hasNoChange);
        
        for (int i = 0; i < contentA.size(); i++) {
            armA.get(i).setAlleleContent(contentB.get(i));
            armB.get(i).setAlleleContent(contentA.get(i));
        }
    }
}
