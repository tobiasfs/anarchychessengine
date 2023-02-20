package data;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public abstract class Data {
	void set(String txt) {
		throw new RuntimeException("set(String) is not supported for this object.");
	}
	
	double getDouble() {
		throw new RuntimeException("getDouble() is not supported for this object.");
	}

	int getInteger() {
		throw new RuntimeException("getInteger() is not supported for this object.");
	}

	String getString() {
		throw new RuntimeException("getString() is not supported for this object.");
	}

	boolean getBoolean() {
		throw new RuntimeException("getBoolean() is not supported for this object.");
	}

	boolean hasKey(String key) {
		return false;
	}

	Data get(String key) {
		throw new RuntimeException("This is not a map, where a key can be used to look up a value.");
	}

	Data getIndex(int index) {
		throw new RuntimeException("This is not a list, where an index can be used to look up a value.");
	}

	int getCount() {
		return 1;
	}

	boolean isNull() {
		return false;
	}

	boolean isEmpty() {
		return false;
	}

}

class DataList extends Data {
	private Vector<Data> value = new Vector<Data>();

	void Add(Data data) {
		value.add(data);
	}

	@Override
	Data getIndex(int index) {
		return value.get(index);
	}

	@Override
	int getCount() {
		return value.size();
	}

	@Override
	boolean isEmpty() {
		return value.isEmpty();
	}
}

class DataMap extends Data {
	private Map<String, Data> value = new HashMap<String, Data>();

	void Add(String key, Data data) {
		value.put(key, data);
	}

	@Override
	boolean hasKey(String key) {
		return value.containsKey(key);
	}

	@Override
	Data get(String key) {
		return value.get(key);
	}

	@Override
	boolean isEmpty() {
		return value.isEmpty();
	}
}

class DataDouble extends Data {
	private double value;

	@Override
	double getDouble() {
		return value;
	}

	@Override
	int getInteger() {
		return (int) value;
	}

	@Override
	String getString() {
		return Double.toString(value);
	}

	@Override
	boolean getBoolean() {
		return Math.abs(value) > 1e-9;
	}
}

class DataBoolean extends Data {
	private boolean value;

	@Override
	double getDouble() {
		if (value)
			return 1.0;
		return 0.0;
	}

	@Override
	int getInteger() {
		if (value)
			return 1;
		return 0;
	}

	@Override
	String getString() {
		if (value)
			return new String("true");
		return new String("false");
	}

	@Override
	boolean getBoolean() {
		return value;
	}
}
