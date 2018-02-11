package org.sara.sarageneticalgorithmsplugin.operator.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.operator.IMutation;

public class SwapMutation extends IMutation {

    @Override
    public void mutate(IChromosome chromosome) {
        int positionA = new Random().nextInt(chromosome.groupSize());
        int positionB = new Random().nextInt(chromosome.groupSize());

        if (positionA == positionB && positionB == (chromosome.groupSize() - 1))
            positionB--;
        else if (positionA == positionB)
            positionB++;

        List<IGene> armA = chromosome.getGenesByType(positionA, false);
        List<IGene> armB = chromosome.getGenesByType(positionB, false);

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

        for (int i = 0; i < contentA.size(); i++) {
            armB.get(i).setAlleleContent(contentA.get(i));
        }

        for (int i = 0; i < contentB.size(); i++) {
            armA.get(i).setAlleleContent(contentB.get(i));
        }
    }
}
