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
	int idxStart;
	int idxEnd;
	int col;
	int row;

	List<UniversalDependency> ud;

	
	
	public Word() {
		ud = new ArrayList<UniversalDependency>();
	}

}
