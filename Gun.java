package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Gun extends Weapon {
	ArrayList<Bullet> bList = new ArrayList<Bullet>();
	int cooldown = 0;
	int direction = 1; // 1 = north, 2 = south, 3 = east, 4 = west
	Player player;

	public Gun(int x, int y, int w, int h, Player p) {
		super(x, y, w, h);
		selected = false;
		player = p;
	}

	public void render(Graphics2D g) {
		if (selected) {
			g.setColor(Color.CYAN);
			g.fill(hitbox);
		}
		tick();
	}

	public void tick() {

		if (selected && player.space) {
			if (Math.toDegrees(player.angle) >= -90 - 45 && Math.toDegrees(player.angle) <= -90 + 45)
				System.out.print("up");
			if (Math.toDegrees(player.angle) >= 0 - 45 && Math.toDegrees(player.angle) <= 0 + 45)
				System.out.print("right");
			if (Math.toDegrees(player.angle) >= 90 - 45 && Math.toDegrees(player.angle) <= 90 + 45)
				System.out.print("down");
			if (Math.toDegrees(player.angle) >= 180 - 45 || Math.toDegrees(player.angle) <= -180 + 45)
				System.out.print("left");
			System.out.println("gun");
		}
	}

	public void fire() {
//		if (player.space) {
//			bList.add(0, new Bullet(x, y, direction));
//			bList.get(0).tick();
//			for (Enemy e : Game.eList) {
//				if (bList.get(bList.size() - 1).hitE(e)) {
//					e.alive = false;
//				}
//			}
//		} else {
//			bList.removeAll(bList);
//		}
	}

}