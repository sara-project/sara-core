package org.sara.sarageneticalgorithmsplugin.operator.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.IMutation;

public class SwapRoomMutation extends IMutation {

    @Override
    public void mutate(IChromosome chromosome) {
        int positionA = ThreadLocalRandom.current().nextInt(0, chromosome.groupSize());
        int positionB = ThreadLocalRandom.current().nextInt(0, chromosome.groupSize());

        if (positionA == positionB && positionB == (chromosome.groupSize() - 1))
            positionB--;
        else if (positionA == positionB)
            positionB++;

        List<IGene> armA = chromosome.getGenesByArm(positionA, false);
        List<IGene> armB = chromosome.getGenesByArm(positionB, false);

        List<Object> contentA = new ArrayList<>();
        List<Object> contentB = new ArrayList<>();

        armA.forEach((g) -> {
            contentA.add(g.getAlleleContent(false));
            g.setAlleleContent(null);
        });

        armB.forEach((g) -> {
            contentB.add(g.getAlleleContent(false));
            g.setAlleleContent(null);
        });
        
        int side = 1;

        for(int i = 0; i < contentA.size(); i++) {
            if(side <= 2) {
                armA.get(i).setAlleleContent(contentB.get(i));
                armB.get(i).setAlleleContent(contentA.get(i));
                side++;
            }
            else {
                armA.get(i).setAlleleContent(contentA.get(i));
                armB.get(i).setAlleleContent(contentB.get(i));
                side++;
            }
            if(side > 4)
                side = 1;
        }
    }
}
