package game;

//import java.awt.geom.Point2D;
//import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

public class Door extends GameObject {
	FontMetrics metrics;
	Font font;
	Game game;
	int level;
	Image sprite;
	int cooldown = 0;

	public Door(int x, int y, int w, int h, Game g, int lvl) {
		super(x, y, w, h);
		level = lvl;
		game = g;
		font = new Font("courier new", 1, 50);
		try {
			sprite = Game.generateSprite("/game/spriteSheets/Door.png");
		} catch (Exception e) {
			e.printStackTrace();
			sprite = null;
		}
	}

	public void render(Graphics2D g) {
		updateRenderPosition();
		Game.drawImage(sprite, Xshifted - 1, Yshifted - 1, w, h, g);
		tick();
	}

	public void tick() {
		if (cooldown == 0) {
			if (this.hitbox.intersects(game.p.hitbox)) {
				switch (++game.level) {
				case 2:
					game.level = 2;
					game.state = GameState.POWERUP1;
					game.buildLvl2();
					break;
				case 3:
					game.buildLvl3();
					game.state = GameState.POWERUP2;
					break;
				default:
					System.out.println("DATA FOR LEVEL " + game.level + " NOT FOUND");
					break;
				}

				System.out.println("Level change");
				cooldown = 100;

			}
		} else
			cooldown--;

	}
}