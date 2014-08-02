package playground.controller;


import java.util.EventObject;

public class ChangeEvent extends EventObject{

	private static final long serialVersionUID = 8269672694693306567L;

	private final ChangeState state;

	public ChangeEvent(final Object source, final ChangeState state) {
		super(source);
		this.state = state;
	}

	public ChangeState getEventName() {
		return state;
	}
}
