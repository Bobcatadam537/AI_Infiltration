package game;

import java.awt.Color;
//import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.Character.action;

public class Sword extends Weapon {
	int cooldown;
	Character c;
	boolean selected;
	boolean up, down, left, right;
	int dir;
	String direction;
	int length, width;
	int damage;
	public Sword(int x, int y, int l, int w, Player p) {
		super(x, y, 0, 0);
		cooldown = 0;
		selected = true;
		length = l;
		width = w;
		c = p;
		dir = 0;
		damage = 15;
	}

	public Sword(int x, int y, int l, int w, Enemy enemy) {
		super(x, y, 0, 0);
		cooldown = 0;
		selected = true;
		length = l;
		width = w;
		c = enemy;
		dir = 0;
		damage = 15;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		if (c.a == action.melee && c.cooldown > 1) {
			switch (c.d) {
			case down:
				hitbox = new Rectangle2D.Double(c.hitbox.getCenterX() - width / 2  , c.hitbox.getMaxY() , width,
						length);
				break;
			case downleft:
				hitbox = new Rectangle2D.Double(c.hitbox.getX() - width  , c.hitbox.getMaxY() , width, length);
				break;
			case downright:
				hitbox = new Rectangle2D.Double(c.hitbox.getMaxX() , c.hitbox.getMaxY() , width, length);
				break;
			case left:
				hitbox = new Rectangle2D.Double(c.hitbox.getX() - length , c.hitbox.getCenterY() - width / 2 ,
						length, width);
				break;
			case right:
				hitbox = new Rectangle2D.Double(c.hitbox.getMaxX() , c.hitbox.getCenterY() - width / 2 , length,
						width);
				break;
			case up:
				hitbox = new Rectangle2D.Double(c.hitbox.getCenterX() - width / 2 , c.hitbox.getY() - length ,
						width, length);
				break;
			case upleft:
				hitbox = new Rectangle2D.Double(c.hitbox.getX() - width , c.hitbox.getY() - length , width,
						length);
				break;
			case upright:
				hitbox = new Rectangle2D.Double(c.hitbox.getMaxX() , c.hitbox.getY() - length , width, length);
				break;
			default:
				hitbox = new Rectangle2D.Double(c.hitbox.getCenterX() , c.hitbox.getCenterY() , 0, 0);
				break;
			}
		} else {
			//hitbox = new Rectangle2D.Double(c.hitbox.getCenterX() , c.hitbox.getCenterY() , 0, 0);
			hitbox = new Rectangle2D.Double(0,0, 0, 0);

		}
		g.fill(new Rectangle2D.Double((int) (hitbox.getX() + Game.scrollX), (int) (hitbox.getY() + Game.scrollY), width, length));
		
		tick();
	}

	public void tick() {
		for (Enemy e : c.game.enemies) {
			if (hitE(e)) {
				if(cooldown == 0) {
				e.takeDamage(damage);
				cooldown = 10;
				}
				else cooldown --;
				
			}
		}
		if(hitP(c.game.p)) {
			if(cooldown == 0) {
				c.game.p.damage(20);
				cooldown = 20;
				}
				else cooldown --;
			
		}
		
	}
}