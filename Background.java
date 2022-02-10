
package game;

import java.awt.Graphics2D;
import java.awt.Image;

public class Background extends GameObject {
	Image image;

	public Background(Image i, int x, int y) {
		super(x, y, 0, 0);
		image = i;
		hitbox.setRect(x, y, image.getWidth(null), image.getHeight(null));
	}

	public void render(Graphics2D g) {
		g.drawImage(image, (int) hitbox.getX(), (int) hitbox.getY(), image.getWidth(null), image.getHeight(null), null);
		System.out.println(image.getWidth(null) + " " + image.getHeight(null));
	}
}