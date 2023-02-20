package game;

public class Board extends Part{
	AbstractGrid grid;

	Board() {
		grid = new SquareGrid();
	}
}
