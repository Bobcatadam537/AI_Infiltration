
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

class Menu extends MouseAdapter {
	Game game;
	Font font;
	FontMetrics metrics;
	double gameW, gameH;
	Image Play, instructionButton, Instructions;

	public Menu(Game game) {
		this.game = game;
		font = new Font("courier new", 1, 50);
		gameW = game.getSize().width;
		gameH = game.getSize().height;
		try {
			Play = Game.generateSprite("/game/menuButtons/Play.png");
			instructionButton = Game.generateSprite("/game/menuButtons/instructButton.png");
			Instructions = Game.generateSprite("/game/menuButtons/Instructions.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (mouseOver(mx, my, (int) gameW / 6, (int) gameH / 6, (int) (gameW * (2.0 / 3)), (int) (gameH / 3))) {
			if (game.state == GameState.MENU) {
				game.state = GameState.RUNNING;
				game.prevState = GameState.MENU;
			}
		}
		if (mouseOver(mx, my, (int) gameW / 6, (int) (gameH * (2.0 / 3)), (int) (gameW * (2.0 / 3)),
				(int) (gameH / 6))) {
			if (game.state == GameState.MENU) {
				game.state = GameState.INSTRUCTIONS;
				game.prevState = GameState.MENU;
			}
		}
		if (mouseOver(mx, my, (int) gameW / 12, (int) (gameH * (21.0 / 24)), (int) (gameW * (10.0 / 12)),
				(int) (gameH * (1.0 / 12)))) {
			if (game.state == GameState.INSTRUCTIONS) {
				game.state = game.prevState;
				game.prevState = GameState.INSTRUCTIONS;
			}
		}
		if (mouseOver(mx, my, (int) (game.getSize().width * (21.0 / 24)), (int) (game.getSize().height * (1.0 / 24)),
				(int) (game.getSize().width / 12), (int) (game.getSize().width / 12))) {
			if (game.state == GameState.RUNNING) {
				game.state = GameState.PAUSE;
				game.prevState = GameState.RUNNING;
			}
		}
		if (mouseOver(mx, my, (int) gameW / 12, (int) gameH / 4, (int) (gameW * (5.0 / 6)), (int) gameH / 6)) {
			if (game.state == GameState.PAUSE) {
				game.state = GameState.RUNNING;
				game.prevState = GameState.PAUSE;
			}
		}
		if (mouseOver(mx, my, (int) gameW / 12, (int) (gameH * .75), (int) (gameW * (5.0 / 6)), (int) gameH / 6)) {
			if (game.state == GameState.PAUSE) {
				game.state = GameState.INSTRUCTIONS;
				game.prevState = GameState.PAUSE;
			}
		}
		if (mouseOver(mx, my, (int) gameW / 12, (int) gameH / 2, (int) (gameW * (5.0 / 6)), (int) gameH / 6)) {
			if (game.state == GameState.PAUSE) {
				game.state = GameState.POWERUPDISPLAY;
				game.prevState = GameState.PAUSE;
			}
		}

		if (mouseOver(mx, my, 100, 200, 600, 800)) {
			if (game.state == GameState.POWERUP1) {
				game.p.powerups.add(1);
				game.p.s.damage = 100;

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
		if (mouseOver(mx, my, (int) (gameW * (10.0 / 12)), (int) (gameH * (10.0 / 12)), (int) gameW / 12,
				(int) gameW / 12)) {
			if (game.state == GameState.POWERUPDISPLAY) {
				game.state = game.prevState;

				game.prevState = GameState.POWERUPDISPLAY;
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
		gameW = game.resolution.getWidth();
		gameH = game.resolution.getHeight();

		metrics = g.getFontMetrics(font);
		if (game.state == GameState.MENU) {
			g.setColor(new Color(255, 255, 255));
			Game.drawImage(Play, gameW / 6, gameH / 6, gameW * (2.0 / 3), gameH / 3, g);
			Game.drawImage(instructionButton, gameW / 6, gameH * (2.0 / 3), gameW * (2.0 / 3), gameH / 6, g);
		}
	}

	public void renderInstructions(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fill(new Rectangle2D.Float((int) gameW / 12, (int) gameH / 12, (int) (gameW * (5.0 / 6)),
				(int) (gameH * (9.0 / 12))));
		Game.drawImage(Instructions, gameW / 12, gameH / 12, gameW * (5.0 / 6), gameH * (9.0 / 12), g);
		g.fill(new Rectangle2D.Float((int) gameW / 12, (int) (gameH * (21.0 / 24)), (int) (gameW * (10.0 / 12)),
				(int) (gameH * (1.0 / 12))));
		g.setColor(Color.RED);
		metrics = g.getFontMetrics(font);
		g.setFont(new Font("courier new", 1, 60));

		g.drawString("Back", 720 - metrics.stringWidth("Back") / 2, 990 - metrics.getHeight() / 2);

	}

	public void renderGameOver(Graphics2D g) {
		g.setColor(Color.RED);
		metrics = g.getFontMetrics(font);
		g.setFont(new Font("courier new", 1, 300));
		g.drawString("GAME OVER", 720 / 2 - metrics.stringWidth("GAME OVER") / 2 - 100, 240 - metrics.getHeight() / 2);

	}

	public void renderPause(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setColor(Color.red);
		g.setFont(new Font("courier new", 1, 80));
		g.drawString("Pause", 720 - metrics.stringWidth("Pause") / 2, 100 + metrics.getHeight());
		g.setColor(Color.WHITE);

		g.fill(new Rectangle2D.Float((int) gameW / 12, (int) gameH / 4, (int) (gameW * (5.0 / 6)), (int) gameH / 6));
		g.fill(new Rectangle2D.Float((int) gameW / 12, (int) gameH / 2, (int) (gameW * (5.0 / 6)), (int) gameH / 6));
		g.fill(new Rectangle2D.Float((int) gameW / 12, (int) (gameH * .75), (int) (gameW * (5.0 / 6)),
				(int) gameH / 6));

	}

	public void renderPowerMenu1(Graphics2D g) {

		g.setColor(Color.red);
		g.setFont(new Font("courier new", 1, 80));
		g.drawString("Select a Powerup", 720 - metrics.stringWidth("Select a Powerup") / 2, 100 + metrics.getHeight());
		g.setColor(Color.WHITE);
		g.fill(new Rectangle2D.Float(100, 200, 600, 800));
		g.fill(new Rectangle2D.Float(800, 200, 600, 800));
		// g.fill(new Rectangle2D.Float(175, 850, 1200, 100));

	}

	public void renderPowerMenu2(Graphics2D g) {

		g.setColor(Color.red);
		g.setFont(new Font("courier new", 1, 80));
		g.drawString("Select a Powerup", 720 - metrics.stringWidth("Select a Powerup") / 2, 100 + metrics.getHeight());
		g.setColor(Color.WHITE);
		g.fill(new Rectangle2D.Float(100, 200, 600, 800));
		g.fill(new Rectangle2D.Float(800, 200, 600, 800));
		// g.fill(new Rectangle2D.Float(175, 850, 1200, 100));

	}

	public void renderPowerDisplay(Graphics2D g) {

		g.setColor(Color.red);
		g.setFont(new Font("courier new", 1, 80));
		g.drawString("Active Powerup", 720 - metrics.stringWidth("Active Powerup") / 2, 100 + metrics.getHeight());
		g.setColor(Color.WHITE);

		g.fill(new Rectangle2D.Float((int) (gameW * (10.0 / 12)), (int) (gameH * (10.0 / 12)), (int) gameW / 12,
				(int) gameW / 12));
		// g.fill(new Rectangle2D.Float(175, 850, 1200, 100));

	}

}