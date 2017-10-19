package de.bitfolge.guilayout.elements.functionality;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.*;

import javax.swing.Icon;

import de.bitfolge.guilayout.elements.screenarea.FunctionalScreenArea;

public abstract class AbstractUIFunctionality implements UIFunctionality {
	protected FunctionalScreenArea parent;
	public static Color ICON_COLOR_DEPRESSED = Color.BLACK;
	public static Color ICON_COLOR_PRESSED = new Color(0,0,255);
	public static Color ICON_COLOR_DISABLED = new Color(128,128,128);
	public static final Integer STATE_PRESSED = new Integer(1);
	public static final Integer STATE_DEPRESSED = new Integer(2);
	public static final Integer STATE_DISABLED = new Integer(4);
	protected double zoom = 1;

	public abstract void draw(Graphics g, Color c);

	public void setParent(FunctionalScreenArea fsa) {
		parent = fsa;
	}
	
	public static Color getColorForState(Integer stateInteger) {
		int state = stateInteger.intValue();
		if (state==AbstractUIFunctionality.STATE_DEPRESSED.intValue()) {
			return AbstractUIFunctionality.ICON_COLOR_DEPRESSED;
		}
		if (state==AbstractUIFunctionality.STATE_PRESSED.intValue()) {
			return AbstractUIFunctionality.ICON_COLOR_PRESSED;
		}
		if (state==AbstractUIFunctionality.STATE_DISABLED.intValue()) {
			return AbstractUIFunctionality.ICON_COLOR_DISABLED;
		}
		return Color.BLACK;
	}
	
	public static Icon getIconForClassAndState(Class c, Integer buttonState) {
		Class[] paramClasses = { Integer.class };
		Integer[] params = { buttonState };
		try {
			Method m = c.getDeclaredMethod("getIcon", paramClasses);
			return (Icon) m.invoke(null, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getActionCommandForClass(Class c) {
		try {
			Field f = c.getDeclaredField("ACTIONCOMMAND");
			return (String) f.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
}
