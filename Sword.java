package game;

import java.awt.Color;
//import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.Character.ACTION;

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
		if (player.action == ACTION.melee && player.cooldown > 1) {
			switch (player.direction) {
			case down:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX() - width / 2 + Game.scrollX - 1,
						player.hitbox.getMaxY() + Game.scrollY - 1, width, length);
				break;
			case downleft:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - width + Game.scrollX - 1,
						player.hitbox.getMaxY() + Game.scrollY - 1, width, length);
				break;
			case downright:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX() + Game.scrollX - 1,
						player.hitbox.getMaxY() + Game.scrollY - 1, width, length);
				break;
			case left:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - length + Game.scrollX - 1,
						player.hitbox.getCenterY() - width / 2 + Game.scrollY - 1, length, width);
				break;
			case right:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX() + Game.scrollX - 1,
						player.hitbox.getCenterY() - width / 2 + Game.scrollY - 1, length, width);
				break;
			case up:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX() - width / 2 + Game.scrollX - 1,
						player.hitbox.getY() - length + Game.scrollY - 1, width, length);
				break;
			case upleft:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - width + Game.scrollX - 1,
						player.hitbox.getY() - length + Game.scrollY - 1, width, length);
				break;
			case upright:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX() + Game.scrollX - 1,
						player.hitbox.getY() - length + Game.scrollY - 1, width, length);
				break;
			default:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX() + Game.scrollX - 1,
						player.hitbox.getCenterY() + Game.scrollY - 1, 0, 0);
				break;
			}
		} else {
			hitbox = new Rectangle2D.Double(player.hitbox.getCenterX() + Game.scrollX - 1,
					player.hitbox.getCenterY() + Game.scrollY - 1, 0, 0);
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