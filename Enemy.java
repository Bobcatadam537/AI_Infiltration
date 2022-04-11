package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import game.Effect.effectType;

public class Enemy extends Character {
	int type;
	Sword s;
	boolean alive;
	boolean loaded = false;
	pNode[][] pathfinding;
	pNode nodePath;
	int pathfindingCooldown = 5;
	double ani = 0;
	int health;

	public Enemy(int x, int y, int w, int h, Game g) {
		super(x, y, w, h, g);
		type = 1;
		alive = true;
		cooldown = 0;
		vx = vy = 0;
		sprites = new Image[32][5];
		try {
			sprites = Game.generateSprites(sprites, "/game/spriteSheets/test.png", 16, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pathfinding = null;
	}

	public Enemy(int x, int y, int w, int h, int t, Game g) {
		this(x, y, w, h, g);
		type = t;
	}

	public void render(Graphics2D g) {
		updateRenderPosition();
		if (alive && onScreen()) {
			System.out.println(hitbox.getX() + ", " + hitbox.getY());
			Game.drawImage(sprites[direction.val + ((int) ani)][action.val], Xshifted, Yshifted, w, h, g);
			g.setColor(Color.BLUE);
			loaded = true;
		}
		g.fill(new Rectangle2D.Float((int) (hitbox.getX() + Game.scrollX - 1), (int) (hitbox.getY() + Game.scrollY - 1),
				health / 2, 3));
		// if (nodePath != null)
		// nodePath.render(g);
		if (type == 3)
			System.out.println("RENDER SWORD");
		if (loaded)
			tick();
		damage();

	}

	public void damage() {

//		if (game.p.s.hitE(this)) {
//			alive = false;
//		}
		if (this.hitbox.intersects(game.p.hitbox)) {
			game.p.damage(1);
			if (game.p.hitbox.getCenterX() < hitbox.getCenterX()) {
				game.p.trigMove(-5, 0);
			} else {
				game.p.trigMove(5, 0);
			}
			if (game.p.hitbox.getCenterY() < hitbox.getCenterY()) {
				game.p.trigMove(0, -5);
			} else {
				game.p.trigMove(0, 5);
			}
		}
		if (!alive) {
			hitbox = new Rectangle2D.Double(0, 0, 0, 0);
		}
	}

	public void die() {
		if (alive) {
			game.effects.add(new Effect(hitbox, effectType.explosion));
			alive = false;
		}
	}

	public void takeDamage(int damage) {
		health -= damage;
		if (health < 0) {
			alive = false;
			game.effects.add(new Effect(hitbox, effectType.explosion));
		}
	}

	public void attack() {
		shoot(30);
		cooldown--;
	}

	public void trigMove() {
		vy = Math.sin(angle);
		vx = Math.cos(angle);
		super.trigMove(vx, vy);
	}

	public void loadPathfinding() {
		pathfinding = pNode.copy(game.walkable);
	}

	public void findPath() {
		if (pathfinding == null)
			loadPathfinding();

		pNode current = pathfinding[(int) (hitbox.getCenterX() / Game.pNodeSize)][(int) (hitbox.getCenterY()
				/ Game.pNodeSize)];
		current.parent = null;
		boolean found = false;

		Set<pNode> open = new HashSet<pNode>();
		Set<pNode> closed = new HashSet<pNode>();
		open.add(current);

		while (!found) {
			current = Collections.min(open);
			open.remove(current);
			closed.add(current);

			if (game.p.hitbox.intersects(new Rectangle2D.Double(current.x * Game.pNodeSize, current.y * Game.pNodeSize,
					Game.pNodeSize, Game.pNodeSize))) {
				nodePath = current;
				return;
			}

			for (pNode neighbor : current.neighbors) {
				if (!neighbor.open || closed.contains(neighbor)) {
					// skip
				} else if (neighbor.cost > current.cost || !open.contains(neighbor)) {
					if (neighbor.x == current.x || neighbor.y == current.y)
						neighbor.cost = current.cost + 10;
					else
						neighbor.cost = current.cost + 14;
					neighbor.parent = current;
					current.child = neighbor;
					if (!open.contains(neighbor))
						open.add(neighbor);
				}
			}
		}
	}

	public void tick() {
		// pathfinding stuff
		if (pathfindingCooldown <= 0) {
			findPath();
			pathfindingCooldown = 10;
			System.out.println();
		}
		pathfindingCooldown--;
		if (nodePath != null) {
			aimAt(nodePath.maxParent(this));
		}
		// animation stuff
		if (ani + 0.1 > 4)
			ani = 0;
		else
			ani += 0.1;
		// attacking and moving stuff
		trigMove();
		aimAt(game.p);
		attack();
		getDirection();
	}
}