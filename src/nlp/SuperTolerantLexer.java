
package nlp;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.management.RuntimeErrorException;

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

	public void AddToken(String txt, Object id) {
		Token tok = new Token(txt, id);
	}

	Vector<Integer> table = new Vector<Integer>(102400, 10240);
	Vector<Word> match = new Vector<Word>(400, 40);

	public SuperTolerantLexer(Language lang) {
		table = new Vector<Integer>(102400, 10240);
		table.setSize(256);
		match = new Vector<Word>(400, 40);
		match.setSize(1);
		int availablestate = 1;
		for (Word word : lang.dict.words) {
			byte[] bytes = word.word.getBytes(StandardCharsets.UTF_8);
			int state = 0;
			for (int pos = 0; pos < bytes.length; ++pos) {
				int tableIdx = ((int) bytes[pos] & 0xff) + state * 256;
				if (table.get(tableIdx) != null) {
					state = table.get(tableIdx);
				} else {
					table.setElementAt(Integer.valueOf(availablestate), tableIdx);
					state = availablestate;
					table.setSize(table.size() + 256);
					match.setSize(table.size() + 1);
					++availablestate;
				}
			}
			if (match.get(state) != null)
				throw new RuntimeException("The word \"" + word + "\" was enterd twice into the lexer.");
			match.setElementAt(word, state);
		}
	}
}
