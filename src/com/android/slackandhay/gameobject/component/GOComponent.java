package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;


public abstract class GOComponent {
	
	// TODO public static?
	public static final String TAG = "GOComponent"; 
	
	protected final GOComponentType type;
	protected final GOComponentType[] requiredComponents;
	protected final GOGameObject parent;
	protected boolean active = true;
	
	public GOComponent(GOComponentType type, GOGameObject parent) {
		this(type, parent, new GOComponentType[0]);
	}
	
	public GOComponent(GOComponentType type, GOGameObject parent, GOComponentType[] requiredComponents) {
		super();
		this.type = type;
		this.parent = parent;
		this.requiredComponents = (requiredComponents == null) ? 
				new GOComponentType[0] : requiredComponents;
		if (!parentHasRequiredComponents()) {
			this.active = false;
		}
	}
	
	public GOComponentType getType() {
		return this.type;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void activate() {
		if (parentHasRequiredComponents()) {
			this.active = true;
		}
	}
	
	public void disactivate() {
		this.active = false;
	}
	
	/* ABSTRACT */
	
	public abstract void update(int dt);
	
	/* PRIVATE */
	
	private boolean parentHasRequiredComponents() {
		for (int i = 0; i < requiredComponents.length; i++) {
			if (requiredComponents[i] == this.type) {
				// silently ignore a requirement for the type being created
				continue;
			}
			if (!parent.hasComponentOfType(requiredComponents[i])) {
				return false;
			}
		}
		return true;
	}
}
