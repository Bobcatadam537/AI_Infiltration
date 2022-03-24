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
		if (this.hitbox.intersects(game.p.hitbox)) {
			// Game.p.hitbox = new Rectangle2D.Double(game.p.hitbox.getX(),
			// game.p.hitbox.getY()+y, game.p.hitbox.getWidth(), game.p.hitbox.getHeight());
			game.p.reset(100, 100);
			game.enemies.removeAll(game.enemies);
			game.level.val++;
			if (game.level.val == 2) {
				game.buildLvl2();
				game.state = GameState.POWERUP1;
			} else if (game.level.val == 3) {
				game.buildLvl3();
				game.state = GameState.POWERUP2;
			}

			System.out.println("Level change");
		}

	}
}
