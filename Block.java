package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends GameObject {
	public Block() {
		super(100, 100, 50, 50);
		dy = dx = 2;

	}

	public void render(Graphics g) {
		g.setColor(new Color(255, 255, 255, 150));
		g.fillRect(x, y, w, h);

	}

	public void tick() {
		if (Game.p.north) {
			y += Game.p.dy;
		}
		if (Game.p.south) {
			y += Game.p.dy;
		}
		if (Game.p.east || Game.p.west) {
			x += Game.p.dx;
		}
	}
}
