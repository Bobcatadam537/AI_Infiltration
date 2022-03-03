package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
//import java.awt.geom.Line2D;
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
	MENU, RUNNING, GAME_OVER, INSTRUCTIONS, PAUSE, POWERUPDISPLAY, POWERUP1, POWERUP2, POWERUP3
}

enum Level{
	ONE, TWO, THREE, FOUR, FIVE, SIX
}

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */

	GameState state = GameState.MENU;
	int level = 1;
	GameState prevState = GameState.GAME_OVER;

	Menu menu = new Menu(this);
	Font font;
	FontMetrics metrics;

	Player p;
	Door d;
	
	//int level;
	LinkedList<Enemy> enemies;
	Background bg;
	Background lv1;
	Background lv3;
	ArrayList<Solid> solids = new ArrayList<Solid>();
	
	ArrayList<Laser> lasers = new ArrayList<Laser>();

	LinkedList<Bullet> bullets = new LinkedList<Bullet>();

	pNode[][] walkable = new pNode[0][0];
	int cooldown = 2000;
	static final int pNodeSize = 10;
	private static final long serialVersionUID = 1L;
	// static final Dimension resolution = new Dimension(240, 160);
	final static Dimension resolution = new Dimension(240, 160);
	static final Rectangle2D screen = new Rectangle2D.Float(0, 0, 240, 160);
	Dimension currentSize = getSize();
	double scaleX, scaleY;
	static double scrollX, scrollY;
	private Thread thread;
	boolean running = false;
	Window w;
	double width, height;
	
	public Game() {
		font = new Font("courier new", 1, 50);

		p = new Player(360 - 8, 1560, 16, 20, this);
		enemies = new LinkedList<Enemy>();
		enemies.add(new Enemy(300, 900, 16, 20, this));


		try {
			bg = new Background(ImageIO.read(getClass().getResourceAsStream("/game/backgrounds/Tutorial.png")), 0, 0);
			 lv1 = new
			 Background(ImageIO.read(getClass().getResourceAsStream("/game/backgrounds/lv3.png")),
			 0, 0);
			 lv3 = new
			 Background(ImageIO.read(getClass().getResourceAsStream("/game/backgrounds/lv3.png")),
			 0, 0);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		d = new Door((int) (bg.hitbox.getCenterX()-(75/2)), (int) (bg.hitbox.getY() + 100), 75, 75, this, 1);
		//d2 = new Door((int) (d.hitbox.getCenterX() + 990), (int) (d.hitbox.getCenterY()), 20, 20, this, 1);
		scaleX = scaleY = 1;
		w = new Window((int) resolution.getWidth(), (int) resolution.getHeight(), "Game", this);
		width = resolution.getWidth();
		height = resolution.getHeight();
		this.addKeyListener(new KeyInput(p));
		this.addMouseListener(menu);
		this.addMouseListener(p.m);
		//this.addMouseListener();
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
		//makeLvl1();

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

	public void makeLvl2() {
	
		solids.removeAll(solids);
		enemies.removeAll(enemies);
		lasers.removeAll(lasers);
		bullets.removeAll(bullets);
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
		lasers.add(new Laser(191, 1200, 30, 30, (528-191-30), this));
		lasers.add(new Laser(191, 1100, 30, 30, (528-191-30), this));
		lasers.add(new Laser(191, 1000, 30, 30, (528-191-30), this));
		
		enemies.add(new Enemy(300, 1150, 20, 20,2, this));
		
		enemies.add(new Enemy(200, 800, 20, 20, 3, this));
		enemies.add(new Enemy(400, 800, 20, 20, 3, this));
		enemies.add(new Enemy(200, 700, 20, 20, 3, this));
		enemies.add(new Enemy(400, 700, 20, 20, 3, this));

		enemies.add(new Enemy(200, 300, 40, 40, 2, this));
		enemies.add(new Enemy(500, 300, 40, 40, 2, this));
		
		lasers.add(new Laser(264, 150, 20, 20, (455-264-20), this));
		
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
		}
	public void makeLvl3() {
		
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
		//int frames = 0;
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
			//frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				//frames = 0;
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
		Graphics g = bs.getDrawGraphics();

		Graphics2D menuG2D = (Graphics2D) g;

		// if ratio is greater than 1.5, it is too wide. if it is less than 1.5, it is
		// if ratio is greater than 1.5, it is too wide. if it is less than 1.5, it is
		// too tall
		if ((currentSize.width + 0.0) / currentSize.height > 1.5) {
			scaleX = (currentSize.height * 1.5) / resolution.getWidth();
			scaleY = (currentSize.height + 0.0) / resolution.getHeight();
//			g2d.scale((currentSize.height * 1.5) / resolution.width, (currentSize.height + 0.0) / resolution.height);
		} else if ((currentSize.width + 0.0) / currentSize.height < 1.5) {
			scaleX = (currentSize.width + 0.0) / resolution.getWidth();
			scaleY = (currentSize.width / 1.5) / resolution.getHeight();
//			g2d.scale((currentSize.width + 0.0) / resolution.width, (currentSize.width / 1.5) / resolution.height);
		} else {
			scaleX = (currentSize.width + 0.0) / resolution.getWidth();
			scaleY = (currentSize.height + 0.0) / resolution.getHeight();
//			g2d.scale((currentSize.width + 0.0) / resolution.width, (currentSize.height + 0.0) / resolution.height);
		}
		g2d.scale(scaleX, scaleY);

		g2d.setColor(Color.BLACK);
		g2d.fill(screen);


		//menuG2D.draw(new Line2D.Double(new Point2D.Double(*scaleX/2, 0), new Point2D.Double(getSize().width/2, height)));

		
		if (p.health <= 0)
			state = GameState.GAME_OVER;

		if (state == GameState.MENU) {

			menu.render(menuG2D);
			
		}

		if (state == GameState.INSTRUCTIONS) {

			menu.renderInstructions(menuG2D);
		}

		else if (state == GameState.RUNNING) {

			renderGame(g2d);
			menuG2D.setColor(Color.WHITE);
			menuG2D.fill(new Rectangle2D.Float((int)(getSize().width * (21.0/24)), (int)(getSize().height * (1.0/24)), (int)(getSize().width/12),(int)(getSize().width/12)));

			metrics = menuG2D.getFontMetrics(font);
			menuG2D.setFont(new Font("courier new", 1, 30));
			menuG2D.drawString("" + (resolution.getWidth() + ", " + resolution.getHeight()), 100, 100);
			menuG2D.drawString("" + (resolution.getWidth() * scaleX + ", " + resolution.getHeight()*scaleY), 100, 500);
			
			menuG2D.drawString((""+ scaleX + ", " + scaleY), 100, 200);
			menuG2D.drawString((""+ width + ", " + height), 100, 300);
			menuG2D.drawString((""+ p.m.cx + ", " + p.m.cy), 100, 400);
			
			
			menuG2D.draw(new Line2D.Double(new Point2D.Double(p.m.cx, p.m.cy), new Point2D.Double(p.m.mx, p.m.my)));
			menuG2D.draw(new Line2D.Double(new Point2D.Double(p.m.cx, p.m.cy), new Point2D.Double(p.m.mx, p.m.cy)));
			menuG2D.draw(new Line2D.Double(new Point2D.Double(p.m.cx, p.m.cy), new Point2D.Double(p.m.cx, p.m.my)));

			//g2d.draw(new Line2D.Double(new Point2D.Double((p.hitbox.getCenter`X()+ scrollX-1), (p.hitbox.getCenterY())+scrollY-1), new Point2D.Double(p.mouseX + scrollX-1, p.mouseY + scrollY-1)));
			
		}

		else if (state == GameState.PAUSE) {

			menu.renderPause(menuG2D);

		}

		else if (state == GameState.GAME_OVER) {

			menu.renderGameOver(menuG2D);
			// stop();

		} else if (state == GameState.POWERUP1) {
			menu.renderPowerMenu1(menuG2D);
		}
	 else if (state == GameState.POWERUP2) {
		menu.renderPowerMenu2(menuG2D);
	}
	 else if (state == GameState.POWERUPDISPLAY) {
		menu.renderPowerDisplay(menuG2D);
	}
	
	
		
		menuG2D.setColor(Color.RED);

		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width/2, 0), new Point2D.Double(getSize().width/2, getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height/2), new Point2D.Double(getSize().width, getSize().height/2)));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width/3, 0), new Point2D.Double(getSize().width/3, getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height/3), new Point2D.Double(getSize().width, getSize().height/3)));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width*(2.0/3), 0), new Point2D.Double(getSize().width* (2.0/3), getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height * (2.0/3)), new Point2D.Double(getSize().width, getSize().height * (2.0/3))));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width/6, 0), new Point2D.Double(getSize().width/6, getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height/6), new Point2D.Double(getSize().width, getSize().height/6)));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width*(5/6.0), 0), new Point2D.Double(getSize().width*(5.0/6), getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height* (5.0/6)), new Point2D.Double(getSize().width, getSize().height*(5.0/6))));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width/12, 0), new Point2D.Double(getSize().width/12, getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height/12), new Point2D.Double(getSize().width, getSize().height/12)));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width*(11.0/12), 0), new Point2D.Double(getSize().width*(11.0/12), getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height* (11/12.0)), new Point2D.Double(getSize().width, getSize().height*(11.0/12))));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width/4, 0), new Point2D.Double(getSize().width/4, getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height/4), new Point2D.Double(getSize().width, getSize().height/4)));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width*(3/4.0), 0), new Point2D.Double(getSize().width*(3.0/4), getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height* (3/4.0)), new Point2D.Double(getSize().width, getSize().height*(3.0/4))));
		
		
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width*(7/12.0), 0), new Point2D.Double(getSize().width*(7.0/12), getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height* (7.0/12)), new Point2D.Double(getSize().width, getSize().height*(7.0/12))));
		
		menuG2D.draw(new Line2D.Double(new Point2D.Double(getSize().width*(5/12.0), 0), new Point2D.Double(getSize().width*(5.0/12), getSize().height)));
		menuG2D.draw(new Line2D.Double(new Point2D.Double(0, getSize().height* (5.0/12)), new Point2D.Double(getSize().width, getSize().height*(5.0/12))));
		
		
		System.out.println(getSize().width + " " + getSize().height);
		
		g2d.dispose();

		bs.show();
		tick();
		try {
			Thread.sleep(2);
		} catch (Exception e) {
		}
		
		
	}

	public void renderGame(Graphics2D g2d) {
		if(level ==1)
		bg.render(g2d);
		if(level == 2)
		lv3.render(g2d);
		p.render(g2d);
		d.render(g2d);
		//d2.render(g2d);
		for (Enemy e : enemies)
			e.render(g2d);
		
		for (Laser l : lasers) {
			l.render(g2d);
		}
		
		for(Solid s : solids) {s.render(g2d);}
		
		g2d.setColor(Color.WHITE);

		// g2d.fill(new Rectangle2D.Float(200, 25, 10, 10));
		for (Bullet b : bullets) {
			b.render(g2d);
		}
		

	}

	public void tick() {
		Iterator<Bullet> bi = bullets.iterator();
		while (bi.hasNext()) {
			Bullet b = bi.next();
			if (b.shooter != p && b.hitbox.intersects(p.hitbox)) {
				bi.remove();
				p.damage(5);
				// System.out.println(p.health);
			} else if (!b.onScreen() || b.collide(solids).size() > 0) {
				bi.remove();
			}

		}

		Iterator<Enemy> ei = enemies.iterator();
		while (ei.hasNext()) {
			Enemy e = ei.next();
			for (Bullet B : bullets)
				if (!B.shooter.getClass().equals(e.getClass()) && e.hitbox.intersects(B.hitbox)) {
					e.takeDamage(B.damage);
					B.damage =0;
				}
			if (e.alive == false) {
				ei.remove();
			}

		}
		for (Bullet b : bullets) {
			for (Laser l : lasers) {
				if (l.hit1.hitbox.intersects(b.hitbox)) {
					l.deactivate(l.hit1);
				}
				if (l.hit2.hitbox.intersects(b.hitbox)) {
					l.deactivate(l.hit2);
				}
				if(l.beam.hitbox.intersects(b.hitbox)) {
					b.damage = 0;
				}
			}
		}

		scroll();
	}

	public void scroll() {
		// float topBorder = 240, bottomBorder = 240, leftBorder = 360, rightBorder =
		// 360, movex = 0, movey = 0;

		scrollX = resolution.getWidth() / 2 - (p.hitbox.getCenterX());
		scrollY = resolution.getHeight() / 2 - (p.hitbox.getCenterY());

//		bg.scroll(scrollX, scrollY);
//		lv1.scroll(scrollX, scrollY);
//		lv3.scroll(scrollX, scrollY);
//		p.scroll(scrollX, scrollY);
//		d.scroll(scrollX, scrollY);
//		d2.scroll(scrollX, scrollY);
//		for (Enemy e : enemies)
//			e.scroll(scrollX, scrollY);
//		for (Bullet b : bullets)
//			b.scroll(scrollX, scrollY);
//		for (Solid s : solids)
//			s.scroll(scrollX, scrollY);
//		for(Laser l : lasers)
//			l.scroll(scrollX, scrollY);

//		if (cooldown == 0) {
//			enemies.add(new Enemy((int) (bg.hitbox.getX() + 300), (int) (bg.hitbox.getY() + 240), 16, 20, this));
//			cooldown = 2000;
//		} else
			//cooldown--;
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
	int wid;
	int hit;
	public Window(int w, int h, String t, Game game) {
		JFrame frame = new JFrame(t);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		wid = frame.getWidth();
		hit = frame.getHeight();
		frame.setVisible(true);
		game.start();
	}
	public double getW() {
		return wid;
	}
	public double getH() {
		return hit;
	}
	
	
}