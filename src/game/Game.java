package game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Game extends Part {
	Board board;
	List<Piece> pieces;
	RuleSet pieceDescription;
	RuleSet startCondition;
	RuleSet actions;
	RuleSet endCondition;

	public Game() {
		board = new Board();
		pieces = new ArrayList<Piece>();
	}

	public void Paint(Graphics g, AffineTransform tr) {
		board.Paint(g, tr);
		for (Piece p : pieces)
			p.Paint(g, tr);
	}
}
