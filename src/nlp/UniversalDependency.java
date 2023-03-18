package nlp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Universal dependency tag. Follows the notation of the Universal Dependency
 * concept except for the ID, XPOS, FORM, HEAD which are found in the Word
 * class.
 * <p>
 * Features for words are not explicitly stated. These are stored in free form
 * in a map. This map contains also the values for the later construction of the
 * objects.
 * <p>
 * Possible features would be: PronType, NumType, Poss, Reflex, Foreign, Abbr,
 * Typo, Gender, Animacy, NounClass, Number, Case, Definite, Degree, VerbForm,
 * Mood, Tense, Aspect, Voice, Evident, Polarity, Person, Polite, Clusivity,
 * Lemma
 * 
 * @see UniversalDependency.Tag
 * @see <a href=
 *      "https://universaldependencies.org/format.html">https://universaldependencies.org/format.html</a>
 * @author Toby
 */
public class UniversalDependency {
	/**
	 * POS Tags for a single word.
	 * 
	 * @see <a href=
	 *      "https://universaldependencies.org/u/pos/index.html">https://universaldependencies.org/u/pos/index.html</a>
	 */
	public enum Tag {
		ADJ, ADV, INTJ, NOUN, PROPN, VERB, ADP, AUX, CCONJ, DET, NUM, PART, PRON, SCONJ, PUNCT, SYM, X
	};

	Tag UPOS;
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
//		this.FEAT = new HashMap<Feature, String>();
		this.property = new HashMap<String, String>();

		if (propsString != null && !propsString.isEmpty()) {
			Matcher prop = parseProperties.matcher(propsString);
			while (prop.find()) {
//			Feature feat = Feature.valueOf(prop.group(1));
//			if (feat == null)
				property.put(prop.group(1).strip(), prop.group(2).strip());
//			else
//				FEAT.put(feat, prop.group(2));
			}
		}
	}

	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append(UPOS.toString());
		if (!property.isEmpty()) {
			b.append(" (");
			boolean first = true;
			for (Entry<String, String> f : property.entrySet()) {
				if (first)
					first = false;
				else
					b.append(", ");
				b.append(f.getKey().toString());
				b.append("=");
				b.append(f.getValue().toString());
			}
			b.append(")");
		}
		return b.toString();
	}

//	public boolean Is(String tag) {
//		Tag t = Tag.valueOf(tag);
//		if (t == null)
//			throw new RuntimeException(String.format("The tag '%s' is not a valid Universal Dependency tag.", tag));
//		return (UPOS == t);
//	}

	public String get(String key) {
		if (property.containsKey(key))
			return property.get(key);
		return "";
	}

	public boolean Is(Tag tag) {
		return (UPOS == tag);
	}

	public boolean Is(Tag tag, String key, String value) {
		if (UPOS != tag)
			return false;
		String ref = get(key);
		if (ref.compareToIgnoreCase(value) == 0)
			return true;
		return false;
	}
}
