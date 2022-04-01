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
		//System.out.println(Xshifted + ", " + Yshifted);
		tick();
	}

	public void tick() {
		if(cooldown == 0) {
		if (this.hitbox.intersects(game.p.hitbox)) {
			// Game.p.hitbox = new Rectangle2D.Double(game.p.hitbox.getX(),
			// game.p.hitbox.getY()+y, game.p.hitbox.getWidth(), game.p.hitbox.getHeight());
			System.out.println(game.level.toString());
			game.p.reset(100, 100);
			//game.enemies.removeAll(game.enemies);
			game.level.val++;
			if (game.level.val == 2) {
				//game.buildLvl2();
				game.level = Level.TWO;
				game.state = GameState.POWERUP1;
				System.out.println(game.level.toString());
				game.buildLvl2();
				//game.p.reset(1, cooldown);
			} else if (game.level.val == 3) {
				game.buildLvl3();
				game.state = GameState.POWERUP2;
			}
			else {System.out.println("BROKENNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");}
			System.out.println("Level change");
			cooldown = 100;
			System.out.println(game.level.toString());

		}}else cooldown--;

	}
}
