package data;

public class DataInteger extends Data {
	private int value;

	@Override
	double getDouble() {
		return value;
	}

	@Override
	int getInteger() {
		return value;
	}

	@Override
	String getString() {
		return Integer.toString(value);
	}

	@Override
	boolean getBoolean() {
		return value != 0;
	}

}