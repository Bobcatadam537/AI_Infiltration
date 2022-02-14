package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Bullet extends Weapon {
	double xv, yv;
	double speed = 5;
	Character shooter;

	public Bullet(int x, int y, double angle) {
		super(x, y, 5, 5);
		yv = speed * Math.sin(angle);
		xv = speed * Math.cos(angle);
	}

	public Bullet(Character s, int x, int y, double angle) {
		super(x, y, 5, 5);
		yv = speed * Math.sin(angle);
		xv = speed * Math.cos(angle);
		shooter = s;
	}

	public void reset(int x, int y) {
		hitbox.setRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fill(hitbox);
		tick();
	}

	public void tick() {
		hitbox = new Rectangle2D.Double(hitbox.getX() + xv+ Game.scrollX-1, hitbox.getY() + yv + Game.scrollY-1, hitbox.getWidth(), hitbox.getHeight());
	}
}