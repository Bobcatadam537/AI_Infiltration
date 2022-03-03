package game;

import java.awt.*;
//import java.awt.geom.Point2D;
//import java.util.ArrayList;
//import java.awt.geom.Rectangle2D;

import javax.imageio.ImageIO;

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

//		g.setColor(new Color(255, 255, 255));
//		g.fill(new Rectangle2D.Double((int) (hitbox.getX() + Game.scrollX), (int) (hitbox.getY() + Game.scrollY), 20,20));
		try {
			Image i = ImageIO.read(getClass().getResourceAsStream("/game/spriteSheets/Door.png"));
			g.drawImage(i, (int)(hitbox.getX( )+Game.scrollX-1), (int)(hitbox.getY()+ Game.scrollY-1), (int)hitbox.getWidth(), (int)hitbox.getHeight(),  null);

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		tick();
		
		
	}

	
	public void transport() {
		if (this.hitbox.intersects(game.p.hitbox)) {
			//Game.p.hitbox = new Rectangle2D.Double(game.p.hitbox.getX(), game.p.hitbox.getY()+y, game.p.hitbox.getWidth(), game.p.hitbox.getHeight());
			game.p.reset(100, 100);
			game.enemies.removeAll(game.enemies);
			game.level++;
			if(game.level == 2) {
			game.makeLvl2();
			game.state = GameState.POWERUP1;}
			else if(game.level == 3) {
				game.makeLvl3();
				game.state = GameState.POWERUP2;
			} 
			
			System.out.println("Level change");
		}
		
	}
	
	public void tick() {
		//if(game.enemies.size() == 0)
		if(level == 1)
		
		
		//(level == 2) {
			transport();
			
		}
		}

		
	

