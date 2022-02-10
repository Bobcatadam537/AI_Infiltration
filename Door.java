package game;

import java.awt.*;
//import java.awt.geom.Point2D;
//import java.util.ArrayList;

public class Door extends GameObject {
	FontMetrics metrics;
	Font font;
	Game game;
	int level;
	public Door(int x, int y, int w, int h, Game g, int lvl) {
		super(x, y, w, h);
		level = lvl;
		game = g;
		font = new Font("courier new", 1, 50);

		// TODO Auto-generated constructor stub
	
	}
	


	
	public void render(Graphics2D g) {

		g.setColor(new Color(255, 255, 255));
		g.fill(hitbox);
		tick();
		
		
	}

	
	public void transport(int x, int y) {
		if (this.hitbox.intersects(game.p.hitbox)) {
			game.p.scroll(x, y);
			game.state = GameState.POWERUP1;
			
			System.out.println("Level change");
		}
		
	}
	
	public void tick() {
		//if(game.enemies.size() == 0)
		if(level == 1)
		transport(975, 1400);
		
		if(level == 2) {
			transport(990, 1450);
			
		}
		}

		
	}

