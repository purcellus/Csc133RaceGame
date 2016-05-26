package a4.observer;

import java.util.Observer;

public interface Observable
{//observer design.  to Be used in the future
	public void addObserver(Observer obs);
	public void notifyObservers();

}
