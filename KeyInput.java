package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.Character.weapon;

public class KeyInput extends KeyAdapter {
	Player player;

	public KeyInput(Player p) {
		player = p;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// System.out.println();

		if (key == KeyEvent.VK_UP)
			player.up = true;
		if (key == KeyEvent.VK_DOWN)
			player.down = true;
		if (key == KeyEvent.VK_RIGHT)
			player.right = true;
		if (key == KeyEvent.VK_LEFT)
			player.left = true;

		if (key == KeyEvent.VK_W)
			player.up = true;
		if (key == KeyEvent.VK_S)
			player.down = true;
		if (key == KeyEvent.VK_D)
			player.right = true;
		if (key == KeyEvent.VK_A)
			player.left = true;
		
		if (key == KeyEvent.VK_SPACE) {
			player.space = true;
			
		}

		if (key == KeyEvent.VK_1)
			player.w = weapon.gun;
		if (key == KeyEvent.VK_2)
			player.w = weapon.sword;
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP)
			player.up = false;
		if (key == KeyEvent.VK_DOWN)
			player.down = false;
		if (key == KeyEvent.VK_RIGHT)
			player.right = false;
		if (key == KeyEvent.VK_LEFT)
			player.left = false;
		
		if (key == KeyEvent.VK_W)
			player.up = false;
		if (key == KeyEvent.VK_S)
			player.down = false;
		if (key == KeyEvent.VK_D)
			player.right = false;
		if (key == KeyEvent.VK_A)
			player.left = false;
		
		if (key == KeyEvent.VK_SPACE) {
			player.space = false;}
	}
}