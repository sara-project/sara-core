package org.sara.interfaces.algorithms.ga.model;

public interface IGene extends Cloneable {

    public Object getAllele();

    public Object getAlleleContent();

    public void setAlleleContent(Object value);

    public Object clone();
}
