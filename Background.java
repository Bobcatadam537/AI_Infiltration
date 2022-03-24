
package game;

import java.awt.Graphics2D;
import java.awt.Image;

public class Background extends GameObject {
	Image image;

	public Background(Image i, int x, int y) {
		super(x - 1, y - 1, i.getWidth(null), i.getHeight(null));
		image = i;
		hitbox.setRect(x, y, w, h);
	}

	public void render(Graphics2D g) {
		updateRenderPosition();
		Game.drawImage(image, Xshifted, Yshifted, w, h, g);
	}
}