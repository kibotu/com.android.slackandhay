package playground.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import playground.Config;
import playground.view.Drawable;

public class Player implements Drawable {

	public double x;
	public double y;
	public int speed;

	private PlayerState _state;
	private SpriteManager _spriteMgr;

	public Player() {
		x = Config.WINDOW_WIDTH / 2;
		y = Config.WINDOW_HEIGHT / 2;
		speed = Config.DEFAULT_PLAYER_SPEED;
		_state = PlayerState.IDLE_FRONT;
		_spriteMgr = new SpriteManager(_state);
	}

	public void moveRel(int dx, int dy) {
		int height = _spriteMgr.getCurrentSprite().getHeight();
		int width = _spriteMgr.getCurrentSprite().getWidth();
		if(x+dx > 0+width/2 && x+dx < Config.WINDOW_WIDTH-width/2) {
			x += dx;
		} 
		if(y+dy > 0+height/2 && y-dy < Config.WINDOW_HEIGHT-12 - height/2) {
			y += dy;
		}  
	}

	@Override
	public void draw(Graphics g) {
		BufferedImage image = _spriteMgr.getCurrentSprite();
		g.drawImage(image, (int)(x - image.getWidth() / 2), (int)(y - image.getHeight() / 2), null);
//		g.drawRect((int) (x - image.getWidth() / 2), (int) (y - image.getHeight() / 2), image.getWidth(), image.getHeight());
	}

	public void nextFrame() {
		_spriteMgr.nextFrame();
	}
	
	public void setState(PlayerState state) {
		_state = state;
		_spriteMgr.setState(state);
	}
}