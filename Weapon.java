package game;

public class Weapon extends GameObject {
	boolean selected;
	int damage;

	public Weapon(int x, int y, int w, int h) {
		super(x, y, w, h);
		selected = false;
	}

	public boolean hitE(Enemy e) {
		if (this.hitbox.intersects(e.hitbox)) {
			return true;
		}
		return false;
	}
}