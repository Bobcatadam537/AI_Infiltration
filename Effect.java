package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class Effect extends GameObject {
	enum effectType {
		explosion(.99, 200, 282, 17, "explosion");

		public double speed;
		public int width, height, numFrames;
		public String name;

		effectType(double s, int w, int h, int n, String str) {
			speed = s;
			width = w;
			height = h;
			numFrames = n;
			name = str;
		}
	}

	Image[] animation;
	double ani = 0;
	effectType type;

	public Effect(int x, int y, int w, int h, effectType t) {
		super(x, y, w, h);
		type = t;
		animation = new Image[type.numFrames];
		try {
			animation = Game.generateSprites(animation, "/game/spriteSheets/" + type.name + ".png", type.width,
					type.height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Effect(Rectangle2D rect, effectType t) {
		super((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
		type = t;
		animation = new Image[type.numFrames];
		try {
			animation = Game.generateSprites(animation, "/game/spriteSheets/" + type.name + ".png", type.width,
					type.height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g) {
		updateRenderPosition();
		g.drawImage(animation[(int) ani], Xshifted, Yshifted, (int) hitbox.getWidth(), (int) hitbox.getHeight(), null);
	}
}
