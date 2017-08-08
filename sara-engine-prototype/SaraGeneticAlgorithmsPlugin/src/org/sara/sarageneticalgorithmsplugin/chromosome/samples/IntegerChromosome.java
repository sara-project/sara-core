package org.sara.sarageneticalgorithmsplugin.chromosome.samples;

import org.sara.sarageneticalgorithmsplugin.chromosome.AbstractChromosome;
import org.sara.sarageneticalgorithmsplugin.genes.IntegerGene;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;

public class IntegerChromosome extends AbstractChromosome {

    private int rangeValue;
    private boolean isValid;

    public IntegerChromosome(int size) {
        this(size, Integer.MAX_VALUE);
        this.isValid = true;
    }

    public IntegerChromosome(int size, int rangeValue) {
        super(size);//crio o array de genes
        this.setRangeValue(rangeValue);//o valor maximo dos numeros que será gerado aleatoriamente
    }

    @Override
    public IChromosome getRandom() {//geracao aleatoria dos genes do cromossomo
        IntegerChromosome random = new IntegerChromosome(this.size(), this.getRangeValue());
        for (int i = 0; i < random.size(); i++) {
            random.setGene(new IntegerGene(this.getRangeValue(), true), i);//geneIF, o indice do array de gene que o gene vai ser inserido
        }
        return random;
    }

    @Override
    public AbstractChromosome getNewInstance() {
        return new IntegerChromosome(this.size(), this.getRangeValue());
    }

    public int getRangeValue() {
        return rangeValue;
    }

    public void setRangeValue(int rangeValue) {
        this.rangeValue = rangeValue;
    }

    @Override
    public void setValidation(boolean valor) {
        this.isValid = valor;
    }

    @Override
    public boolean getValidation() {
        return isValid;
    }
}
