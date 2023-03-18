package nlp;

import game.Game;
import game.Part;

public class GameParser extends NLParser {

	private Part context;

	public GameParser(SuperTolerantLexer lexer) {
		super(lexer);
	}

	public Game parseGame(String contents) {
		Game game = new Game();

		context = game;

		return game;
	}

}
