package org.sara.sarageneticalgorithmsplugin.genes;

import org.sara.interfaces.algorithms.ga.genes.IGene;
import java.util.Random;
import org.sara.interfaces.ICore;

public class IFBAGene extends AbstractGene implements IGene {
    
    public IFBAGene() {
        this(new Random(IFBAGene.getMaxValue()).nextInt());
    }
     
    public IFBAGene(int schoolClassID) {
        super(new Integer(schoolClassID));
    }

    @Override
    public IGene getRandom() {
        return new IFBAGene(IFBAGene.getMaxValue());
    }

    @Override
    public String toString() {
        return this.getAllele().toString();
    }

    @Override
    public Object clone() {
        return new IFBAGene(((Integer) this.getAllele()));
    }

    public static int getMaxValue() {
        return ICore.getInstance().getModelController().getSchoolClass().size();
    }
}
