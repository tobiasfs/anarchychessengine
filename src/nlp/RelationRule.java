package nlp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nlp.Token.Relation;
import nlp.UniversalDependency.Tag;

public class RelationRule {
	static Pattern parseLine = Pattern.compile("([A-Za-z:]+|<-\\[|\\]->|\\]-|-\\[)");

	enum Direction {
		undefined, reduce_to_left, reduce_to_right
	}

	public Tag tagLeft;
	public Tag tagRight;
	public Relation relation;
	public Direction direction;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(tagLeft);
		if (direction == Direction.reduce_to_right)
			builder.append(" -[");
		if (direction == Direction.reduce_to_left)
			builder.append(" <-[");
		builder.append(relation);
		if (direction == Direction.reduce_to_right)
			builder.append("]-> ");
		if (direction == Direction.reduce_to_left)
			builder.append("]- ");
		builder.append(tagRight);
		return builder.toString();
	}

	public RelationRule(String text) {
		Matcher m = parseLine.matcher(text);
		if (!m.find())
			throw new RuntimeException("The rule \"" + text + "\" does not fit the pattern for relation-rules.");
		try {
			tagLeft = Tag.valueOf(m.group());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("In the rule \"" + text + "\" the tag \"" + m.group()
					+ "\" is not a valid Universal Dependency tag.");
		}

		if (!m.find())
			throw new RuntimeException("In the rule \"" + text + "\" expected an <-[ or an -[ here.");
		direction = Direction.undefined;
		if (m.group().compareTo("-[") == 0)
			direction = Direction.reduce_to_right;
		if (m.group().compareTo("<-[") == 0)
			direction = Direction.reduce_to_left;
		if (!m.find())
			throw new RuntimeException("The rule \"" + text + "\" does not fit the pattern for relation-rules.");
		try {
			relation = Relation.valueOf(m.group());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("In the rule \"" + text + "\" the relation \"" + m.group()
					+ "\" is not a valid Universal Dependency relation.");
		}

		if (!m.find())
			throw new RuntimeException("In the rule \"" + text + "\" expected an <-[ or an -[ here.");

		if (direction == Direction.reduce_to_right && m.group().compareTo("]->") != 0)
			throw new RuntimeException("The rule \"" + text + "\" misses an end of the arrow ( ]-> ).");
		if (direction == Direction.reduce_to_left && m.group().compareTo("]-") != 0)
			throw new RuntimeException("The rule \"" + text + "\" misses the beginnin of the arrow ( ]- ).");

		if (!m.find())
			throw new RuntimeException("The rule \"" + text + "\" does not fit the pattern for relation-rules.");
		try {
			tagRight = Tag.valueOf(m.group());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("In the rule \"" + text + "\" the tag \"" + m.group()
					+ "\" is not a valid Universal Dependency tag.");
		}
	}

}
