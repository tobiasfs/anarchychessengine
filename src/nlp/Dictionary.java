package nlp;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	public List<Word> words;

	public Dictionary() {
		words = new ArrayList<Word>();
	}

	@Override
	public String toString() {
		if (words.size() == 1)
			return "Dictionary with 1 word";
		else
			return "Dictionary with " + Integer.toString(words.size()) + " words";
	}
}
