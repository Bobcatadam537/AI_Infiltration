package game;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameObject {
	Rectangle2D hitbox;
	double x, y, w, h;
	int Xshifted, Yshifted;
	double angle = 0; // angles are in radians
	LinkedList<Line2D> touching;

	public GameObject(int x, int y, int w, int h) {
		hitbox = new Rectangle2D.Double(x, y, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		touching = new LinkedList<Line2D>();
		Xshifted = (int) (x + Game.scrollX);
		Yshifted = (int) (y + Game.scrollY);
	}

	public LinkedList<Line2D> collide(ArrayList<Solid> S) {
		touching = new LinkedList<Line2D>();
		for (Solid s : S) {
			for (Line2D line : s.lines) {
				if (line.intersects(hitbox))
					touching.add(line);
			}
		}
		return touching;
	}

	public void updateRenderPosition() {
		Xshifted = (int) (x + Game.scrollX);
		Yshifted = (int) (y + Game.scrollY);
	}

	public void tick() {

	}

	public void aimAt(GameObject o) {
		angle = Math.atan2(o.hitbox.getCenterY() - hitbox.getCenterY(), o.hitbox.getCenterX() - hitbox.getCenterX());
	}

	public boolean onScreen() {
		return x + Game.scrollX < 240 && x + w + Game.scrollX > 0 && y + Game.scrollY < 240 && y + w + Game.scrollY > 0;
	}
}