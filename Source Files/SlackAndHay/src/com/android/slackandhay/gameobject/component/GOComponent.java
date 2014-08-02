package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 * <p>
 * Components are what makes gameObjects functional: as the constituting parts
 * of their parent object, they can add properties, behavior or certain services
 * to it. Without components, a gameObject is nothing.
 * </p>
 * <p>
 * Each component has a ComponentType, which is one of a well-defined number of
 * such types; the ComponentType determines the exact kind of properties,
 * service and/or behavior that the component implements.
 * </p>
 * <p>
 * A component might realize different aspects of the simulated world, such as a
 * position in space, or a graphical representation; as such, the component can
 * be thought of as the hook to the particular game engine simulating this
 * aspect, for example a physics engine or the renderer. You could also say that
 * the component is the extension of that engine's interface into a game object,
 * making changing the engine as simple as swapping out the component of a given
 * type.
 * </p>
 * <p>
 * What all components have in common is the following:
 * <ul>
 * <li>a {@link GOComponentType type},</li>
 * <li>a parent {@link GOGameObject gameObject},</li>
 * <li>a list of other {@link GOComponentType types}, of which the parent must
 * have functioning components for this component to work,</li>
 * <li>the option to be <code>active</code> or <code>inactive</code>,</li>
 * <li>an {@link #update(int) update()} method (for regular housekeeping or
 * similar tasks).</li>
 * </ul>
 * </p>
 * 
 * @author til
 * 
 */
public abstract class GOComponent {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponent.class.getSimpleName();

	protected final GOComponentType type;
	protected final GOComponentType[] requiredComponents;
	protected final GOGameObject parent;
	protected boolean active = true;

	public GOComponent(final GOComponentType type, final GOGameObject parent) {
		this(type, parent, new GOComponentType[0]);
	}

	/**
	 * This constructor should not be called directly, instead inherit this
	 * class and call super()
	 * 
	 * @param type
	 *            the state type
	 * @param parent
	 *            the parent game object
	 * @param requiredComponents
	 *            the list of components which are required for this component
	 *            to work
	 */
	public GOComponent(final GOComponentType type, final GOGameObject parent, final GOComponentType[] requiredComponents) {
		super();
		this.type = type;
		this.parent = parent;
		this.requiredComponents = requiredComponents == null ? new GOComponentType[0] : requiredComponents;
		if (!parentHasRequiredComponents()) {
			active = false;
		}
	}

	/**
	 * 
	 * @return the component type
	 */
	public GOComponentType getType() {
		return type;
	}

	/**
	 * 
	 * @return if this component is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * activates this component, if all the required components are available
	 * 
	 * @return
	 * 		if the component is now active
	 */
	public boolean activate() {
		if (parentHasRequiredComponents()) {
			active = true;
		}
		return active;
	}

	/**
	 * deactivates this component
	 */
	public void deactivate() {
		active = false;
	}

	/* ABSTRACT */

	public abstract void update(int dt);

	/* PRIVATE */

	private boolean parentHasRequiredComponents() {
		for (int i = 0; i < requiredComponents.length; i++) {
			if (requiredComponents[i] == type) {
				// silently ignore a requirement for the type being created
				continue;
			}
			if (!parent.hasComponentOfType(requiredComponents[i]))
				return false;
		}
		return true;
	}
}
