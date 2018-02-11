package org.sara.interfaces;

public abstract class ICore {

    public static ICore getInstance() {
        return instance;
    }

    public abstract IUiController getUiController();
    public abstract IProjectController getProjectController();
    public abstract IModelController getModelController();
    protected ICore() {}
    
    protected static ICore instance = null;
}
