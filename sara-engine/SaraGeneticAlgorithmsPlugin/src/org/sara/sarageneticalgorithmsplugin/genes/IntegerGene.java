package org.sara.sarageneticalgorithmsplugin.genes;

import org.sara.interfaces.algorithms.ga.genes.IGene;
import java.util.Random;

public class IntegerGene extends AbstractGene implements IGene {

    public IntegerGene(int value) {
        super(new Integer(value));
        this.setMaxValue(Integer.MAX_VALUE);
    }

    public IntegerGene() {
        this(new Integer(((new Random()).nextInt())));
    }

    public IntegerGene(int rangeValue, boolean withRangeValue) {
        super(new Integer(((new Random()).nextInt(rangeValue))));
        this.setMaxValue(rangeValue);
    }

    @Override
    public IGene getRandom() {
        return new IntegerGene(this.getMaxValue(), true);
    }

    @Override
    public String toString() {
        return this.getAllele().toString();
    }

    @Override
    public Object clone() {
        return new IntegerGene(((Integer) this.getAllele()));
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    private int maxValue;
}
