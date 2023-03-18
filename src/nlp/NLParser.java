package nlp;

import java.util.ArrayList;
import java.util.List;

import nlp.RelationRule.Direction;
import nlp.UniversalDependency.Tag;

/**
 * The Natural Language Parser has a small set of rules to parse a very formal
 * set of sentences into trees. The trees are stored with the {@link Token} that
 * the lexer generated. Each token has a relations field, that inform about the
 * connected tokens.
 * <p>
 * The parser produces a list of Tokens for the verbs of each sentence.
 * 
 * @author Toby
 */
public class NLParser {

	SuperTolerantLexer lexer;
	private Token token;
	public List<Token> sentences;
	private List<Token> parts;

	public NLParser(SuperTolerantLexer lexer) {
		this.lexer = lexer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (Token sentence : sentences) {
			if (first)
				first = false;
			else
				builder.append('\n');
			builder.append(sentence.toString());
		}
		return builder.toString();
	}

	private void next() {
		token = lexer.nextToken();
	}

	public void parse(String contents) {
		lexer.Reset();
		lexer.initLexing(contents);
		sentences = new ArrayList<Token>();
		System.out.println("----------------------------------------------------- Parsing ---");
		next();
		while (token != null) {
			Token sentence = parseSentence();
			sentences.add(sentence);
		}
	}

	private Token parseSentence() {
		if (token == null)
			throw new RuntimeException("More tokens in input were expected.");

		parts = new ArrayList<Token>();
		while (token != null && !token.Is(Tag.PUNCT, "PunctType", "Peri")) {
			parts.add(token);
			next();
		}
		if (token.Is(Tag.PUNCT, "PunctType", "Peri")) {
			parts.add(token);
			next();
		}

		if (parts.isEmpty())
			throw new RuntimeException("The list of collected words is empty. This should not happen.");

		List<RelationRule> rules = lexer.lang.relationrules;

		while (parts.size() > 1) {
			boolean foundrule = false;
			for (RelationRule r : rules) {
				if (r.direction == Direction.reduce_to_right) {
					for (int i = 0; i < (parts.size() - 1); ++i) {
						if (parts.get(i).Is(r.tagLeft) && parts.get(i + 1).Is(r.tagRight)) {
							foundrule = true;
							if (parts.get(i + 1).relations.containsKey(r.relation)) {
								throw new RuntimeException(String.format(
										"At row %d, col %d:\n%s\nThe relation \"%s\" is already filled. Current rule: %s",
										parts.get(i).row, parts.get(i).col, parts.toString(), r.relation.toString(),
										r.toString()));
							}
							parts.get(i + 1).relations.put(r.relation, parts.remove(i));
							break;
						}
					}
					if (foundrule)
						break;
				} else {
					for (int i = (parts.size() - 2); i >= 0; --i) {
						if (parts.get(i).Is(r.tagLeft) && parts.get(i + 1).Is(r.tagRight)) {
							foundrule = true;
							if (parts.get(i).relations.containsKey(r.relation)) {
								throw new RuntimeException(String.format(
										"At row %d, col %d:\n%s\nThe relation \"%s\" is already filled. Current rule: %s",
										parts.get(i).row, parts.get(i).col, parts.toString(), r.relation.toString(),
										r.toString()));
							}
							parts.get(i).relations.put(r.relation, parts.remove(i + 1));
							break;
						}
					}
					if (foundrule)
						break;
				}
			}
			if (!foundrule)
				break;
		}
		if (parts.size() > 1)
			System.out.println(String.format("Around row %d, col %d: Could not reduce the sentence completely:\n%s",
					parts.get(0).row, parts.get(0).col, parts.toString()));
		return parts.get(0);
	}
}
