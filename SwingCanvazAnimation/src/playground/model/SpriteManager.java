package playground.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;

import playground.Config;

public class SpriteManager {

	private BufferedImage _spriteSheet;
	private PlayerState _state;
	private HashMap<PlayerState, BufferedImage[]> _sprites;
	private int _curFrame;

	public SpriteManager(PlayerState state) {
		_state = state;
		_curFrame = 0;
		_spriteSheet = getBufferedImage(Config.SPRITE_CHARACTER);
		_sprites = new HashMap<PlayerState, BufferedImage[]>();
		parseSprites();
	}

	private BufferedImage getSubImage(int x, int y, int width, int height) {
		return _spriteSheet.getSubimage(x, y, width, height);
	}

	public BufferedImage getBufferedImage(String filepath) {
		return Pictures.toBufferedImage(new ImageIcon(this.getClass().getClassLoader().getResource(filepath)).getImage());
	}

	public BufferedImage flip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}

	public BufferedImage getCurrentSprite() {
		return _sprites.get(_state)[_curFrame];
	}

	public int getSpriteFramesAmount() {
		return _sprites.get(_state).length;
	}

	public void nextFrame() {
		// _curFrame++;
		if (_curFrame < _sprites.get(_state).length - 1) {
			_curFrame++;
		} else {
			_curFrame = 0;
		}
	}

	public void setState(PlayerState state) {
		if (_curFrame > _sprites.get(state).length - 1) {
			_curFrame = 0;
		}
		_state = state;
	}

	private void parseSprites() {

		BufferedImage[] allSprites = new BufferedImage[] {
				// zwinkern
				getSubImage(16, 22, 32, 70), // 0
				getSubImage(56, 22, 32, 70), // 1
				getSubImage(96, 22, 32, 70), // 2
				// walking down
				getSubImage(170, 22, 36, 70), // 3
				getSubImage(214, 22, 32, 70), // 4
				getSubImage(256, 22, 34, 70), // 5
				getSubImage(296, 22, 36, 70), // 6
				getSubImage(336, 22, 32, 70), // 7
				getSubImage(376, 22, 34, 70), // 8
				// running down
				getSubImage(676, 18, 36, 70), // 9
				getSubImage(722, 18, 36, 70), // 10
				// umschauen
				getSubImage(976, 16, 32, 70), // 11
				getSubImage(1016, 16, 32, 70), // 12
				// walking up
				getSubImage(94, 694, 36, 70), // 13
				getSubImage(146, 690, 32, 70), // 14
				getSubImage(190, 696, 36, 70), // 15
				getSubImage(234, 694, 36, 70), // 16
				getSubImage(280, 690, 32, 70), // 17
				getSubImage(322, 696, 32, 70), // 18
				// running up
				getSubImage(602, 690, 34, 76), // 19
				getSubImage(644, 690, 34, 76), // 20
				// walking right
				getSubImage(154, 364, 30, 68), // 21
				getSubImage(202, 368, 42, 64), // 22
				getSubImage(256, 366, 28, 66), // 23
				getSubImage(300, 364, 28, 68), // 24
				getSubImage(344, 368, 44, 64), // 25
				getSubImage(402, 366, 28, 66), // 26
				// running right
				getSubImage(738, 374, 56, 64), // 27
				getSubImage(802, 374, 56, 64), // 28
				// walking left
				flip(getSubImage(154, 364, 30, 68)), // 29
				flip(getSubImage(202, 368, 42, 64)), // 30
				flip(getSubImage(256, 366, 28, 66)), // 31
				flip(getSubImage(300, 364, 28, 68)), // 32
				flip(getSubImage(344, 368, 44, 64)), // 33
				flip(getSubImage(402, 366, 28, 66)), // 34
				// running left
				flip(getSubImage(738, 374, 56, 64)), // 35
				flip(getSubImage(802, 374, 56, 64)), // 36
		};

		/** parsing walking down **/
		BufferedImage[] temp = new BufferedImage[] { 
				allSprites[3],
				allSprites[4], 
				allSprites[5], 
				allSprites[6], 
				allSprites[7],
				allSprites[8] 
		};
		_sprites.put(PlayerState.WALK_FRONT, temp);

		/** parsing walking right **/
		temp = new BufferedImage[] { 
				allSprites[21], 
				allSprites[22],
				allSprites[23], 
				allSprites[24], 
				allSprites[25], 
				allSprites[26] 
		};
		_sprites.put(PlayerState.WALK_RIGHT, temp);

		/** parsing walking up **/
		temp = new BufferedImage[] { 
				allSprites[13], 
				allSprites[14],
				allSprites[15], 
				allSprites[16], 
				allSprites[17], 
				allSprites[18] 
		};
		_sprites.put(PlayerState.WALK_BACK, temp);

		/** parsing walking left **/
		temp = new BufferedImage[] { 
				allSprites[29], 
				allSprites[30],
				allSprites[31], 
				allSprites[32], 
				allSprites[33], 
				allSprites[34] 
		};
		_sprites.put(PlayerState.WALK_LEFT, temp);

		/** parsing idle **/
		temp = new BufferedImage[] { 
				allSprites[0], 
				allSprites[0],
				allSprites[0], 
				allSprites[1], 
				allSprites[2], 
				allSprites[0],
				allSprites[0], 
				allSprites[0], 
				allSprites[0], 
				allSprites[0],
				allSprites[0], 
				allSprites[0], 
				allSprites[0], 
				allSprites[0],
				allSprites[0], 
				allSprites[0], 
				allSprites[0], 
				allSprites[1],
				allSprites[2], 
				allSprites[0], 
				allSprites[0], 
				allSprites[0],
				allSprites[0], 
				allSprites[0], 
				allSprites[0], 
				allSprites[0],
				allSprites[11], 
				allSprites[11], 
				allSprites[11], 
				allSprites[11],
				allSprites[0], 
				allSprites[0], 
				allSprites[0], 
				allSprites[0],
				allSprites[12], 
				allSprites[12], 
				allSprites[12], 
				allSprites[12], 
		};
		_sprites.put(PlayerState.IDLE_FRONT, temp);

		/** parsing blinking **/
		temp = new BufferedImage[] { 
				allSprites[0], 
				allSprites[1],
				allSprites[2] 
		};
		_sprites.put(PlayerState.BLINK_FRONT, temp);

		/** parsing running down **/
		temp = new BufferedImage[] { 
				allSprites[8], 
				allSprites[9] 
		};
		_sprites.put(PlayerState.RUN_FRONT, temp);

		/** parsing running up **/
		temp = new BufferedImage[] { 
				allSprites[19], 
				allSprites[20] 
		};
		_sprites.put(PlayerState.RUN_BACK, temp);

		/** parsing running right **/
		temp = new BufferedImage[] { 
				allSprites[21], 
				allSprites[27], 
				allSprites[28],
 	            allSprites[25],
		};
		_sprites.put(PlayerState.RUN_RIGHT, temp);

		/** parsing running left **/
		temp = new BufferedImage[] {
				allSprites[35],
				allSprites[34],
				allSprites[36],
				
		};
		_sprites.put(PlayerState.RUN_LEFT, temp);
	}
}