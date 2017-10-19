package de.bitfolge.guilayout.elements.functionality;

import java.awt.Color;
import java.awt.Graphics;

import de.bitfolge.guilayout.elements.screenarea.FunctionalScreenArea;

public interface UIFunctionality {
	
	public void draw(Graphics g, Color c);
	
	public void setParent(FunctionalScreenArea fsa);
	
	public void setZoom(double zoom);

}
