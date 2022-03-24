package game;

import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Character extends GameObject {
	enum DIRECTION {
		up(0), upright(4), right(8), downright(12), down(16), downleft(20), left(24), upleft(28);

		public int val;

		DIRECTION(int i) {
			val = i;
		}
	}

	enum ACTION {
		none(0), shoot(1), melee(2), dodge(3), hurt(4);

		public int val;

		ACTION(int i) {
			val = i;
		}
	}

	enum WEAPON {
		gun(1), sword(2);

		public int val;

		WEAPON(int i) {
			val = i;
		}
	}

	double vx, vy;
	double speed = 0.5;
	int health;
	int cooldown;
	ACTION action;
	DIRECTION direction;
	WEAPON weapon;
	Game game;

	Image[][] sprites;

	public Character(int x, int y, int w, int h, Game g) {
		super(x, y, w, h);
		game = g;
		action = ACTION.none;
		direction = DIRECTION.up;
		weapon = WEAPON.gun;
		sprites = null;
	}

	public void trigMove() {
		trigMove(vx, vy);
	}

	public void trigMove(double mx, double my) {
		vx = mx;
		vy = my;
		// x direction
		x += vx;
		hitbox.setRect(x, y, w, h);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getX() == 1.0 || normal.getX() == -1.0) {
				x -= vx;
				hitbox.setRect(x, y, w, h);
			} else if (normal.getY() == 1.0 || normal.getY() == -1.0) {
			} else {
				x = x - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX());
				y = y - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY());
				hitbox.setRect(x, y, w, h);
			}
		}
		// y direction
		y += vy;
		hitbox.setRect(x, y, w, h);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getY() == 1.0 || normal.getY() == -1.0) {
				y -= vy;
				hitbox.setRect(x, y - vy, w, h);
			} else if (normal.getX() == 1.0 || normal.getX() == -1.0) {
			} else {
				x = x - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX());
				y = y - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY());
				hitbox.setRect(x, y, w, h);
			}
		}
	}

	public void shoot(int c) {
		if (cooldown == 0) {
			action = ACTION.shoot;
			game.bullets
					.add(new Bullet(this, (int) (hitbox.getCenterX() - 2.5), (int) (hitbox.getCenterY() - 2.5), angle));
			cooldown = c;
		}
	}

	public void shootMouse(int c, double angle) {
		if (cooldown == 0) {
			action = ACTION.shoot;
			game.bullets.add(new Bullet(this, (int) hitbox.getCenterX(), (int) hitbox.getCenterY(), angle));
			cooldown = c;
		}
	}

	public void melee(int c) {
		if (cooldown == 0) {
			action = ACTION.melee;
			cooldown = c;
		}
	}

	public void getDirection() {
		String temp = "";
		if (Math.toDegrees(angle) >= -90 - 60 && Math.toDegrees(angle) <= -90 + 60)
			temp += "up";
		if (Math.toDegrees(angle) >= 90 - 60 && Math.toDegrees(angle) <= 90 + 60)
			temp += "down";
		if (Math.toDegrees(angle) >= 0 - 60 && Math.toDegrees(angle) <= 0 + 60)
			temp += "right";
		if (Math.toDegrees(angle) >= 180 - 60 || Math.toDegrees(angle) <= -180 + 60)
			temp += "left";

		direction = DIRECTION.valueOf(temp);
	}

}
