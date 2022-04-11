
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

class Menu extends MouseAdapter {
	Game game;
	Font font;
	FontMetrics metrics;
	double gameW, gameH;
	Image Title;
	Button TitlePlay, TitleControls;
	Image Controls;
	Button ControlsBack, ControlsMove, ControlsAttack, ControlsSelect;
	Button ControlsMoveB, ControlsAttackB, ControlsSelectB;
	Image HUD;
	Button HUDPause, HUDTitle1, HUDWeapon1, HUDWeapon2, HUDWeapon3;
	Image Pause;
	Button PauseResume, PauseControls, PausePowerups, PauseQuit;
	Image Death;
	Image Powerups;
	Button PowerupsBack;
	Button DeathRestart, DeathQuit;
	Image Power_1;
	Button Power_1_1, Power_1_2;

	private class Button {
		Image image;
		Rectangle2D hitbox;

		public Button(Image i, int x, int y, int w, int h) {
			image = i;
			hitbox = new Rectangle2D.Double(x, y, w, h);
		}

		public void highlight(Graphics2D g) {
			Game.drawImage(image, hitbox, game.sidebarSize, game.topbarSize, g);
		}
	}

	public Menu(Game game) {
		this.game = game;
		font = new Font("courier new", 1, 50);
		gameW = game.getSize().width;
		gameH = game.getSize().height;

		Title = image("/game/menus/Title/Title.png");
		TitlePlay = new Button(image("/game/menus/Title/TitlePlay.png"), 40, 26, 160, 52);
		TitleControls = new Button(image("/game/menus/Title/TitleControls.png"), 40, 106, 160, 13);

		Controls = image("/game/menus/Controls/Controls.png");
		ControlsBack = new Button(image("/game/menus/Controls/ControlsBack.png"), 20, 140, 200, 13);
		ControlsMove = new Button(image("/game/menus/Controls/ControlsMove.png"), 24, 114, 48, 13);
		ControlsMoveB = new Button(image("/game/menus/Controls/ControlsMoveB.png"), 0, -5, 240, 160);
		ControlsAttack = new Button(image("/game/menus/Controls/ControlsAttack.png"), 74, 114, 70, 13);
		ControlsAttackB = new Button(image("/game/menus/Controls/ControlsAttackB.png"), 0, -5, 240, 160);
		ControlsSelect = new Button(image("/game/menus/Controls/ControlsSelect.png"), 146, 114, 70, 13);
		ControlsSelectB = new Button(image("/game/menus/Controls/ControlsSelectB.png"), 0, -5, 240, 160);

		HUD = image("/game/menus/HUD/HUD.png");
		HUDTitle1 = new Button(image("/game/menus/HUD/HUDTitle1.png"), 74, 134, 92, 26);
		HUDWeapon1 = new Button(image("/game/menus/HUD/HUDWeapon1.png"), 164, 134, 76, 26);
		HUDWeapon2 = new Button(image("/game/menus/HUD/HUDWeapon2.png"), 164, 134, 76, 26);
		HUDWeapon3 = new Button(image("/game/menus/HUD/HUDWeapon3.png"), 164, 134, 76, 26);
		HUDPause = new Button(image("/game/menus/HUD/HUDPause.png"), 210, 6, 20, 20);

		Pause = image("/game/menus/Pause/Pause.png");
		PauseResume = new Button(image("/game/menus/Pause/PauseResume.png"), 20, 46, 200, 26);
		PausePowerups = new Button(image("/game/menus/Pause/PausePowerups.png"), 20, 74, 200, 26);
		PauseControls = new Button(image("/game/menus/Pause/PauseControls.png"), 20, 102, 200, 26);
		PauseQuit = new Button(image("/game/menus/Pause/PauseQuit.png"), 74, 130, 92, 26);

		Powerups = image("/game/menus/Powerups/Powerups.png");
		PowerupsBack = new Button(image("/game/menus/Powerups/PowerupsBack.png"), 20, 140, 200, 13);

		Death = image("/game/menus/Death/Death.png");
		DeathRestart = new Button(image("/game/menus/Death/DeathRestart.png"), 45, 76, 158, 46);
		DeathQuit = new Button(image("/game/menus/Death/DeathQuit.png"), 78, 126, 92, 26);

		Power_1 = image("/game/menus/Power_1/Power_1.png");
		Power_1_1 = new Button(image("/game/menus/Power_1/Power_1_1.png"), 11, 11, 98, 138);
		Power_1_2 = new Button(image("/game/menus/Power_1/Power_1_2.png"), 131, 11, 98, 138);

	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		switch (game.state) {
		case TITLE:
			if (mouseOver(TitlePlay)) {
				game.state = GameState.RUNNING;
				game.prevState = GameState.TITLE;
			} else if (mouseOver(TitleControls)) {
				game.state = GameState.INSTRUCTIONS;
				game.prevState = GameState.TITLE;
			}
			break;
		case INSTRUCTIONS:
			if (mouseOver(ControlsBack)) {
				game.state = game.prevState;
				game.prevState = GameState.INSTRUCTIONS;
			}
			break;
		case PAUSE:
			if (mouseOver(PauseResume)) {
				game.state = GameState.RUNNING;
				game.prevState = GameState.PAUSE;
			} else if (mouseOver(PausePowerups)) {
				game.state = GameState.POWERUPDISPLAY;
				game.prevState = GameState.PAUSE;
			} else if (mouseOver(PauseControls)) {
				game.state = GameState.INSTRUCTIONS;
				game.prevState = GameState.PAUSE;
			} else if (mouseOver(PauseQuit)) {
				System.exit(0);
			}
			break;
		case GAME_OVER:
			if (mouseOver(DeathRestart)) {
				switch (game.level) {
				case 1:
					game.buildLvl1();
					break;
				case 2:
					game.buildLvl2();
					break;
				default:
					System.out.println("HOW DID YOU DIE ON LEVEL " + game.level + " IT DOESN'T EXIST");
				}

				game.state = GameState.RUNNING;
			} else if (mouseOver(DeathQuit)) {
				System.exit(0);
			}
			break;
		case POWERUP1:
			if (mouseOver(Power_1_1)) {
				game.p.powerups.add(1);
				game.p.s.damage = 100;

				game.state = GameState.RUNNING;
				game.prevState = GameState.POWERUP1;
			} else if (mouseOver(Power_1_2)) {
				game.p.powerups.add(2);
				game.p.shootSpeed = 5;

				game.state = GameState.RUNNING;
				game.prevState = GameState.POWERUP1;
			}
			break;
		case POWERUP2:
			break;
		case POWERUP3:
			break;
		case POWERUPDISPLAY:
			if (mouseOver(PowerupsBack)) {
				game.state = game.prevState;
				game.prevState = GameState.POWERUPDISPLAY;
			}
			break;
		case RUNNING:
			if (mouseOver(HUDPause)) {
				game.state = GameState.PAUSE;
				game.prevState = GameState.RUNNING;
			}
			break;
		default:
			break;
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

	public boolean mouseOver(Button b) {
		double x = MouseInfo.getPointerInfo().getLocation().getX() / game.scaleX - game.sidebarSize;
		double y = MouseInfo.getPointerInfo().getLocation().getY() / game.scaleY - game.topbarSize;
		return b.hitbox.contains(x, y);
	}

	public void render(Graphics2D g) {
		metrics = g.getFontMetrics(font);

		switch (game.state) {
		case TITLE:
			Game.drawImage(Title, game.sidebarSize, game.topbarSize, 240, 160, g);
			if (mouseOver(TitlePlay))
				TitlePlay.highlight(g);
			else if (mouseOver(TitleControls))
				TitleControls.highlight(g);
			break;
		case INSTRUCTIONS:
			Game.drawImage(Controls, game.sidebarSize, game.topbarSize, 240, 160, g);
			if (mouseOver(ControlsBack)) {
				ControlsBack.highlight(g);
			} else if (mouseOver(ControlsMove)) {
				ControlsMove.highlight(g);
				ControlsMoveB.highlight(g);
			} else if (mouseOver(ControlsAttack)) {
				ControlsAttack.highlight(g);
				ControlsAttackB.highlight(g);
			} else if (mouseOver(ControlsSelect)) {
				ControlsSelect.highlight(g);
				ControlsSelectB.highlight(g);
			}
			break;
		case PAUSE:
			Game.drawImage(Pause, game.sidebarSize, game.topbarSize, 240, 160, g);
			if (mouseOver(PauseResume))
				PauseResume.highlight(g);
			if (mouseOver(PausePowerups))
				PausePowerups.highlight(g);
			if (mouseOver(PauseControls))
				PauseControls.highlight(g);
			if (mouseOver(PauseQuit))
				PauseQuit.highlight(g);
			break;
		case GAME_OVER:
			Game.drawImage(Death, game.sidebarSize, game.topbarSize, 240, 160, g);
			if (mouseOver(DeathRestart))
				DeathRestart.highlight(g);
			if (mouseOver(DeathQuit))
				DeathQuit.highlight(g);
			break;
		case POWERUP1:
			Game.drawImage(Power_1, game.sidebarSize, game.topbarSize, 240, 160, g);
			if (mouseOver(Power_1_1))
				Power_1_1.highlight(g);
			if (mouseOver(Power_1_2))
				Power_1_2.highlight(g);
			break;
		case POWERUP2:
			break;
		case POWERUP3:
			break;
		case POWERUPDISPLAY:
			Game.drawImage(Powerups, game.sidebarSize, game.topbarSize, 240, 160, g);
			if (mouseOver(PowerupsBack))
				PowerupsBack.highlight(g);
			break;
		case RUNNING:
			game.renderGame(g);
			Game.drawImage(HUD, game.sidebarSize, game.topbarSize, 240, 160, g);

			switch (game.p.weapon) {
			case gun:
				HUDWeapon1.highlight(g);
				break;
			case sword:
				HUDWeapon2.highlight(g);
				break;
			}

			switch (game.level) {
			case 1:
				HUDTitle1.highlight(g);
				break;
			default:
			}

			g.setColor(Color.red);
			g.fillRect(6 + (int) game.sidebarSize, 140 + (int) game.topbarSize, (int) (64 * (game.p.health / 50.0)), 4);

			if (mouseOver(HUDPause))
				HUDPause.highlight(g);
			break;
		default:
			break;

		}

	}

	public void renderPause(Graphics2D g) {
		Game.drawImage(Pause, game.sidebarSize, game.topbarSize, 240, 160, g);
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

	public Image image(String url) {
		try {
			return Game.generateSprite(url);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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