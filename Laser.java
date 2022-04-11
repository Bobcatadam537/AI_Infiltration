package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import game.Effect.effectType;
//import java.awt.geom.Rectangle2D;

public class Laser extends GameObject {
	GameObject hit1;
	GameObject hit2;
	Solid leftBase, rightBase;
	GameObject beam;
	Game game;
	boolean deactivated1;
	boolean deactivated2;
	Image[] Left = new Image[2];
	Image[] Right = new Image[2];
	Image Beam;

	public Laser(int x, int y, int l, int h, Game g) {
		super(x, y, l + h, h);
		game = g;
		ArrayList<Point2D> temp = new ArrayList<Point2D>();
		temp.add(new Point2D.Double(x, y));
		temp.add(new Point2D.Double(x, y + h));
		temp.add(new Point2D.Double(x + h - 8, y + h));
		temp.add(new Point2D.Double(x + h, y + h - 8));
		temp.add(new Point2D.Double(x + h, y + 8));
		temp.add(new Point2D.Double(x + h - 8, y));
		temp.add(new Point2D.Double(x, y));
		leftBase = new Solid(temp);
		game.solids.add(leftBase);

		temp = new ArrayList<Point2D>();
		temp.add(new Point2D.Double(x + l + h, y));
		temp.add(new Point2D.Double(x + l + h, y + h));
		temp.add(new Point2D.Double(x + l + 8, y + h));
		temp.add(new Point2D.Double(x + l, y + h - 8));
		temp.add(new Point2D.Double(x + l, y + 8));
		temp.add(new Point2D.Double(x + l + 8, y));
		temp.add(new Point2D.Double(x + l + h, y));
		rightBase = new Solid(temp);
		game.solids.add(rightBase);

		hit1 = new GameObject(x, y, h, h);
		hit2 = new GameObject(x + l, y, 30, 30);
		beam = new GameObject(x + 12, y + 12, l, 6);
		deactivated1 = deactivated2 = false;

		try {
			Left[0] = Game.generateSprite("/game/spriteSheets/Laser/laserBaseL.png");
			Left[1] = Game.generateSprite("/game/spriteSheets/Laser/laserBaseLD.png");
			Right[0] = Game.generateSprite("/game/spriteSheets/Laser/laserBaseR.png");
			Right[1] = Game.generateSprite("/game/spriteSheets/Laser/laserBaseRD.png");
			Beam = Game.generateSprite("/game/spriteSheets/Laser/laserBeam.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g) {
		hit1.updateRenderPosition();
		hit2.updateRenderPosition();
		beam.updateRenderPosition();

		Game.drawImage(Left[deactivated1 ? 1 : 0], hit1.Xshifted, hit1.Yshifted, hit1.w, hit1.h, g);
		Game.drawImage(Right[deactivated2 ? 1 : 0], hit2.Xshifted, hit2.Yshifted, hit2.w, hit2.h, g);
		if (!(deactivated1 && deactivated2))
			Game.drawImage(Beam, beam.Xshifted, beam.Yshifted, beam.w, beam.h, g);

		tick();

	}

	public void deactivate(GameObject g) {
		if (!deactivated1 && g == hit1) {
			deactivated1 = true;
			game.effects.add(new Effect(hit1.hitbox, effectType.explosion));
		} else if (!deactivated2 && g == hit2) {
			deactivated2 = true;
			game.effects.add(new Effect(hit2.hitbox, effectType.explosion));
		}

		if (deactivated1 == deactivated2 == true)
			beam.hitbox = new Rectangle2D.Double(0, 0, 0, 0);
	}

	public void tick() {
//		collideV(hit1);
//		collideV(hit2);
//		collideH(hit1);
//		collideH(hit2);

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
			}
		}
	}
}