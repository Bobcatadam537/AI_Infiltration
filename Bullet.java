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
		damage = 20;
	}

	public Bullet(Character s, int x, int y, double angle) {
		this(x, y, angle);
		shooter = s;
	}

	public void reset(int x, int y) {
		hitbox.setRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}

	public void render(Graphics2D g) {
		updateRenderPosition();
		g.setColor(Color.WHITE);
		if (damage != 0)
			g.fill(new Rectangle2D.Double(Xshifted, Yshifted, 5, 5));
		tick();
	}

	public void tick() {
		x += xv;
		y += yv;
		hitbox = new Rectangle2D.Double(x, y, w, h);
	}
}