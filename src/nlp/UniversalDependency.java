package nlp;

import java.util.HashMap;
import java.util.Map;

public class UniversalDependency {
	enum Tag {
		ADJ, ADV, INTJ, NOUN, PROPN, VERB, ADP, AUX, CCONJ, DET, NUM, PART, PRON, SCONJ, PUNCT, SYM, X
	};

	enum Feature {
		PronType, NumType, Poss, Reflex, Foreign, Abbr, Typo, Gender, Animacy, NounClass, Number, Case, Definite,
		Degree, VerbForm, Mood, Tense, Aspect, Voice, Evident, Polarity, Person, Polite, Clusivity
	};

	int ID;
	Tag UPOS;
	String LEMMA;
	String FORM;
	Map<Feature, String> FEAT;
	Map<String, String> property;
	
	public UniversalDependency(int id, Tag upos, String lemma, String form) {
		this.ID = id;
		this.UPOS = upos;
		this.LEMMA = lemma;
		this.FORM = form;
		this.FEAT = new HashMap<Feature, String>();
		this.property = new HashMap<String, String>();
	}
	
	
	
}
