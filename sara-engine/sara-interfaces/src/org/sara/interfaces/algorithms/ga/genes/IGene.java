package org.sara.interfaces.algorithms.ga.genes;

public interface IGene extends Cloneable {

    public void setAllele(Object value);

    public Object getAllele();

    public IGene getRandom();

    public Object clone();
}
