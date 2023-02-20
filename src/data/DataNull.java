package data;

public class DataNull extends Data {

	@Override
	boolean isNull() {
		return true;
	}

	@Override
	boolean isEmpty() {
		return true;
	}
}