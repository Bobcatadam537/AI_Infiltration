package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;
import javax.imageio.ImageIO;
//import java.awt.geom.Point2D;


class Player extends Character {
	// does not utilize the velocity or angle how the A.I. classes do.
	// movement will be translated into angle/velocity, not controlled by it.

	boolean up, down, left, right, space;
	int inv;
	int shootSpeed;
	//double cx, cy;
	
	Sword s;
	ArrayList<Integer> powerups = new ArrayList<>();
	MouseInput m = new MouseInput(this);
	int mouseX, mouseY;
	double shootAngle;
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
		g.drawImage(sprites[d.dir][a.val], (int) (hitbox.getX() + Game.scrollX), (int) (hitbox.getY() + Game.scrollY),
				(int) (hitbox.getX() + Game.scrollX - 1) + W, (int) (hitbox.getY() + Game.scrollY - 1) + H, 0, 0, 16,
				20, null);
		g.fill(new Rectangle2D.Float((int) (hitbox.getX() + Game.scrollX - 1), (int) (hitbox.getY() + Game.scrollY - 1),
				health / 2, 3));

		if (a == action.melee) {
			s.render(g);
			g.setColor(Color.white);
			g.draw(melee);
		}
//		g.draw(new Line2D.Double(new Point2D.Double(hitbox.getCenterX() + Game.scrollX-1,hitbox.getCenterY()+Game.scrollY-1), new Point2D.Double(m.mx+ Game.scrollX-1, hitbox.getCenterY()+ Game.scrollY-1)));
//		g.draw(new Line2D.Double(new Point2D.Double(hitbox.getCenterX()+Game.scrollX-1,hitbox.getCenterY()+Game.scrollY-1), new Point2D.Double(hitbox.getCenterX()+Game.scrollX-1, m.my+ Game.scrollY-1)));
//

		
		
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

		if (!(vy == vx && vy == 0))
			angle = Math.atan2(vy, vx);

		if (health <= 0) {

			game.state = GameState.GAME_OVER;

		}
		
		//shootAngle = Math.atan2(mouseY - hitbox.getCenterY(), mouseX - hitbox.getCenterX());
		
	}
	
	public double findShootAngle() {
		shootAngle = Math.tan((mouseX-hitbox.getCenterY())/(mouseY-hitbox.getCenterY()));
		
		return shootAngle;
	}

	public void action() {
		if (space) {
			if (w == weapon.gun)
				shootMouse(shootSpeed, m.findAngle());
			if (w == weapon.sword)
				melee(10);
		}
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
	public void reset(int x, int y) {
		hitbox = new Rectangle2D.Double(360-8,1540, hitbox.getWidth(), hitbox.getHeight());
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

class MouseInput extends MouseAdapter{
	double mx;
	double my;
	double angle;
	double cx,cy;
	//Game game;
	Player p;
	public MouseInput(Player player){
		p = player;
		cx = 0;
		cy = 0;
	}
	
	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
		p.space = true;	
		
		//p.shootMouse(10, findAngle());
	}
	
	public double findAngle() {
		cx = (Game.resolution.getWidth()* (p.game.scaleX))/2;
		cy = (Game.resolution.getHeight()* (p.game.scaleY))/2;
		
		
		//double dx;
		//double dy;
		
		
//		if(mx >= cx && my <= cy) {
//			dx = mx-cx;
//			dy = cy-my;
//			angle = Math.atan(dx/dy);
//		}
//		else if (mx < cx && my <= cy) {
//			dx = cx-mx;
//			dy = cy-my;
//			angle = .5*Math.PI + Math.atan(dx/dy);
//		}
//		else if(mx <= cx && my > cy) {
//			dx = cx - mx;
//			dy = my - cy;
//			angle = Math.PI + Math.atan(dx/dy);
//		}
//		else {
//			dx = mx - cx;`
//			dy = my - cy;
//			angle = 2*Math.PI-Math.atan(dx/dy);
//		}
		angle = Math.atan2(my-cy, mx-cx);

		//System.out.println(angle * (180/Math.PI));
		return angle;
	}
	
	public void mouseReleased(MouseEvent e) {
		p.space = false;
	}
	
}