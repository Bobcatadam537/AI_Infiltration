package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class Enemy extends Character {
	int type;
	boolean alive;
	boolean loaded = false;
	pNode[][] pathfinding;
	pNode nodePath;
	int pathfindingCooldown = 5;

	public Enemy(int x, int y, int w, int h, Game g) {
		super(x, y, w, h, g);
		type = 1;
		alive = true;
		cooldown = 0;
		vx = vy = 0;
		sprites = new Image[32][5];
		try {
			sprites = Game.generateSprites(sprites,
					ImageIO.read(getClass().getResourceAsStream("/game/spriteSheets/test.png")), 16, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pathfinding = null;
	}

	public Enemy(int x, int y, int w, int h, int t, Game g) {
		super(x, y, w, h, g);
		type = t;
		alive = true;
		vx = vy = 0;
		try {
			sprites = Game.generateSprites(sprites, ImageIO.read(getClass().getResource("/game/spriteSheets/Test.png")),
					16, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pathfinding = pNode.copy(game.walkable);
	}

	public void render(Graphics2D g) {
		if (alive && onScreen()) {
			g.drawImage(sprites[d.dir][a.val], (int) (hitbox.getX() + Game.scrollX),
					(int) (hitbox.getY() + Game.scrollY), W, H, null);
			g.setColor(Color.BLUE);
			loaded = true;
		}
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
			hitbox = new Rectangle2D.Double(hitbox.getWidth(), hitbox.getHeight(), Integer.MAX_VALUE,
					Integer.MAX_VALUE);
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
		if (pathfindingCooldown <= 0) {
			findPath();
			pathfindingCooldown = 10;
			System.out.println();
		}
		pathfindingCooldown--;
		if (nodePath != null) {
			aimAt(nodePath.maxParent(this));
		}
		trigMove();
		aimAt(game.p);
		attack();
		getDirection();
	}
}