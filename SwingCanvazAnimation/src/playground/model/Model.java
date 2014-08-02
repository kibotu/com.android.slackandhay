package playground.model;

import java.util.ArrayList;
import playground.view.Drawable;

public class Model {

	private ArrayList<Drawable> drawables;
	private Player _player;
	
	public Model() {
		drawables = new ArrayList<Drawable>();
		_player = new Player();
		drawables.add(_player);
	}
	
	public ArrayList<Drawable> getDrawables() {
		return drawables;
	}
	
	public Player getPlayer() {
		return _player;
	}
}