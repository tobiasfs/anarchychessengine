package nlp;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage for one word used in the construction of the text. The word has one
 * or more Universal Dependencies attached to it (e.g. the word "score" can be a
 * VERB or a NOUN depending on the context).
 * 
 * 
 * 
 */
public class Word {
	String word;
	List<UniversalDependency> ud;
	public String lemma;

	public Word(String word) {
		this.word = word;
		this.lemma = word;
		ud = new ArrayList<UniversalDependency>();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(word);
		if (word.compareTo(lemma) != 0) {
			b.append(" <");
			b.append(lemma);
			b.append(">");
		}
		if (!ud.isEmpty()) {
			b.append(" ");
			b.append(ud.toString());
		}
		return b.toString();
	}

}
