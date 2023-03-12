package nlp;

public class Token {
	private String txt;
	private Object id;

	public Token(String txt, Object id) {
		this.txt = txt;
		this.id = id;
	}

	public String getTxt() {
		return txt;
	}

	public Object getId() {
		return id;

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(txt);
		builder.append("\t\t");
		builder.append(id);
		return builder.toString();
	}
}
