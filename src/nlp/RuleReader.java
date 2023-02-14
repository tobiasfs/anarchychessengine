package nlp;

public class RuleReader {
	SuperTolerantLexer lex = new SuperTolerantLexer();
	
	public RuleReader() {
		lex.AddDET("the");
		lex.AddPRON("he");
		lex.AddPRON("she");
		lex.AddPRON("it");
		
		
	}
	
	
	
}
