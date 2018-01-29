package org.sara.interfaces;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import org.sara.interfaces.algorithms.ga.IGAEngine;

public abstract class IProjectController implements IObservable {

    public IProjectController() {
        AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
        OS_NAME = System.getProperty("os.name");
        observers = new ArrayList<>();
        nameActivePlugins = new ArrayList<>();
    }

    public abstract void addNameActivePlugin(String name);

    public abstract List<String> allActivePlugin();

    public abstract List<IPlugin> pluginsByType(Object type);

    public abstract void setDataJars(URLClassLoader dataJars);
    
    public IGAEngine getGAEngine() {
        if(currentGAEngine == null) {
           System.out.println("No genetic algorithm engine was added");
           System.exit(1);
        }
        return currentGAEngine;
    }
    
    public void setGAEngine(IGAEngine engine) {
        this.currentGAEngine = engine;
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
        System.out.println("System: observer  " + observer.getClass().getSimpleName()
                + " was registered for " + observer.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(IObserver obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyAllObserver() {
        for (IObserver ob : observers) {
            //ob.update(/**/);
        }
    }

    public final int AVAILABLE_PROCESSORS;
    public final String OS_NAME;
    protected List<IObserver> observers;
    protected List<String> nameActivePlugins;
    protected IGAEngine currentGAEngine;
}
