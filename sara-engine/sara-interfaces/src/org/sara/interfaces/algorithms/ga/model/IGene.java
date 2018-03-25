package org.sara.interfaces.algorithms.ga.model;

public interface IGene extends Cloneable {

    public Object getAllele(boolean clone);

    public Object getAlleleContent(boolean clone);

    public boolean setAlleleContent(Object value);

    public Object clone();
}
