package org.sara.interfaces;

public abstract class ICore {

    public static ICore getInstance() {
        return instance;
    }

    public abstract IUiController getUiController();

    public abstract IProjectController getProjectController();

    protected ICore() {
    }
    protected static ICore instance = null;
}
