package game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
//import java.awt.geom.Rectangle2D;

public class Laser{
	GameObject hit1;
	GameObject hit2;
	GameObject beam;
	Game game;
	boolean deactivated1;
	boolean deactivated2;
	public Laser(int x, int y, int w, int h, int d, Game g) {
		hit1 = new GameObject(x,y,w,h);
		hit2 = new GameObject(x+d,y,w,h);
		beam = new GameObject(x, y + (int)(w*.25), d, 5);
		deactivated1 = deactivated2 = false;
		game = g;
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics2D g) {
		g.setColor(new Color(255, 255, 255));
		g.fill(hit1.hitbox);
		g.fill(hit2.hitbox);
		g.fill(beam.hitbox);
		
		tick();

	}
	
	
	
	public void deactivate(GameObject g) {
		if(g == hit1) {deactivated1 = true;}
		if(g == hit2) {deactivated2 = true;}
		
		if(deactivated1 == deactivated2 == true)
		beam.hitbox = new Rectangle2D.Double(0,0,0,0);
	}
	
	public void tick() {
		collide(hit1);
		collide(hit2);
		collide(beam);
		
	}
	
	public void collide(GameObject g) {
		if (g.hitbox.intersects(game.p.hitbox)) {
			
			if (game.p.hitbox.getCenterX() < g.hitbox.getCenterX()) {
				game.p.trigMove(-2, 0);
			} else {
				game.p.trigMove(2, 0);
			}
			if (game.p.hitbox.getCenterY() < g.hitbox.getCenterY()) {
				game.p.trigMove(0, -2);
			} else {
				game.p.trigMove(0, 2);
			}
		}
	}
	
	public void scroll(double scrollX, double scrollY){
		//hit1.scroll(scrollX, scrollY);
		//hit2.scroll(scrollX, scrollY);
		//beam.scroll(scrollX, scrollY);

	}
	
	
}
