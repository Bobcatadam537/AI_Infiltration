package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

enum GameState {
	TITLE, RUNNING, GAME_OVER, INSTRUCTIONS, PAUSE, POWERUPDISPLAY, POWERUP1, POWERUP2, POWERUP3
}

public class Game extends Canvas implements Runnable {
	GameState state = GameState.TITLE;
	GameState prevState = GameState.GAME_OVER;
	int level = 1;

	Menu menu = new Menu(this);
	Font font;
	FontMetrics metrics;

	Player p;
	Door d;

	Background lv1, lv2, lv3;

	ArrayList<Solid> solids = new ArrayList<Solid>();
	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	ArrayList<Laser> lasers = new ArrayList<Laser>();
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	LinkedList<Effect> effects = new LinkedList<Effect>();

	pNode[][] walkable = new pNode[0][0];
	int cooldown = 2000;
	static final int pNodeSize = 10;
	private static final long serialVersionUID = 1L;
	Dimension resolution;
	static final Rectangle2D screen = new Rectangle2D.Float(0, 0, 240, 160);
	double scaleX, scaleY;
	static double scrollX, scrollY;
	double sidebarSize = 0;
	double topbarSize = 0;
	private Thread thread;
	boolean running = false;
	Window w;

	public Game() {
		font = new Font("courier new", 1, 50);

		p = new Player(360 - 8, 1560, this);
		enemies.add(new Enemy(300, 900, 16, 20, this));

		try {
			lv1 = new Background(Game.generateSprite("/game/backgrounds/lv1.png"), 0, 0);
			lv2 = new Background(Game.generateSprite("/game/backgrounds/lv2.png"), 0, 0);
			lv3 = new Background(Game.generateSprite("/game/backgrounds/lv3.png"), 0, 0);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		d = new Door((int) lv1.x / 2 - (75 / 2), (int) lv1.y + 100, 75, 75, this, 1);
		scaleX = scaleY = 1;
		w = new Window((int) screen.getWidth(), (int) screen.getHeight(), "Game", this);
		resolution = new Dimension(w.getWidth(), w.getHeight());
		this.addMouseListener(menu);
		this.addMouseListener(p.m);
	}

	public synchronized void start() {
		buildLvl1();

		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public void buildLvl1() {
		solids = new ArrayList<Solid>();
		enemies = new LinkedList<Enemy>();
		lasers = new ArrayList<Laser>();
		bullets = new LinkedList<Bullet>();
		effects = new LinkedList<Effect>();
		level = 1;
		d = new Door(360 - 8, 1300, 75, 75, this, 1);
		p.reset(360 - 8, 1560);

		enemies.add(new Enemy(300, 900, 16, 20, this));

		ArrayList<Point2D> tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Double(302, 36));
		tempPoints.add(new Point2D.Double(417, 36));
		tempPoints.add(new Point2D.Double(453, 72));
		tempPoints.add(new Point2D.Double(453, 224));
		tempPoints.add(new Point2D.Double(648, 419));
		tempPoints.add(new Point2D.Double(648, 540));
		tempPoints.add(new Point2D.Double(408, 780));
		tempPoints.add(new Point2D.Double(408, 834));
		tempPoints.add(new Point2D.Double(445, 871));
		tempPoints.add(new Point2D.Double(610, 871));
		tempPoints.add(new Point2D.Double(648, 909));
		tempPoints.add(new Point2D.Double(648, 1170));
		tempPoints.add(new Point2D.Double(610, 1208));
		tempPoints.add(new Point2D.Double(445, 1208));
		tempPoints.add(new Point2D.Double(408, 1245));
		tempPoints.add(new Point2D.Double(408, 1447));
		tempPoints.add(new Point2D.Double(379, 1476));
		tempPoints.add(new Point2D.Double(379, 1539));
		tempPoints.add(new Point2D.Double(373, 1545));
		tempPoints.add(new Point2D.Double(373, 1549));
		tempPoints.add(new Point2D.Double(381, 1557));
		tempPoints.add(new Point2D.Double(381, 1579));
		tempPoints.add(new Point2D.Double(372, 1588));
		tempPoints.add(new Point2D.Double(347, 1588));
		tempPoints.add(new Point2D.Double(338, 1579));
		tempPoints.add(new Point2D.Double(338, 1557));
		tempPoints.add(new Point2D.Double(346, 1549));
		tempPoints.add(new Point2D.Double(346, 1545));
		tempPoints.add(new Point2D.Double(340, 1539));
		tempPoints.add(new Point2D.Double(340, 1476));
		tempPoints.add(new Point2D.Double(311, 1447));
		tempPoints.add(new Point2D.Double(311, 1245));
		tempPoints.add(new Point2D.Double(274, 1208));
		tempPoints.add(new Point2D.Double(109, 1208));
		tempPoints.add(new Point2D.Double(71, 1170));
		tempPoints.add(new Point2D.Double(71, 909));
		tempPoints.add(new Point2D.Double(109, 871));
		tempPoints.add(new Point2D.Double(274, 871));
		tempPoints.add(new Point2D.Double(311, 834));
		tempPoints.add(new Point2D.Double(311, 780));
		tempPoints.add(new Point2D.Double(71, 540));
		tempPoints.add(new Point2D.Double(71, 419));
		tempPoints.add(new Point2D.Double(266, 224));
		tempPoints.add(new Point2D.Double(266, 72));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Double(279, 959));
		tempPoints.add(new Point2D.Double(440, 959));
		tempPoints.add(new Point2D.Double(440, 1120));
		tempPoints.add(new Point2D.Double(279, 1120));
		solids.add(new Solid(tempPoints, true));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Double(359, 269));
		tempPoints.add(new Point2D.Double(360, 269));
		tempPoints.add(new Point2D.Double(551, 460));
		tempPoints.add(new Point2D.Double(551, 499));
		tempPoints.add(new Point2D.Double(360, 690));
		tempPoints.add(new Point2D.Double(359, 690));
		tempPoints.add(new Point2D.Double(168, 499));
		tempPoints.add(new Point2D.Double(168, 460));
		solids.add(new Solid(tempPoints, true));

