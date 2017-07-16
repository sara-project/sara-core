package org.sara.interfaces;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public abstract class IProjectController implements IObservable {

    public IProjectController() {
        observers = new ArrayList<>();
    }

    public abstract void addNameActivePlugin(String name);

    public abstract List<String> allActivePlugin();

    public abstract List<IPlugin> pluginsByType(Object type);

    public abstract void setDataJars(URLClassLoader dataJars);

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

    protected List<IObserver> observers;
}
