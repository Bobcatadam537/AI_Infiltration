package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Bullet extends Weapon {
	double xv, yv;
	double speed = 5;
	Character shooter;
	int damage;
	int width;
	int height;
	public Bullet(int x, int y, double angle) {
		super(x, y, 5, 5);
		yv = speed * Math.sin(angle);
		xv = speed * Math.cos(angle);
		damage = 20;
		width = height = 5;
	}

	public Bullet(Character s, int x, int y, double angle) {
		super(x, y, 5, 5);
		yv = speed * Math.sin(angle);
		xv = speed * Math.cos(angle);
		shooter = s;
		damage = 20;
		width = height = 5;

	}

	public void reset(int x, int y) {
		hitbox.setRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		if(damage != 0) {
		g.fill(new Rectangle2D.Double((int) (hitbox.getX() + Game.scrollX), (int) (hitbox.getY() + Game.scrollY), width,
				height));
		tick();}
	}

	public void tick() {
		hitbox = new Rectangle2D.Double(hitbox.getX() + xv, hitbox.getY() + yv, hitbox.getWidth(), hitbox.getHeight());
	}
}