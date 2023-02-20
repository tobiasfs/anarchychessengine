package nlp;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Language {
	static Pattern parseLine = Pattern.compile("^\\s*([@:!])\\s*(.+?)\\s*$", Pattern.MULTILINE);

	static public Dictionary read(File file) throws IOException {
		Dictionary dict = new Dictionary();
		String contents = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		Matcher line = parseLine.matcher(contents);
		Word word = null;
		while (line.find()) {
			String type = line.group(1);
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
				UniversalDependency ud = new UniversalDependency(line.group(2));
				word.ud.add(ud);
			}
		}
		return dict;
	}
}
