package nlp;

import java.util.ArrayList;
import java.util.List;

import nlp.UniversalDependency.Tag;

/**
 * Storage for one word used in the construction of the text. The word has one
 * or more Universal Dependencies attached to it (e.g. the word "score" can be a
 * VERB or a NOUN depending on the context).
 * 
 * 
 * 
 * @author Toby
 */
public class Word {
	String word;
	List<UniversalDependency> ud;
	public String lemma;

	/**
	 * Constructor for a word. The String passed in is used for the word as well as
	 * the lemma. If later a particular {@link UniversalDependency} is selected
	 * (with {@link Token#fix(Tag)} the lemma in that Universal Dependency is
	 * overwritten.
	 * 
	 * @param word String with word.
	 */
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

//	private UniversalDependency getUD(String tag) {
////		for (UniversalDependency u : ud)
////			if (u.Is(tag))
////				return u;
//		return null;
//	}
//
//	private UniversalDependency getUD(String tag, String key, String value) {
////		for (UniversalDependency u : ud)
////			if (u.Is(tag)) {
////				String ref = u.get(key);
////				if (ref.compareToIgnoreCase(value) == 0)
////					return u;
////			}
//		return null;
//	}
//
//	private boolean Is(String tag) {
//		UniversalDependency u = getUD(tag);
//		return u != null;
//	}

	public boolean Is(Tag tag, String key, String value) {
		for (UniversalDependency u : ud)
			if (u.Is(tag, key, value))
				return true;
		return false;
	}

	public boolean Is(Tag tag) {
		for (UniversalDependency u : ud)
			if (u.Is(tag))
				return true;
		return false;
	}

}
