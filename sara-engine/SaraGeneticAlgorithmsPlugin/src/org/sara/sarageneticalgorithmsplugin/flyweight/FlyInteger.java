package org.sara.sarageneticalgorithmsplugin.flyweight;

import org.sara.interfaces.algorithms.ga.genes.IGene;
import org.sara.sarageneticalgorithmsplugin.genes.IntegerGene;

public class FlyInteger extends IntegerGene implements IGene{

    @Override
    public Object clone(){
        return this;
    }

    public FlyInteger() {
    	super();
    }
    
    public FlyInteger(int value) {
    	super(value);
    }    
    
    public FlyInteger(int rangeValue, boolean withRangeValue) {
        super(rangeValue, withRangeValue);
    }    
    
    @Override
    public IGene getRandom() {
        return FlyIntFactory.getFlyIntFactory().getFlyIntegerWithRange(this.getMaxValue());
    }
}
