package game;

import java.awt.Color;
//import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.Character.action;

public class Sword extends Weapon {
	int cooldown;
	Player player;
	boolean selected;
	boolean up, down, left, right;
	int dir;
	String direction;
	int length, width;

	public Sword(int x, int y, int l, int w, Player p) {
		super(x, y, 0, 0);
		selected = true;
		length = l;
		width = w;
		player = p;
		dir = 0;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		if (player.a == action.melee && player.cooldown > 1) {
			switch (player.d) {
			case down:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX() - width / 2, player.hitbox.getMaxY(), width,
						length);
				break;
			case downleft:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - width, player.hitbox.getMaxY(), width, length);
				break;
			case downright:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX(), player.hitbox.getMaxY(), width, length);
				break;
			case left:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - length, player.hitbox.getCenterY() - width / 2,
						length, width);
				break;
			case right:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX(), player.hitbox.getCenterY() - width / 2, length,
						width);
				break;
			case up:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX() - width / 2, player.hitbox.getY() - length,
						width, length);
				break;
			case upleft:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - width, player.hitbox.getY() - length, width,
						length);
				break;
			case upright:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX(), player.hitbox.getY() - length, width, length);
				break;
			default:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX(), player.hitbox.getCenterY(), 0, 0);
				break;
			}
		} else {
			hitbox = new Rectangle2D.Double(player.hitbox.getCenterX(), player.hitbox.getCenterY(), 0, 0);
		}
		g.fill(hitbox);

		tick();
	}

	public void tick() {
		for (Enemy e : player.game.enemies) {
			if (hitE(e)) {
				e.alive = false;
			}
		}
	}
}