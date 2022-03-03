package game;

import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
//import java.awt.event.*;


public class Character extends GameObject {
	enum direction {
		up(0), upright(4), right(8), downright(12), down(16), downleft(20), left(24), upleft(28);

		public int dir;

		direction(int i) {
			dir = i;
		}
	}

	enum action {
		none(0), shoot(1), melee(2), dodge(3), hurt(4);

		public int val;

		action(int i) {
			val = i;
		}
	}

	enum weapon {
		gun(1), sword(2);

		public int val;

		weapon(int i) {
			val = i;
		}
	}

	double vx, vy;
	int W, H;
	double speed = .5;
	int health;
	int cooldown;
	action a;
	direction d;
	weapon w;
	Line2D melee;
	Game game;
	
	//this.addMouseListener();
	Image[][] sprites;

	public Character(int X, int Y, int W, int H, Game g) {
		super(X, Y, W, H);
		this.W = W;
		this.H = H;
		game = g;
		a = action.none;
		d = direction.up;
		w = weapon.gun;
		melee = null;
		sprites = null;
		
	}

	public void trigMove() {
		// x direction
		hitbox.setRect(hitbox.getMinX() + vx, hitbox.getMinY(), W, H);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getX() == 1) {
				hitbox.setRect(hitbox.getMinX() - vx, hitbox.getMinY(), W, H);
			} else if (normal.getY() == -1.0) {
			} else {
				hitbox.setRect(hitbox.getMinX() - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX()),
						hitbox.getMinY() - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY()), W, H);
			}
		}
		// y direction
		hitbox.setRect(hitbox.getMinX(), hitbox.getMinY() + vy, W, H);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getY() == 1) {
				hitbox.setRect(hitbox.getMinX(), hitbox.getMinY() - vy, W, H);
			} else if (normal.getX() == 1.0) {
				// do nothing
			} else {
				hitbox.setRect(hitbox.getMinX() - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX()),
						hitbox.getMinY() - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY()), W, H);
			}
		}
		
		for(Laser l : game.lasers) {
			if(hitbox.intersects(l.hit1.hitbox)) {}
		}
		
	}

	public void trigMove(double mx, double my) {
		vx = mx;
		vy = my;
		// x direction
		hitbox.setRect(hitbox.getMinX() + vx, hitbox.getMinY(), W, H);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getX() == 1) {
				hitbox.setRect(hitbox.getMinX() - vx, hitbox.getMinY(), W, H);
			} else if (normal.getY() == -1.0) {
			} else {
				hitbox.setRect(hitbox.getMinX() - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX()),
						hitbox.getMinY() - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY()), W, H);
			}
		}
		// y direction
		hitbox.setRect(hitbox.getMinX(), hitbox.getMinY() + vy, W, H);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getY() == 1) {
				hitbox.setRect(hitbox.getMinX(), hitbox.getMinY() - vy, W, H);
			} else if (normal.getX() == 1.0) {
				// do nothing
			} else {
				hitbox.setRect(hitbox.getMinX() - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX()),
						hitbox.getMinY() - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY()), W, H);
			}
		}
	}

	public void shoot(int c) {
		if (cooldown == 0) {
			a = action.shoot;
			game.bullets.add(new Bullet(this, (int) hitbox.getCenterX(), (int) hitbox.getCenterY(), angle));
			cooldown = c;
		}
	}
	
	public void shootMouse(int c, double angle) {
		if (cooldown == 0) {
			a = action.shoot;
			game.bullets.add(new Bullet(this, (int) hitbox.getCenterX(), (int) hitbox.getCenterY(), angle));
			cooldown = c;
		}
	}
	
	
	public void melee(int c) {
		if (cooldown == 0) {
			a = action.melee;
			cooldown = c;
		}
	}

	public void getDirection() {
		String temp = "";
		if (Math.toDegrees(angle) >= -90 - 45 && Math.toDegrees(angle) <= -90 + 45)
			temp += "up";
		if (Math.toDegrees(angle) >= 90 - 45 && Math.toDegrees(angle) <= 90 + 45)
			temp += "down";
		if (Math.toDegrees(angle) >= 0 - 45 && Math.toDegrees(angle) <= 0 + 45)
			temp += "right";
		if (Math.toDegrees(angle) >= 180 - 45 || Math.toDegrees(angle) <= -180 + 45)
			temp += "left";

		d = direction.valueOf(temp);
	}

}