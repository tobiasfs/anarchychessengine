package game;

import java.util.ArrayList;
import java.util.List;

public class Game extends Part{
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
}
