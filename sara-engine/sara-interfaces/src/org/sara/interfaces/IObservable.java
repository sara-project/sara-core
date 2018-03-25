package org.sara.interfaces;

public interface IObservable {

    public void registerObserver(IObserver obj);

    public void removeObserver(IObserver obj);

    public void notifyAllObserver();

    public void notifyObeserver(IObserver obj);
}
