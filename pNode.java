package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class pNode extends GameObject implements Comparable<pNode> {
	int cost = 0;
	boolean open;
	ArrayList<pNode> neighbors;
	pNode parent, child;
	int x, y;

	public pNode(int x, int y, boolean o) {
		super(x * Game.pNodeSize, y * Game.pNodeSize, Game.pNodeSize, Game.pNodeSize);
		neighbors = new ArrayList<pNode>();
		parent = child = null;
		open = o;
		this.x = x;
		this.y = y;
	}

	public pNode maxParent() {
		if (parent == null)
			return this;
		return parent.maxParent();
	}

	public pNode maxParent(Enemy e) {
		if (parent == null || parent.hitbox.intersects(e.hitbox))
			return this;
		return parent.maxParent(e);
	}

	public pNode removeParent() {
		pNode temp = parent;
		parent = null;
		return temp;
	}

	public String toString() {
		// return (parent == null ? "" : parent.pathCoords()) + " -> [" + x + ", " + y +
		// "]";
		return " -> [" + x + ", " + y + "]";
	}

	public void testCost() {
		System.out.println(cost);
		if (parent != null)
			parent.testCost();
	}

	public String pathCoords() {
		return (parent == null ? "" : parent.pathCoords()) + " -> [" + x + ", " + y + "]";
	}

	public static void linkNodes(pNode[][] walkable) {
		for (int row = 0; row < walkable.length; row++) {
			for (int col = 0; col < walkable[0].length; col++) {
				// up, upright, right, downright, down, downleft, left, upleft
				if (row > 0)
					walkable[row][col].neighbors.add(walkable[row - 1][col]);
				if (row > 0 && col < walkable[0].length - 1)
					walkable[row][col].neighbors.add(walkable[row - 1][col + 1]);
				if (col < walkable[0].length - 1)
					walkable[row][col].neighbors.add(walkable[row][col + 1]);
				if (row < walkable.length - 1 && col < walkable[0].length - 1)
					walkable[row][col].neighbors.add(walkable[row + 1][col + 1]);
				if (row < walkable.length - 1)
					walkable[row][col].neighbors.add(walkable[row + 1][col]);
				if (row < walkable.length - 1 && col > 0)
					walkable[row][col].neighbors.add(walkable[row + 1][col - 1]);
				if (col > 0)
					walkable[row][col].neighbors.add(walkable[row][col - 1]);
				if (row > 0 && col > 0)
					walkable[row][col].neighbors.add(walkable[row - 1][col - 1]);
			}
		}
	}

	public static pNode[][] copy(pNode[][] walkable) {
		pNode[][] copy = new pNode[walkable.length][walkable[0].length];
		for (int row = 0; row < walkable.length; row++) {
			for (int col = 0; col < walkable[0].length; col++) {
				copy[row][col] = new pNode(row, col, walkable[row][col].open);
			}
		}
		linkNodes(copy);
		return copy;
	}

	public int compareTo(pNode other) {
		return cost - other.cost;
	}

	public void render(Graphics2D g2d) {
		g2d.setColor(Color.yellow);
		g2d.draw(new Rectangle2D.Double(x * Game.pNodeSize + Game.scrollX, y * Game.pNodeSize + Game.scrollY,
				Game.pNodeSize, Game.pNodeSize));
		if (parent != null)
			parent.render(g2d);
	}
}
