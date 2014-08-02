package playground.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JPanel;

import playground.model.Model;
import playground.model.Player;
import playground.model.PlayerState;
import playground.view.ViewComponent;

public class Controller implements Runnable, KeyListener {

	private Model _model;
	private ViewComponent _view;
	private Set<Integer> _pressedKeys;

	public Controller() {
		this._model = new Model();
		this._view = new ViewComponent(_model);
		_pressedKeys = new TreeSet<Integer>();
		_view.addKeyListener(this);
		_view.setFocusable(true);
	}

	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		double startTime = System.currentTimeMillis();
		while (true) {			
		
			/** 62.5 fps **/
			sleep(16);
			
			/** synch time with frames **/
			double runTime = System.currentTimeMillis() - startTime;
			if (runTime > 1000/5) {
				nextFrame();
				startTime = System.currentTimeMillis();
			}

			/** player moves **/
			movePlayer();

			/** repaint **/
			_view.repaint();
		}
	}

	private void nextFrame() {
		Player player = _model.getPlayer();
		player.nextFrame();
	}

	protected void movePlayer() {
		Player player = _model.getPlayer();
		int speed = player.speed;
		PlayerState state = PlayerState.IDLE_FRONT;
		boolean shiftIsPressed = _pressedKeys.contains(KeyEvent.VK_SHIFT);
		speed = shiftIsPressed ? speed+3 : speed;
		if (_pressedKeys.contains(KeyEvent.VK_RIGHT)) {
			state = shiftIsPressed ? PlayerState.RUN_RIGHT : PlayerState.WALK_RIGHT;
			player.moveRel(speed, 0);
		}
		if (_pressedKeys.contains(KeyEvent.VK_LEFT)) {
			state = shiftIsPressed ? PlayerState.RUN_LEFT : PlayerState.WALK_LEFT;
			player.moveRel(-speed, 0);
		}
		if (_pressedKeys.contains(KeyEvent.VK_UP)) {
			state = shiftIsPressed ? PlayerState.RUN_BACK : PlayerState.WALK_BACK;
			player.moveRel(0, -speed);
		}
		if (_pressedKeys.contains(KeyEvent.VK_DOWN)) {
			state = shiftIsPressed ? PlayerState.RUN_FRONT : PlayerState.WALK_FRONT;
			player.moveRel(0, speed);
		}
		if (_pressedKeys.contains(KeyEvent.VK_SPACE)) {
			// do nothing yet
		}
		player.setState(state);
	}
	
	public JPanel getView() {
		return _view;
	}

	public void start() {
		final Thread thread = new Thread(this);
		thread.start();
	}

	public void sleep(final int time) {
		try {
			Thread.sleep(time);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	}

	@Override
	public void keyTyped(final KeyEvent e) {
		// ignore
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			_pressedKeys.add(KeyEvent.VK_RIGHT);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			_pressedKeys.add(KeyEvent.VK_LEFT);
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			_pressedKeys.add(KeyEvent.VK_UP);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			_pressedKeys.add(KeyEvent.VK_DOWN);
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			_pressedKeys.add(KeyEvent.VK_SPACE);
		} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			_pressedKeys.add(KeyEvent.VK_SHIFT);
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			_pressedKeys.remove(KeyEvent.VK_RIGHT);
			_model.getPlayer().setState(PlayerState.IDLE_FRONT);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			_pressedKeys.remove(KeyEvent.VK_LEFT);
			_model.getPlayer().setState(PlayerState.IDLE_FRONT);
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			_pressedKeys.remove(KeyEvent.VK_UP);
			_model.getPlayer().setState(PlayerState.IDLE_FRONT);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			_pressedKeys.remove(KeyEvent.VK_DOWN);
			_model.getPlayer().setState(PlayerState.IDLE_FRONT);
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			_pressedKeys.remove(KeyEvent.VK_SPACE);
			_model.getPlayer().setState(PlayerState.IDLE_FRONT);
		} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			_pressedKeys.remove(KeyEvent.VK_SHIFT);
			_model.getPlayer().setState(PlayerState.IDLE_FRONT);
		}
	}
}
