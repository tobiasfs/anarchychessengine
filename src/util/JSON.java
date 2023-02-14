package util;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.DataHierarchy;

public class JSON extends DataHierarchy {

	public JSON() {
	}

	public void readFile(Path filename) {

		String input = new String();

		Pattern pattern = Pattern.compile(
				"[a-zA-Z_][a-zA-Z0-9_]*+|\"([^\"\\\\]++|\".)*+\"|[+\\-]?\\s*[0-9.]([eEfF][+\\-]?[0-9]+)|[\\[\\]\\(\\),:{}]");
		Matcher lexer = pattern.matcher(input);

	}

	public void Parser(Matcher lexer) {

	}

}
