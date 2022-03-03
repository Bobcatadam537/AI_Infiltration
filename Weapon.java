package game;

public class Weapon extends GameObject {
	boolean selected;

	public Weapon(int x, int y, int w, int h) {
		super(x, y, w, h);
		selected = false;
	}

	public boolean hitE(Enemy e) {
		return this.hitbox.intersects(e.hitbox);
			
	}
	public boolean hitP(Player p) {
		return this.hitbox.intersects(p.hitbox);
			
	}
	
}