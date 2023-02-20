package nlp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Universal dependency tag. Follows the notation in <a
 * href=https://universaldependencies.org/format.html> UD web-site</a> except
 * for the ID, XPOS, FORM, HEAD which are found in the Word class.
 */
public class UniversalDependency {
	enum Tag {
		ADJ, ADV, INTJ, NOUN, PROPN, VERB, ADP, AUX, CCONJ, DET, NUM, PART, PRON, SCONJ, PUNCT, SYM, X
	};

	enum Feature {
		PronType, NumType, Poss, Reflex, Foreign, Abbr, Typo, Gender, Animacy, NounClass, Number, Case, Definite,
		Degree, VerbForm, Mood, Tense, Aspect, Voice, Evident, Polarity, Person, Polite, Clusivity, Lemma
	};

	Tag UPOS;
	Map<Feature, String> FEAT;
	Map<String, String> property;

	static Pattern parseLine = Pattern.compile("^[:]?\\s*([A-Z]+)\\s*(\\([^\\)]*\\))?");
	static Pattern parseProperties = Pattern.compile("([a-zA-Z]*)\\s*=\s*([^,\\)]*)");

	public UniversalDependency(String txt) {
		Matcher line = parseLine.matcher(txt);
		if (!line.find()) {
			throw new RuntimeException("In the line \"" + txt + "\" no universal dependency was found.");
		}
		String tagString = line.group(1);
		String propsString = line.group(2);
		Tag tag = Tag.valueOf(tagString);

		this.UPOS = tag;
		this.FEAT = new HashMap<Feature, String>();
		this.property = new HashMap<String, String>();
	}

}
