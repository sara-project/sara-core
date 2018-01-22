package org.sara.sarageneticalgorithmsplugin.chromosome;

import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.sarageneticalgorithmsplugin.genes.IntegerGene;

public class IntegerChromosome extends AbstractChromosome{
    
    public IntegerChromosome(int size) {
        this(size, Integer.MAX_VALUE);
        this.isValid = true;
    }
    
    public IntegerChromosome(int size, int rangeValue) {
        super(size);
        this.setRangeValue(rangeValue);
    }

    @Override
    public IChromosome getRandom() {
        IntegerChromosome random = new IntegerChromosome(this.getSize(), this.getRangeValue());
        for(int i = 0; i < random.getSize(); i++)
            random.setGene(new IntegerGene(this.getRangeValue(),true), i);
        return random;        
    }

    @Override
     public  AbstractChromosome getNewInstance(){
        return new IntegerChromosome(this.getSize(), this.getRangeValue());
     }  

    public int getRangeValue() {
        return rangeValue;
    }

    public void setRangeValue(int rangeValue) {
        this.rangeValue = rangeValue;
    }
    
    @Override
    public void setValidation(boolean valor){
        this.isValid=valor;
    }
    
    @Override
    public boolean getValidation(){
        return isValid;
    }

    private int rangeValue;
    private boolean isValid;
}
