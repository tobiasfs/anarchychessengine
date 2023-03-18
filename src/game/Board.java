package game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

public class Board extends Part {
	AbstractGrid grid;

	Board() {
		grid = new SquareGrid();
	}

	@Override
	public void Paint(Graphics g, AffineTransform tr) {
		// TODO Auto-generated method stub
		// rectangle originated at 10,10 and end at 240,240
		g.drawRect(10, 10, 240, 240);
		// filled Rectangle with rounded corners.
		g.fillRoundRect(50, 50, 100, 100, 80, 80);

	}

}
