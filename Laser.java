package game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
//import java.awt.geom.Rectangle2D;

import javax.imageio.ImageIO;

public class Laser {
	GameObject hit1;
	GameObject hit2;
	GameObject beam;
	Game game;
	boolean deactivated1;
	boolean deactivated2;

	public Laser(int x, int y, int w, int h, int d, Game g) {
		hit1 = new GameObject(x, y, w, h);
		hit2 = new GameObject(x + d, y, w, h);
		beam = new GameObject(x, y + (int) (w * .33), d, 5);
		deactivated1 = deactivated2 = false;
		game = g;
		// TODO Auto-generated constructor stub
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		
		
		if(deactivated1)
		g.setColor(Color.BLACK);
		
		g.fill(new Rectangle2D.Double(hit1.hitbox.getX() + Game.scrollX-1, hit1.hitbox.getY() + Game.scrollY-1, hit1.hitbox.getWidth(), hit1.hitbox.getHeight()));
		
		g.setColor(Color.WHITE);	
		if(deactivated2)
		g.setColor(Color.BLACK);
		g.fill(new Rectangle2D.Double(hit2.hitbox.getX() + Game.scrollX-1, hit2.hitbox.getY() + Game.scrollY-1, hit2.hitbox.getWidth(), hit2.hitbox.getHeight()));

		g.setColor(Color.WHITE);

//		g.fill(	new Rectangle2D.Double(beam.hitbox.getX() + Game.scrollX-1, beam.hitbox.getY() + Game.scrollY-1, beam.hitbox.getWidth(), beam.hitbox.getHeight()));
		try {
			Image i = ImageIO.read(getClass().getResourceAsStream("/game/spriteSheets/laserBeam.png"));
			g.drawImage(i, (int)(beam.hitbox.getX( )+Game.scrollX-1), (int)(beam.hitbox.getY()+ Game.scrollY-1), (int)beam.hitbox.getWidth(), (int)beam.hitbox.getHeight(),  null);

		}catch (Exception e) {
			e.printStackTrace();
		}	
		tick();

	}

	public void deactivate(GameObject g) {
		if (g == hit1) {
			deactivated1 = true;
		}
		if (g == hit2) {
			deactivated2 = true;
		}

		if (deactivated1 == deactivated2 == true)
			beam.hitbox = new Rectangle2D.Double(0, 0, 0, 0);
	}

	public void tick() {
		collideV(hit1);
		collideV(hit2);
		collideH(hit1);
		collideH(hit2);
		
		collideV(beam);
		

	}

	public void collideV(GameObject g) {
		if (g.hitbox.intersects(game.p.hitbox)) {

			if (game.p.hitbox.getCenterY() < g.hitbox.getCenterY()) {
				game.p.trigMove(0, -2);
			} else {
				game.p.trigMove(0, 2);
			}
		}
	}
	public void collideH(GameObject g) {
		if (g.hitbox.intersects(game.p.hitbox)) {

		if (game.p.hitbox.getCenterX() < g.hitbox.getCenterX()) {
			game.p.trigMove(-1.5, 0);
		} else {
			game.p.trigMove(1.5, 0);
		}}
	}
	

	public void scroll(double scrollX, double scrollY) {
		// hit1.scroll(scrollX, scrollY);
		// hit2.scroll(scrollX, scrollY);
		// beam.scroll(scrollX, scrollY);

	}

}