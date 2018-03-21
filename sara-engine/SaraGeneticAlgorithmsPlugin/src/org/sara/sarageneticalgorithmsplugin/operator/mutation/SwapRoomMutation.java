package org.sara.sarageneticalgorithmsplugin.operator.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.IMutation;

public class SwapRoomMutation extends IMutation {

    @Override
    public IChromosome mutateChromosome(IChromosome chromosome) {
        boolean hasNoChange = true;
        List<Object> contentA = new ArrayList<>();
        List<Object> contentB = new ArrayList<>();
        List<IGene> armA;
        List<IGene> armB;
        List<Integer> tabuList = new ArrayList<>();
        int positionA;
        int positionB;
            
        do {
            //Se já testou todas posições, retorna
            if(tabuList.size() >= chromosome.groupSize())
                return chromosome;
            
            do {
                positionA = ThreadLocalRandom.current().nextInt(chromosome.groupSize());
            }while(tabuList.contains(positionA));
            tabuList.add(positionA);
            
            if(tabuList.size() >= chromosome.groupSize())
                return chromosome;
            do {
                positionB = ThreadLocalRandom.current().nextInt(chromosome.groupSize());
            }while(tabuList.contains(positionB));
            tabuList.add(positionB);
            
            armA = chromosome.getGenesByArm(positionA, false);
            armB = chromosome.getGenesByArm(positionB, false);

            contentA = new ArrayList<>();
            contentB = new ArrayList<>();

            for(int i = 0; i < armA.size(); i++) {
                Object alleleContentA = armA.get(i).getAlleleContent(true);
                Object alleleContentB = armB.get(i).getAlleleContent(true);
                
                contentA.add(alleleContentA);
                contentB.add(alleleContentB);
                
                if(alleleContentA != null || alleleContentB != null)
                    hasNoChange = false;
            }
           
        } while(hasNoChange);
        
        int side = 1;

        for(int i = 0; i < contentA.size(); i++) {
            if(side <= 2) {
                side++;
                if(!armA.get(i).setAlleleContent(contentB.get(i)) || !armB.get(i).setAlleleContent(contentA.get(i))) {
                    armA.get(i).setAlleleContent(contentA.get(i));
                    armB.get(i).setAlleleContent(contentB.get(i));
                }
            }
            else {
                side++;
                armA.get(i).setAlleleContent(contentA.get(i));
                armB.get(i).setAlleleContent(contentB.get(i));
            }
            if(side > 4)
                side = 1;
        }
        
        chromosome.setGenesByArm(positionA, armA);
        chromosome.setGenesByArm(positionB, armB);
        return chromosome;
    }
}
