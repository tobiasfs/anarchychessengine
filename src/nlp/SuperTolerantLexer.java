
package nlp;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Super Tolerant Lexer</b>. This lexer is first set up by handing in words
 * and enums. It only processes full words, no regular expressions. The target
 * of this lexer is the processing of natural language with oh-so-many errors.
 * The processing is accomplished by creating a table lexer on the fly. This
 * lexer is full of alternatives and shortcuts.
 */
public class SuperTolerantLexer {
	private class Token {
		private String txt;
		private Object id;

		public Token(String txt, Object id) {
			this.txt = txt;
			this.id = id;
		}

		public String getTxt() {
			return txt;
		}

		public Object getId() {
			return id;
		}

	}

	private List<Token> token = new ArrayList<Token>();

	public SuperTolerantLexer() {
	}

	public void AddToken(String txt, Object id) {
		Token tok = new Token(txt, id);
	}

}
