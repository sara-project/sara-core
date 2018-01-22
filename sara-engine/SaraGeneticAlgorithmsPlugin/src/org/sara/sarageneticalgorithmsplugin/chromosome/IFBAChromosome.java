package org.sara.sarageneticalgorithmsplugin.chromosome;

import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.sarageneticalgorithmsplugin.flyweight.FlyIntFactory;

public class IFBAChromosome extends IntegerChromosome {

    public IFBAChromosome() {
        super(ICore.getInstance().getModelController().getSlots().size());
    }

    @Override
    public IChromosome getRandom() {
        IFBAChromosome random = new IFBAChromosome();
        for (int i = 0; i < random.getSize(); i++) {
            random.setGene(FlyIntFactory.getFlyIntFactory().getFlyIntegerWithRange(this.getRangeValue()), i);
        }
        return random;
    }
}
