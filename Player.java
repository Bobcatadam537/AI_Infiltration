package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

class Player extends Character {
	// does not utilize the velocity or angle how the A.I. classes do.
	// movement will be translated into angle/velocity, not controlled by it.

	boolean up, down, left, right, space;
	int inv;
	int shootSpeed;
	Sword s;
	ArrayList<Integer> powerups = new ArrayList<>();
	public Player(int x, int y, int w, int h, Game g) {
		super(x, y, w, h, g);
		up = down = left = right = space = false;
		angle = 0;
		vx = vy = 0;
		shootSpeed = 20;

		s = new Sword(x, y, 16, w, this);
		melee = new Line2D.Double(hitbox.getCenterX(), hitbox.getCenterY(), hitbox.getCenterX(), hitbox.getCenterY());
		health = 50;
		inv = 0;
		sprites = new Image[32][5];

		try {
			sprites = Game.generateSprites(sprites,
					ImageIO.read(getClass().getResourceAsStream("/game/spriteSheets/test.png")), 16, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void render(Graphics2D g) {
		g.setColor(new Color(255, 255, 255));
		g.drawImage(sprites[d.dir][a.val], (int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getX() + W,
				(int) hitbox.getY() + H, 0, 0, 16, 20, null);
		g.fill(new Rectangle2D.Float((int)hitbox.getX(), (int)hitbox.getY()-5 , health/2, 3));

		if (a == action.melee) {
			s.render(g);
			g.setColor(Color.white);
			g.draw(melee);
		}
		getDirection();
		tick();
	}

	public void tick() {
		action();

		if (up && !down)
			vy = -speed;
		else if (!up && down)
			vy = speed;
		else
			vy = 0;

		if (left && !right)
			vx = -speed;
		else if (!left && right)
			vx = speed;
		else
			vx = 0;

		if (vx != 0)
			vy = vy / Math.sqrt(2);
		if (vy != 0)
			vx = vx / Math.sqrt(2);

		trigMove();

		if (!(vy == vx && vy == 0))
			angle = Math.atan2(vy, vx);

		if (health <= 0) {

			game.state = GameState.GAME_OVER;

		}
	}

	public void action() {
		if (space)
			if (w == weapon.gun)
				shoot(shootSpeed);
			else if (w == weapon.sword)
				melee(10);

		if (cooldown > 0) {
			cooldown--;
			if (a == action.melee) {
				double newAngle = angle + Math.toRadians(45) - Math.toRadians(cooldown * 9);
				melee = new Line2D.Double(hitbox.getCenterX(), hitbox.getCenterY(),
						hitbox.getCenterX() + 20 * Math.cos(newAngle), hitbox.getCenterY() + 20 * Math.sin(newAngle));
			}
		} else {
			a = action.none;
		}
	}

	public void damage(int val) {
		if (inv == 0) {
			health -= val;
			inv = 1;
		} else
			inv = 0;
	}
}