package org.sara.interfaces.abstractyfactory;

public abstract class IAbstractFactory {

    public static IAbstractFactory getInstance() {
        return instance;
    }

    protected IAbstractFactory() {
    }
    protected static IAbstractFactory instance = null;
}
