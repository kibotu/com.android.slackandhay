package playground.controller;

public interface ChangeEventObserver {
	public void addChangeEventListener(ChangeEventListener listener);
	public void removeChangeEventListener(ChangeEventListener listener);
}
