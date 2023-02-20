package data;

public class DataString extends Data {
	private String value;

	@Override
	void set(String txt) {
		value = new String(txt);
	}

	@Override
	double getDouble() {
		return Double.parseDouble(value);
	}

	@Override
	int getInteger() {
		return Integer.parseInt(value);
	}

	@Override
	String getString() {
		return value;
	}

	@Override
	boolean getBoolean() {
		return value.compareToIgnoreCase("true") == 0 || value.compareToIgnoreCase("on") == 0
				|| value.compareToIgnoreCase("yes") == 0 || value.compareToIgnoreCase("1") == 0;
	}
}
