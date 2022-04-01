package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

class Player extends Character {
	boolean up, down, left, right, space;
	final static int width = 12, height = 20;
	int inv;
	int shootSpeed;
	double ani = 0;
	Sword s;
	ArrayList<Integer> powerups = new ArrayList<>();
	MouseInput m = new MouseInput(this);
	int mouseX, mouseY;
	double shootAngle;

	public Player(int x, int y, Game g) {
		super(x, y, width, height, g);
		up = down = left = right = space = false;
		angle = 0;
		vx = vy = 0;
		shootSpeed = 20;

		s = new Sword(x, y, width, width, this);
		health = 50;
		inv = 0;
		// Generates the sprite array
		sprites = new Image[32][5];
		try {
			sprites = Game.generateSprites(sprites, "/game/spriteSheets/PlayerSprites.png", width + 2, height);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void render(Graphics2D g) {
		updateRenderPosition();
		Game.drawImage(sprites[direction.val + ((int) ani)][action.val], Xshifted - 1, Yshifted, w + 2, h, g);
		g.fill(new Rectangle2D.Double(Xshifted - 1, Yshifted - 1, health / 2, 3));

		if (action == ACTION.melee) {
			s.render(g);
			g.setColor(Color.white);
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

		trigMove(vx, vy);
		trigMove(vx, vy);
		trigMove(vx, vy);
		trigMove(vx, vy);

		if (!(vy == vx && vy == 0)) {
			angle = Math.atan2(vy, vx);
			if (ani + 0.1 > 4)
				ani = 0;
			else
				ani += 0.1;
		} else {
			ani = 0;
		}

		if (health <= 0) {

			game.state = GameState.GAME_OVER;

		}
	}

	public double findShootAngle() {
		shootAngle = Math.tan((mouseX - hitbox.getCenterY()) / (mouseY - hitbox.getCenterY()));
		return shootAngle;
	}

	public void action() {
		if (space)
			if (weapon == WEAPON.gun)
				shootMouse(shootSpeed, m.findAngle());
			else if (weapon == WEAPON.sword)
				melee(10);

		if (cooldown > 0) {
			cooldown--;
		} else {
			action = ACTION.none;
		}
	}

	public void reset(int x, int y) {
		this.x = x;
		this.y = y;
		health = 50;
	}

	public void damage(int val) {
		if (inv == 0) {
			health -= val;
			inv = 1;
		} else
			inv = 0;
	}
}

class MouseInput extends MouseAdapter {
	double mx;
	double my;
	double angle;
	double cx, cy;
	// Game game;
	Player p;

	public MouseInput(Player player) {
		p = player;
		cx = 0;
		cy = 0;
	}

	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();

		p.space = true;

		// p.shootMouse(10, findAngle());
	}

	public double findAngle() {
		cx = (Game.screen.getWidth() * (p.game.scaleX)) / 2;
		cy = (Game.screen.getHeight() * (p.game.scaleY)) / 2;

		angle = Math.atan2(my - cy, mx - cx);

		// System.out.println(angle * (180/Math.PI));
		return angle;
	}

	public void mouseReleased(MouseEvent e) {
		p.space = false;
	}

}
