package nlp;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Language for describing the games. {@link #readDictionary(File)} first to
 * fill in some words. Then construct a parser from it, by passing a language to
 * the {@link SuperTolerantLexer}.
 *
 * @author Toby
 */
public class Language {
	static Pattern parseLine = Pattern.compile("^\\s*([@:!>~])\\s*(.+?)\\s*$", Pattern.MULTILINE);

	Dictionary dict = null;
	List<RelationRule> relationrules = null;
	String name;

	public Language() {
		name = new String("en");

	}

	/**
	 * Read a file with words and {@link UniversalDependency} defined within. (A
	 * word can have multiple Universal-Dependencies.)
	 * <p>
	 * {@code Language lang = new Language();}<br>
	 * {@code File file = Paths.get(".", "language", "dictionary_en.txt").toFile();}<br>
	 * {@code lang.readDictionary(file);}
	 * 
	 * @param file File with language description
	 * @throws IOException
	 */
	public void readDictionary(File file) throws IOException {
		dict = new Dictionary();
		relationrules = new ArrayList<RelationRule>();
		String contents = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		Matcher line = parseLine.matcher(contents);
		Word word = null;
		while (line.find()) {
			String type = line.group(1);
			if (type.compareTo(">") == 0) {
				dict.languagename = line.group(2);
			}
			if (type.compareTo("~") == 0) {
				RelationRule rule = new RelationRule(line.group(2));
				relationrules.add(rule);
			}

			if (type.compareTo("@") == 0) {
				word = new Word(line.group(2));
				dict.words.add(word);
			}
			if (type.compareTo("!") == 0) {
				if (word == null)
					throw new RuntimeException("In the dictionary the first line is a lemma (!) and not a word (@).");
				word.lemma = line.group(2);
			}
			if (type.compareTo(":") == 0) {
				if (word == null)
					throw new RuntimeException(
							"In the dictionary the first line is a universal-dependency (:) and not a word (@).");
				UniversalDependency ud = new UniversalDependency(line.group(2));
				word.ud.add(ud);
			}
		}
	}

	boolean isUpper(int ch) {
		if (ch >= 'A' && ch <= 'Z')
			return true;

		return false;
	}

	int toLower(int ch) {
		if (ch >= 'A' && ch <= 'Z')
			return ch + 32;
		return ch;
	}

	int toUpper(int ch) {
		if (ch >= 'a' && ch <= 'z')
			return ch - 32;
		return ch;
	}
}
