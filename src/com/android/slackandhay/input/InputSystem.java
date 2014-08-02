package com.android.slackandhay.input;

import android.graphics.Point;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import com.android.slackandhay.gameobject.component.GOComponentInput;

import java.util.HashMap;

/**
 * this class represents the input system in which all inputs (e.g. touch
 * screens, d pads, keyboards and so on) are gathered and interpreted
 * 
 * also all the input bindings are placed here
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public class InputSystem implements OnTouchListener, OnKeyListener, OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = InputSystem.class.getSimpleName();

	HashMap<InputAction.Action, InputAction> inputActionTable = new HashMap<InputAction.Action, InputAction>();

	private final InputTouch inputTouch = new InputTouch();
	private static InputSystem instance;
	// As you might have noticed, i don't care about keyboards.
	private final InputKeyboard inputKeyboard = new InputKeyboard();

	private InputSystem() {

	}

	public void bind(InputAction.Action actiontype, int keycode, int touchbutton, GOComponentInput component) {
		inputActionTable.put(actiontype, new InputAction(actiontype, component, keycode, touchbutton));
	}

	public static InputSystem getInstance() {
		if (instance == null) {
			instance = new InputSystem();
		}
		return instance;
	}

	public void setResolution(final int screenResolutionX, final int screenResolutionY) {
		inputTouch.setScreenResolution(screenResolutionX, screenResolutionY);
	}

	@Override
	public boolean onTouch(final View v, final MotionEvent event) {
		final boolean touchHandled = inputTouch.onTouch(event);
		// Log.d(TAG,
		// "[Touch left] x:"+inputTouch.getLeftDelta().x+" y:"+inputTouch.getLeftDelta().y
		// );
		// Log.d(TAG,
		// "[Touch right] x:"+inputTouch.getRightDelta().x+" y:"+inputTouch.getRightDelta().y
		// );
		return touchHandled;
	}

	@Override
	public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
		// DIE KEYBOARD, DIE!
		return inputKeyboard.onKey(event);

	}

	@Override
	public void onClick(final View arg0) {
		// TODO Create class to cope with mouse input... maybe.
	}

	public Point getMapOffsetPoint() {
		return inputTouch.getLeftDelta();
	}

	public boolean buttonWasPressed(final int buttonnr) {
		return inputTouch.getButtonWasPressed(buttonnr);
	}

	/**
	 * Returns the offset of the finger position since the last time it was
	 * requested: please note: once it has been requested the values will be
	 * lost, so save them!
	 * 
	 * @return a point containing a offset.
	 */
	public Point getLeftTouchOffset() {
		return inputTouch.getLastLeftDelta();
	}

	/**
	 * Returns the offset of the finger position since the last time it was
	 * requested: please note: once it has been requested the values will be
	 * lost, so save them!
	 * 
	 * @return a point containing a offset.
	 */
	public Point getRightTouchOffset() {
		return inputTouch.getLastRightDelta();
	}
}
