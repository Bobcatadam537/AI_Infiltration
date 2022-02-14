
package game;

import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.*;

class Menu extends MouseAdapter {
	Game game;
	Font font;
	FontMetrics metrics;

	public Menu(Game game) {
		this.game = game;
		font = new Font("courier new", 1, 50);
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (mouseOver(mx, my, 300, 250, (int)(520*1.75), (int)(200*1.5))) {

			if (game.state == GameState.MENU) {
				game.state = GameState.RUNNING;
				game.prevState = GameState.MENU;
			}
		}
		if (mouseOver(mx, my, 300, 600, 910, 150)) {
			if (game.state == GameState.MENU) {
				game.state = GameState.INSTRUCTIONS;
				game.prevState = GameState.MENU;
			}
		}
		if (mouseOver(mx, my, 100, 900, 1300, 100)) {
			if (game.state == GameState.INSTRUCTIONS) {
				game.state = game.prevState;
			game.prevState = GameState.INSTRUCTIONS;}
		}
		if (mouseOver(mx, my, 1350, 50, 100, 100)) {
			if (game.state == GameState.RUNNING) {
				game.state = GameState.PAUSE;
				game.prevState = GameState.RUNNING;
			}
		}
		if (mouseOver(mx, my, 175, 250, 1200, 150)) {
			if (game.state == GameState.PAUSE) {
				game.state = GameState.RUNNING;
			game.prevState = GameState.PAUSE;}
		}
		if (mouseOver(mx, my, 175, 750, 1200, 150)) {
			if (game.state == GameState.PAUSE) {
				game.state = GameState.INSTRUCTIONS;
				game.prevState = GameState.PAUSE;
			}
		}
		if (mouseOver(mx, my, 100, 200, 600, 800)) {
			if (game.state == GameState.POWERUP1) {
				game.p.powerups.add(1);
				game.p.s.length = 30;

				game.state = GameState.RUNNING;
				game.prevState = GameState.POWERUP1;
			}
		}
		if (mouseOver(mx, my, 800, 200, 600, 800)) {
			if (game.state == GameState.POWERUP1) {
				game.p.powerups.add(2);
				game.p.shootSpeed = 5;

				game.state = GameState.RUNNING;
				game.prevState = GameState.POWERUP1;
			}
		}
		
		
	}

	public void mouseReleased(MouseEvent e) {

	}

	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width)
			if (my > y && my < y + height)
				return true;
		return false;
	}


	public void render(Graphics2D g) {
		metrics = g.getFontMetrics(font);
		if (game.state == GameState.MENU) {

			g.setColor(new Color(255, 255, 255));
			g.fill(new Rectangle2D.Float(300, 250, (int)(520*1.75), (int)(200*1.5)));

			g.fill(new Rectangle2D.Float(300, 600, 910, 150));
			g.setColor(Color.red);
			g.setFont(new Font("courier new", 1, 80));
			g.drawString("Play", 720 - metrics.stringWidth("Play") / 2, 450 - metrics.getHeight() / 2);
			g.setFont(new Font("courier new", 1, 70));
			g.drawString("Instructions", 700 - metrics.stringWidth("Instructions") / 2, 725 - metrics.getHeight() / 2);
		}

	}

	public void renderInstructions(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fill(new Rectangle2D.Float(100, 100, 1300, 750));
		g.fill(new Rectangle2D.Float(100, 900, 1300, 100));
		g.setColor(Color.RED);
		metrics = g.getFontMetrics(font);
		g.setFont(new Font("courier new", 1, 60));
		
		
		g.drawString("Back", 720 - metrics.stringWidth("Back") / 2, 990 - metrics.getHeight() / 2);

	}
	
	public void renderGameOver(Graphics2D g) {
		g.setColor(Color.RED);
		metrics = g.getFontMetrics(font);
		g.setFont(new Font("courier new", 1, 300));
		g.drawString("GAME OVER", 720 / 2 - metrics.stringWidth("GAME OVER") / 2-100, 240 - metrics.getHeight() / 2);

	}
	
	public void renderPause(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setColor(Color.red);
		g.setFont(new Font("courier new", 1, 80));
		g.drawString("Pause", 720 - metrics.stringWidth("Pause") / 2, 100+ metrics.getHeight());
		g.setColor(Color.WHITE);

		g.fill(new Rectangle2D.Float(175, 250, 1200, 150));
		g.fill(new Rectangle2D.Float(175, 500, 1200, 150));
		g.fill(new Rectangle2D.Float(175, 750, 1200, 150));
		
	}
	
	public void renderPowerMenu1(Graphics2D g) {
		
		g.setColor(Color.red);
		g.setFont(new Font("courier new", 1, 80));
		g.drawString("Select a Powerup", 720 - metrics.stringWidth("Select a Powerup") / 2, 100+ metrics.getHeight());
		g.setColor(Color.WHITE);
		g.fill(new Rectangle2D.Float(100, 200, 600, 800));
		g.fill(new Rectangle2D.Float(800, 200, 600, 800));
		//g.fill(new Rectangle2D.Float(175, 850, 1200, 100));
		

		
	}
	
}
