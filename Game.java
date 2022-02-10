package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
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

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	Player p;
	LinkedList<Enemy> enemies;
	Background bg;
	ArrayList<Solid> solids = new ArrayList<Solid>();
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	pNode[][] walkable = new pNode[0][0];

	int cooldown = 2000;
	static final int pNodeSize = 10;

	private static final long serialVersionUID = 1L;
	// static final Dimension resolution = new Dimension(240, 160);
	final static Dimension resolution = new Dimension(240, 160);
	Dimension currentSize = getSize();
	Rectangle2D screen = new Rectangle2D.Double(0, 0, 240, 160);
	double scaleX, scaleY;
	static double scrollX, scrollY;
	private Thread thread;
	boolean running = false;

	public Game() {
		p = new Player(360 - 8, 1560, 16, 20, this);
		enemies = new LinkedList<Enemy>();
		enemies.add(new Enemy(300, 900, 16, 20, this));
		try {
			bg = new Background(ImageIO.read(getClass().getResourceAsStream("/game/backgrounds/Tutorial.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		scaleX = scaleY = 1;
		new Window((int) resolution.getWidth(), (int) resolution.getHeight(), "Game", this);
		this.addKeyListener(new KeyInput(p));
	}

	public synchronized void start() {
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

		walkable = new pNode[(int) (bg.hitbox.getWidth() / pNodeSize)][(int) (bg.hitbox.getHeight() / pNodeSize)];

		for (int row = 0; row < walkable.length; row++) {
			for (int col = 0; col < bg.hitbox.getHeight() / pNodeSize; col++) {
				GameObject temp = new GameObject(row * pNodeSize, col * pNodeSize, pNodeSize, pNodeSize);
				walkable[row][col] = new pNode(row, col, temp.collide(solids).size() == 0);
			}
		}

		pNode.linkNodes(walkable);

		for (Enemy e : enemies) {
			e.loadPathfinding();
		}

		thread = new Thread(this);
		thread.start();
		running = true;
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

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		renderTimer.schedule(RENDER, 17);
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
//			if (running)
//				render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}

		}
		stop();
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		currentSize = getSize();
		currentSize.setSize(currentSize.width, currentSize.width);
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		// if ratio is greater than 1.5, it is too wide. if it is less than 1.5, it is
		// too tall
		if ((currentSize.width + 0.0) / currentSize.height > 1.5) {
			scaleX = (currentSize.height * 1.5) / resolution.getWidth();
			scaleY = (currentSize.height + 0.0) / resolution.getHeight();
		} else if ((currentSize.width + 0.0) / currentSize.height < 1.5) {
			scaleX = (currentSize.width + 0.0) / resolution.getWidth();
			scaleY = (currentSize.width / 1.5) / resolution.getHeight();
		} else {
			scaleX = (currentSize.width + 0.0) / resolution.getWidth();
			scaleY = (currentSize.height + 0.0) / resolution.getHeight();
		}
		g2d.scale(scaleX, scaleY);

		g2d.setColor(Color.BLACK);
		g2d.fill(screen);
		bg.render(g2d);
		p.render(g2d);
		for (Enemy e : enemies)
			e.render(g2d);
		for (Bullet b : bullets)
			b.render(g2d);
		for (Solid s : solids)
			s.render(g2d);
		g2d.dispose();

		bs.show();
		tick();
		try {
			Thread.sleep(2);
		} catch (Exception e) {
		}

	}

	public void tick() {
		Iterator<Bullet> bi = bullets.iterator();
		while (bi.hasNext()) {
			Bullet b = bi.next();
			if (b.shooter != p && b.hitbox.intersects(p.hitbox)) {
				bi.remove();
				p.damage(1);
				System.out.println(p.health);
			} else if (!b.onScreen() || b.collide(solids).size() > 0) {
				bi.remove();
			}
		}

		Iterator<Enemy> ei = enemies.iterator();
		while (ei.hasNext()) {
			Enemy e = ei.next();
			for (Bullet B : bullets)
				if (!B.shooter.getClass().equals(e.getClass()) && e.hitbox.intersects(B.hitbox))
					e.alive = false;
			if (e.alive == false) {
				ei.remove();
			}
		}

		scroll();
	}

	public void scroll() {
		scrollX = resolution.getWidth() / 2 - (p.hitbox.getCenterX());
		scrollY = resolution.getHeight() / 2 - (p.hitbox.getCenterY());

		if (cooldown == 0) {
			enemies.add(new Enemy((int) (bg.hitbox.getX() + 300), (int) (bg.hitbox.getY() + 240), 16, 20, this));
			cooldown = 2000;
		} else
			cooldown--;
	}

	public static Image[][] generateSprites(Image[][] s, BufferedImage spriteSheet, int w, int h) {
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				s[i][j] = spriteSheet.getSubimage(i * w, j * h, w, h);
			}
		}
		return s;
	}

	public static void main(String args[]) {
		new Game();
	}

	class RenderTask extends TimerTask {
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window(int w, int h, String t, Game game) {
		JFrame frame = new JFrame(t);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
}
