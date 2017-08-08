package org.sara.sarageneticalgorithmsplugin.genes;

import org.sara.interfaces.algorithms.ga.genes.IGene;
import java.util.Random;

public class IntegerGene extends AbstractGene implements IGene{
    
    private int maxValue;
    
    /** Creates a new instance of IntegerGene */
    public IntegerGene(int value) {//Inicia um gene com um valor pre-estipulado
        super(new Integer(value));        
        this.setMaxValue(Integer.MAX_VALUE);
    }
    
    public IntegerGene() {//Inicia um gene aleatoriamente
        this(new Integer(((new Random()).nextInt())));
    }
    
    public IntegerGene(int rangeValue, boolean withRangeValue) {
        super(new Integer(((new Random()).nextInt(rangeValue))));
        this.setMaxValue(rangeValue);
    }
    

    public IGene getRandom() {
        return new IntegerGene(this.getMaxValue(), true);
    }
    
    public String toString(){
        return this.getAllele().toString();
    }
    
    public Object clone(){
        return new IntegerGene(((Integer)this.getAllele()).intValue());
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    
}
