package game;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import game.Character.WEAPON;

public class KeyBindings extends JLabel {
	private enum ACT {
		START_UP, STOP_UP, START_DOWN, STOP_DOWN, START_LEFT, STOP_LEFT, START_RIGHT, STOP_RIGHT, START_ATTACK,
		STOP_ATTACK, SELECT_1, SELECT_2, QUIT;
	}

	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	Player player;

	public KeyBindings(Player p) {
		player = p;
		// mapping for up
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), ACT.START_UP);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), ACT.STOP_UP);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), ACT.START_UP);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), ACT.STOP_UP);
		// mapping for down
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), ACT.START_DOWN);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), ACT.STOP_DOWN);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), ACT.START_DOWN);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), ACT.STOP_DOWN);
		// keys for left
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), ACT.START_LEFT);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), ACT.STOP_LEFT);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), ACT.START_LEFT);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), ACT.STOP_LEFT);
		// keys for right
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), ACT.START_RIGHT);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), ACT.STOP_RIGHT);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), ACT.START_RIGHT);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), ACT.STOP_RIGHT);
		// keys for attack
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), ACT.START_ATTACK);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), ACT.STOP_ATTACK);
		// keys for weapon switching
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke("1"), ACT.SELECT_1);
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke("2"), ACT.SELECT_2);
		// quit game
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke("ESCAPE"), ACT.QUIT);
		// action setup
		for (ACT action : ACT.values())
			this.getActionMap().put(action, new Action(action));
	}

	private class Action extends AbstractAction {
		ACT action;

		Action(ACT a) {
			action = a;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (action) {
			case START_DOWN:
				player.down = true;
				break;
			case START_LEFT:
				player.left = true;
				break;
			case START_RIGHT:
				player.right = true;
				break;
			case START_UP:
				player.up = true;
				break;
			case STOP_DOWN:
				player.down = false;
				break;
			case STOP_LEFT:
				player.left = false;
				break;
			case STOP_RIGHT:
				player.right = false;
				break;
			case STOP_UP:
				player.up = false;
				break;
			case START_ATTACK:
				player.space = true;
				break;
			case STOP_ATTACK:
				player.space = false;
				break;
			case SELECT_1:
				player.weapon = WEAPON.gun;
				break;
			case SELECT_2:
				player.weapon = WEAPON.sword;
				break;
			case QUIT:
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}
}