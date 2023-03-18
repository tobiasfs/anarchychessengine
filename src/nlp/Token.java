package nlp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import nlp.UniversalDependency.Tag;

/**
 * The tokens are returned from the {@link SuperTolerantLexer}. They contain
 * fields to store the {@link UniversalDependency} that has been identified for
 * the given token in respect to its position in the sentence. (The
 * {@link Word}s contain all possible Universal Dependencies.) Stored within the
 * token is also the relation of the token to the other tokens of the sentence.
 * The main token of a sentence is its VERB.
 * 
 * @see Token.Relation
 * @see <a href=
 *      "https://universaldependencies.org/u/dep/index.html">https://universaldependencies.org/u/dep/index.html</a>
 * 
 * @author Toby
 */

public class Token {
	/**
	 * Token relations in a sentence. The relations are all together in one enum
	 * here. Normally they are grouped into core relations (subject-, verb-, object-
	 * related), Non-core dependent and noun dependent. Also there is the PARATAXIS
	 * relation, that is used to connect tokens across sentences.
	 * <p>
	 * Taken from <a href=
	 * "https://universaldependencies.org/u/dep/index.html">https://universaldependencies.org/u/dep/index.html</a>
	 */
	enum Relation {
		NSUBJ, OBJ, IOBJ, CSUBJ, CCOMP, XCOMP, OBL, VOCATIVE, EXPL, DISLOCATED, ADVCL, ADVMOD, DISCOURSE, AUX, COP,
		MARK, NMOD, APPOS, NUMMOD, ACL, AMOD, DET, CLF, CASE, CONJ, CC, PARATAXIS, FLAT, PUNCT
	}

	private String text;
	public String lemma;
	public Word word;
	private UniversalDependency ud = null;
	public Map<Relation, Token> relations;
	public int row;
	public int col;

	public Token(String txt, Word word) {
		this.text = txt;
		this.word = word;
		if (word.lemma.isEmpty())
			this.lemma = txt;
		else
			this.lemma = word.lemma;
		relations = new HashMap<Relation, Token>();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (relations.isEmpty()) {
			builder.append(text);
			builder.append(" ");
			builder.append(word);
		} else {
			builder.append('(');
			builder.append(lemma);
			builder.append(' ');
			boolean first = true;
			for (Entry<Relation, Token> p : relations.entrySet()) {
				if (first)
					first = false;
				else
					builder.append(", ");
				builder.append(p.getKey());
				builder.append(':');
				builder.append(p.getValue().toString());
			}
			builder.append(')');
		}
		return builder.toString();
	}

	/**
	 * Checks if the {@link Word} in this token has an {@link UniversalDependency}
	 * of the given type.
	 * 
	 * @param tag {@link UniversalDependency.Tag}
	 * @return Boolean, if the tag was found anywhere
	 */
	public boolean Is(Tag tag) {
		if (ud != null)
			return ud.Is(tag);
		for (UniversalDependency ud : word.ud)
			if (ud.Is(tag))
				return true;
		return false;
	}

	/**
	 * Checks if the {@link Word} in this token has an {@link UniversalDependency}
	 * of the given type and has a key-value pair, that macthces the given one. For
	 * example there might be more than one tag for VERB but with different
	 * VerbForms.
	 * 
	 * @param tag   {@link UniversalDependency.Tag}
	 * @param key   String with key
	 * @param value String with value for the given key
	 * @return
	 */
	public boolean Is(Tag tag, String key, String value) {
		if (ud != null)
			return ud.Is(tag, key, value);
		for (UniversalDependency ud : word.ud)
			if (ud.Is(tag, key, value))
				return true;
		return false;
	}

	/**
	 * Fix a Word to a particular {@link UniversalDependency}. Each word has several
	 * possible Universal Dependencies in the Dictionary. Based on the outcome of
	 * the parsing one of these is selected. The following function sets the token
	 * to the needed one.
	 * 
	 * @param tag {@link UniversalDependency.Tag}
	 */
	public void fix(Tag tag) {
		if (ud != null) {
			if (!ud.Is(tag))
				throw new RuntimeException("For the word " + this.toString()
						+ ", the tag is already fixed and cannot be changed to \"" + tag.toString() + "\".");
			return;
		}
		for (UniversalDependency ud : word.ud)
			if (ud.Is(tag)) {
				setDependency(ud);
				return;
			}
		throw new RuntimeException("The word " + this.toString() + " has no possible universal dependency named \""
				+ tag.toString() + "\".");
	}

	/**
	 * Fix a Word to a particular {@link UniversalDependency}. Each word has several
	 * possible Universal Dependencies in the Dictionary. Based on the outcome of
	 * the parsing one of these is selected. The following function sets the token
	 * to the needed one.
	 * 
	 * @param tag   {@link UniversalDependency.Tag}
	 * @param key   String with a key in the Universal Dependency
	 * @param value Value in that key
	 */
	public void fix(Tag tag, String key, String value) {
		if (ud != null) {
			if (!ud.Is(tag, key, value))
				throw new RuntimeException("For the word " + this.toString()
						+ ", the tag is already fixed and cannot be changed to \"" + tag.toString() + "\".");
			return;
		}
		for (UniversalDependency ud : word.ud)
			if (ud.Is(tag, key, value)) {
				setDependency(ud);
				return;
			}
		throw new RuntimeException("The word " + this.toString() + " has no possible universal dependency named \""
				+ tag.toString() + "\".");
	}

	private void setDependency(UniversalDependency ud) {
		this.ud = ud;
		String lemma = ud.get("lemma");
		if (!lemma.isEmpty())
			this.lemma = lemma;
	}

}
