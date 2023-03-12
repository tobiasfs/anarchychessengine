
package nlp;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

/**
 * <b>Super Tolerant Lexer</b>. This lexer is first set up by handing in words
 * and enums. It only processes full words, no regular expressions. The target
 * of this lexer is the processing of natural language with oh-so-many errors.
 * The processing is accomplished by creating a table lexer on the fly. This
 * lexer is full of alternatives and shortcuts.
 */
public class SuperTolerantLexer {

	Vector<Integer> table = new Vector<Integer>(102400, 10240);
	Vector<Word> match = new Vector<Word>(400, 40);
	private byte[] bytes;
	private int pos;
	private int row;
	private int col;
	private Language lang;
	private Word wordX;

	public SuperTolerantLexer(Language lang) {
		this.lang = lang;
		this.wordX = new Word("");
		this.wordX.ud.add(new UniversalDependency("X"));
		
		final int initialStateCount = 2;

		table = new Vector<Integer>(102400, 10240);
		table.setSize(256 * initialStateCount);
		match = new Vector<Word>(400, 40);
		match.setSize(initialStateCount);

		int nextFreeState = initialStateCount + 1;

		// Loop all whitespace characters back to state 0
		for (int idx = 1; idx <= 32; ++idx)
			table.setElementAt(Integer.valueOf(0), idx);

		// Add garbage collector state 1 to state 0
		for (int idx = 33; idx <= 127; ++idx)
			table.setElementAt(Integer.valueOf(1), idx);

		
		match.setElementAt(wordX, 1);
		for (int idx = 'A'; idx <= 'A'; ++idx)
			table.setElementAt(Integer.valueOf(1), 256 + idx);
		for (int idx = 'a'; idx <= 'z'; ++idx)
			table.setElementAt(Integer.valueOf(1), 256 + idx);
		for (int idx = 128; idx <= 255; ++idx)
			table.setElementAt(Integer.valueOf(1), 256 + idx);

		// Add all words from the dictionary
		for (Word word : lang.dict.words) {
			nextFreeState = match.size();
			int state = findState(word, nextFreeState);

			Word next = match.get(state);
			if (next != null && !next.Is(UniversalDependency.Tag.X))
				throw new RuntimeException("The word \"" + word + "\" was already enterd into the lexer.");
			match.setElementAt(word, state);
		}

		// TODO Robustness improvement by adding additional forward links.
		// TODO Robustness improvement by adding character repetition rules.
	}

	/**
	 * Add a word into the lexer. This function is intended, that when during the
	 * parsing of a text a new token in encountered the word in the token can be
	 * added to the lexer to be recognized as a word afterwards. </br>
	 * <b>Note:</b> Words already in the lexer are overwritten.
	 * 
	 * @param word Word to add.
	 */
	public void addWord(Word word) {
		int nextFreeState = match.size();
		int state = findState(word, nextFreeState);
		match.setElementAt(word, state);
	}

	/**
	 * Find the state in the lexer for a given word.
	 * 
	 * @param word          Word object with a new word to be added to the lexer.
	 * @param nextFreeState Position of the next free state in the table.
	 * @return State, where the word will end. This can be an old state or a newly
	 *         added state.
	 */
	private int findState(Word word, int nextFreeState) {
		byte[] bytes = word.word.getBytes(StandardCharsets.UTF_8);
		int state = 0;
		for (int pos = 0; pos < bytes.length; ++pos) {
			int stateOffset = state * 256;
			int value = (int) bytes[pos] & 0xff;
			int tableIdx = value + stateOffset;
			boolean onlyUppercase = lang.isUpper(value);

			Integer nextState = table.get(tableIdx);
			// Skip the garbage state 1 otherwise Garbage plus some word from the 2nd char
			// on would match. (e.g. 'xyzight' would be match 'Knight'.)
			if (nextState != null && nextState.intValue() >= 2) {
				state = nextState.intValue();
			} else {
				table.setElementAt(Integer.valueOf(nextFreeState), tableIdx);
				if (!onlyUppercase) {
					int valueUpper = lang.toUpper(value);
					int tableIdxUpper = valueUpper + stateOffset;
					table.setElementAt(Integer.valueOf(nextFreeState), tableIdxUpper);
				}
				// Create entry that is pointed to from state.
				int offset = table.size();
				table.setSize(offset + 256);
				// Initialize the new cells in the table for garbage collection.
				for (int idx = 'A'; idx <= 'A'; ++idx)
					table.setElementAt(Integer.valueOf(1), offset + idx);
				for (int idx = 'a'; idx <= 'z'; ++idx)
					table.setElementAt(Integer.valueOf(1), offset + idx);
				for (int idx = 128; idx <= 255; ++idx)
					table.setElementAt(Integer.valueOf(1), offset + idx);
				match.setSize(match.size() + 1);
				match.setElementAt(wordX, match.size()-1);
				state = nextFreeState;
				++nextFreeState;
			}
		}
		return state;
	}

	public void initLexing(String contents) {
		bytes = contents.getBytes(StandardCharsets.UTF_8);
		pos = 0;
		row = 1;
		col = 1;
	}

	/**
	 * Reads the next token from the input text. The token is searched against the
	 * dictionary. If found a new token is returned with the word from the text and
	 * also with the universal Dependencies, that are associated to the word.
	 * 
	 * If a word is not in the dictionary, this lexer returns the next word from the
	 * input text as a Universal Dependency token marked as 'X'. This UD indicates,
	 * that the word was not parsable. Here it means, that it is missing from the
	 * dictionary.
	 * 
	 * @return Token with one or more Universal Dependencies assigned to it. The UDs
	 *         are taken from the dictionary.
	 */
	public Token nextToken() {

		int state = 0;
		int startPos = pos;
		int parenthesis = 0;
		while (pos < bytes.length) {
			int value;
			if (pos < bytes.length)
				value = (int) bytes[pos] & 0xff;
			else
				value = 0;
			if (value == '(')
				parenthesis++;
			if (value == ')' && parenthesis > 0)
				parenthesis--;
			if (parenthesis == 0 && value != ')') {
				int tableIdx = value + state * 256;
				Integer nextState = table.get(tableIdx);

				if (nextState == null) {
					Word word = match.get(state);
					// If there is nothing associated to the word, something went wrong. At least a
					// garbage token should have been found.
					//TODO 'passing' is a word, 'pass' results in an exception, it should return and 'X'-UD.
					if (word == null)
						throw new RuntimeException(
								String.format("The word at row %1$d col %2$d could not be read.", row, col));

					// If interpunctuation or whitespace is found, pack the parsed text into a token
					// and return it.
					Token token = new Token(new String(Arrays.copyOfRange(bytes, startPos, pos)), word);
					return token;
				} else {
					state = nextState.intValue();
				}
			}
			pos++;

			// Positional pointer in text.
			if ((value >= 32 && value <= 127) || (value >= 192 && value <= 244))
				col++;
			if (value == 9)
				col = col + (4 - (col % 4));
			if (value == 10) {
				row++;
				col = 0;
			}
			// Read the whitespace and discard it by shifting the startPos.
			if (value <= 32)
				startPos = pos;
		}
		return null;
	}
}