		buildPathfinding();
	}

	public void buildLvl2() {
		solids = new ArrayList<Solid>();
		enemies = new LinkedList<Enemy>();
		lasers = new ArrayList<Laser>();
		bullets = new LinkedList<Bullet>();
		effects = new LinkedList<Effect>();
		level = 2;
		d = new Door(100, 100, 75, 75, this, 1);
		p.reset(360 - 8, 1460);

		ArrayList<Point2D> tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Double(302, 1565));
		tempPoints.add(new Point2D.Double(417, 1565));
		tempPoints.add(new Point2D.Double(455, 1527));
		tempPoints.add(new Point2D.Double(455, 1374));
		tempPoints.add(new Point2D.Double(528, 1302));
		tempPoints.add(new Point2D.Double(528, 291));
		tempPoints.add(new Point2D.Double(455, 219));
		tempPoints.add(new Point2D.Double(455, 66));
		tempPoints.add(new Point2D.Double(417, 28));
		tempPoints.add(new Point2D.Double(302, 28));
		tempPoints.add(new Point2D.Double(264, 66));
		tempPoints.add(new Point2D.Double(264, 219));
		tempPoints.add(new Point2D.Double(191, 291));
		tempPoints.add(new Point2D.Double(191, 1302));
		tempPoints.add(new Point2D.Double(264, 1374));
		tempPoints.add(new Point2D.Double(264, 1527));
		tempPoints.add(new Point2D.Double(302, 1565));
		solids.add(new Solid(tempPoints));

		tempPoints.removeAll(tempPoints);
		tempPoints.add(new Point2D.Double(1191, 1210));

		tempPoints.add(new Point2D.Double(1508, 1210));
		// solids.add(new Solid(tempPoints));

		lasers.add(new Laser(191, 1200, 307, 30, this));
		lasers.add(new Laser(191, 1100, 307, 30, this));
		lasers.add(new Laser(191, 1000, 307, 30, this));

		enemies.add(new Enemy(300, 1150, 20, 20, 2, this));

		enemies.add(new Enemy(200, 800, 20, 20, 3, this));
		enemies.add(new Enemy(400, 800, 20, 20, 3, this));
		enemies.add(new Enemy(200, 700, 20, 20, 3, this));
		enemies.add(new Enemy(400, 700, 20, 20, 3, this));

		enemies.add(new Enemy(200, 300, 40, 40, 2, this));
		enemies.add(new Enemy(500, 300, 40, 40, 2, this));

		// lasers.add(new Laser(264, 150, 20, 20, (455 - 264 - 20), this));

		buildPathfinding();
	}

	public void buildLvl3() {
		System.out.println("LEVEL 3 UNDER CONSTRUCTION");
	}

	public void buildPathfinding() {
		walkable = new pNode[(int) (lv1.hitbox.getWidth() / pNodeSize)][(int) (lv1.hitbox.getHeight() / pNodeSize)];
		for (int row = 0; row < walkable.length; row++) {
			for (int col = 0; col < lv1.hitbox.getHeight() / pNodeSize; col++) {
				GameObject temp = new GameObject(row * pNodeSize, col * pNodeSize, pNodeSize, pNodeSize);
				walkable[row][col] = new pNode(row, col, temp.collide(solids).size() == 0);
			}
		}
		pNode.linkNodes(walkable);

		for (Enemy e : enemies) {
			e.loadPathfinding();
		}
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Timer renderTimer = new Timer("render");
		TimerTask RENDER = new RenderTask(this, renderTimer);
		renderTimer.schedule(RENDER, 17);
		while (running) {

		}
		stop();
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		resolution.setSize(w.getWidth(), w.getHeight());
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

		// if ratio is greater than 1.5, it is too wide. if it is less than 1.5, it is
		// too tall
		if ((resolution.width + 0.0) / resolution.height > 1.5) {
			scaleX = (resolution.height * 1.5) / screen.getWidth();
			scaleY = (resolution.height + 0.0) / screen.getHeight();
//			g2d.scale((resolution.height * 1.5) / screen.width, (resolution.height + 0.0) / screen.height);
		} else if ((resolution.width + 0.0) / resolution.height < 1.5) {
			scaleX = (resolution.width + 0.0) / screen.getWidth();
			scaleY = (resolution.width / 1.5) / screen.getHeight();
//			g2d.scale((resolution.width + 0.0) / screen.width, (resolution.width / 1.5) / screen.height);
		} else {
			scaleX = (resolution.width + 0.0) / screen.getWidth();
			scaleY = (resolution.height + 0.0) / screen.getHeight();
//			g2d.scale((resolution.width + 0.0) / screen.width, (resolution.height + 0.0) / screen.height);
		}
		g2d.scale(scaleX, scaleY);
		g2d.setColor(Color.BLACK);
		g2d.setBackground(Color.black);
		g2d.fillRect(0, 0, (int) resolution.getWidth(), (int) resolution.getHeight());

		menu.render(g2d);

//			menuG2D.setColor(Color.WHITE);
//			// menuG2D.fill(new Rectangle2D.Float(1250, 50, 100, 100));
//			menuG2D.fill(new Rectangle2D.Double(getSize().width * (21.0 / 24), getSize().height * (1.0 / 24),
//					getSize().width / 12, getSize().width / 12));
//
//			metrics = menuG2D.getFontMetrics(font);
//			menuG2D.setFont(new Font("courier new", 1, 30));
//			menuG2D.drawString("" + (screen.getWidth() + ", " + screen.getHeight()), 100, 100);
//			menuG2D.drawString("" + (screen.getWidth() * scaleX + ", " + screen.getHeight() * scaleY), 100, 500);
//
//			menuG2D.drawString(("" + scaleX + ", " + scaleY), 100, 200);
//			menuG2D.drawString(("" + 240 + ", " + 160), 100, 300);
//			menuG2D.drawString(("" + p.m.cx + ", " + p.m.cy), 100, 400);
//
//			menuG2D.draw(new Line2D.Double(new Point2D.Double(p.m.cx, p.m.cy), new Point2D.Double(p.m.mx, p.m.my)));
//			menuG2D.draw(new Line2D.Double(new Point2D.Double(p.m.cx, p.m.cy), new Point2D.Double(p.m.mx, p.m.cy)));
//			menuG2D.draw(new Line2D.Double(new Point2D.Double(p.m.cx, p.m.cy), new Point2D.Double(p.m.cx, p.m.my)));

		g2d.setColor(Color.black);
		if (screen.getWidth() * scaleX < resolution.getWidth()) {
			sidebarSize = Math.round((resolution.getWidth() - screen.getWidth() * scaleX) / scaleX) / 2;
			g2d.fill(new Rectangle2D.Double(0, 0, sidebarSize, screen.getHeight()));
			g2d.fill(new Rectangle2D.Double(screen.getWidth() + sidebarSize, 0, screen.getWidth(), screen.getHeight()));
		} else if (screen.getHeight() * scaleY < resolution.getHeight()) {
			topbarSize = Math.round((resolution.getHeight() - screen.getHeight() * scaleY) / scaleY) / 2;
			g2d.fill(new Rectangle2D.Double(0, 0, screen.getWidth(), topbarSize));
			g2d.fill(new Rectangle2D.Double(0, screen.getHeight() + topbarSize, screen.getWidth(), screen.getHeight()));
		}

		g2d.dispose();

		bs.show();
		tick();
		try {
			Thread.sleep(2);
		} catch (Exception e) {
		}
	}

	public void renderGame(Graphics2D g2d) {
		System.out.println(level);
		switch (level) {
		case 1:
			lv1.render(g2d);
			break;
		case 2:
			lv2.render(g2d);
			break;
		case 3:
			lv3.render(g2d);
			break;
		default:
			System.out.println("UNEXPECTED LEVEL VALUE");
		}

		p.render(g2d);
		d.render(g2d);

		for (Enemy e : enemies)
			e.render(g2d);
		for (Bullet b : bullets)
			b.render(g2d);
		for (Laser l : lasers) {
			l.render(g2d);
		}
		Iterator<Effect> it = effects.iterator();
		while (it.hasNext()) {
			Effect e = it.next();
			e.ani += e.type.speed;
			if (e.ani > e.type.numFrames) {
				it.remove();
			} else {
				e.render(g2d);
			}
		}
		g2d.setColor(Color.WHITE);
		for (Solid s : solids)
			s.render(g2d);
	}

	public void tick() {
		Iterator<Bullet> bi = bullets.iterator();
		while (bi.hasNext()) {
			Bullet b = bi.next();
			if (b.shooter != p && b.hitbox.intersects(p.hitbox)) {
				bi.remove();
				p.damage(5);
			} else if (!b.onScreen() || b.collide(solids).size() > 0) {
				bi.remove();
			} else if (lasers.size() != 0) {
				for (Laser l : lasers) {
					if (l.hit1.hitbox.intersects(b.hitbox)) {
						l.deactivate(l.hit1);
						bi.remove();
					} else if (l.hit2.hitbox.intersects(b.hitbox)) {
						l.deactivate(l.hit2);
						bi.remove();
					}
				}
			}
		}

		for (Enemy e : enemies)
			for (Bullet B : bullets)
				if (!B.shooter.getClass().equals(e.getClass()) && e.hitbox.intersects(B.hitbox)) {
					e.takeDamage(B.damage);
					B.damage = 0;
				}

		Iterator<Enemy> ei = enemies.iterator();
		while (ei.hasNext()) {
			Enemy e = ei.next();
			if (e.alive == false)
				ei.remove();
		}

		for (Bullet b : bullets) {
			for (Laser l : lasers) {
				if (l.hit1.hitbox.intersects(b.hitbox)) {
					l.deactivate(l.hit1);
				}
				if (l.hit2.hitbox.intersects(b.hitbox)) {
					l.deactivate(l.hit2);
				}
				if (l.beam.hitbox.intersects(b.hitbox)) {
					b.damage = 0;
				}
			}
		}
		scrollX = screen.getWidth() / 2 - (p.hitbox.getCenterX());
		scrollY = screen.getHeight() / 2 - (p.hitbox.getCenterY());
		if (screen.getWidth() * scaleX < resolution.getWidth()) {
			double temp = Math.round((resolution.getWidth() - screen.getWidth() * scaleX) / scaleX);
			scrollX += temp / 2;
		} else if (screen.getHeight() * scaleY < resolution.getHeight()) {
			double temp = Math.round((resolution.getHeight() - screen.getHeight() * scaleY) / scaleY);
			scrollY += temp / 2;
		}
	}

	public static Image[][] generateSprites(Image[][] s, String str, int w, int h) throws IOException {
		BufferedImage spriteSheet = ImageIO.read(Game.class.getResource(str));
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				s[i][j] = spriteSheet.getSubimage(i * w, j * h, w, h);
			}
		}
		return s;
	}

	public static Image[] generateSprites(Image[] s, String str, int w, int h) throws IOException {
		BufferedImage spriteSheet = ImageIO.read(Game.class.getResource(str));
		for (int i = 0; i < s.length; i++) {
			s[i] = spriteSheet.getSubimage(i * w, 0, w, h);
		}
		return s;
	}

	public static Image generateSprite(String str) throws IOException {
		BufferedImage sprite = ImageIO.read(Game.class.getResource(str));
		return sprite;
	}

	public static void drawImage(Image i, double x, double y, double w, double h, Graphics2D g) {
		g.drawImage(i, (int) x, (int) y, (int) w, (int) h, null);
	}

	public static void drawImage(Image i, Rectangle2D r, double s, double t, Graphics2D g) {
		g.drawImage(i, (int) (r.getX() + s), (int) (r.getY() + t), (int) r.getWidth(), (int) r.getHeight(), null);
	}

	public static void main(String args[]) {
		new Game();
	}

	private class RenderTask extends TimerTask {
		Game game;
		Timer timer;

		public RenderTask(Game game, Timer timer) {
			this.game = game;
			this.timer = timer;
		}

		public void run() {
			game.render();
			timer.schedule(new RenderTask(game, timer), 17);
		}

	}
}

class Window extends Canvas {

	JFrame frame;

	private static final long serialVersionUID = 1L;

	public Window(int w, int h, String t, Game game) {
		frame = new JFrame(t);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		frame.add(new KeyBindings(game.p));
		this.setBounds(frame.getBounds());
		game.start();
	}
}