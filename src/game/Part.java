package game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import data.Data;

/**
 * Abstract class for the entity component system. It contains all the
 * properties, that the elements have and provides these informations to the
 * generating components.
 */
public abstract class Part {
	public Map<String, Data> properties;

	public Part() {
		properties = new HashMap<String, Data>();
	}

	public abstract void Paint(Graphics g, AffineTransform tr);

}
