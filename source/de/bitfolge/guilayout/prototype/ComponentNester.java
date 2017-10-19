package de.bitfolge.guilayout.prototype;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import de.bitfolge.guilayout.drawing.ComponentDocker;
import de.bitfolge.guilayout.elements.screenarea.ScreenArea;

public class ComponentNester extends ComponentDocker {
	
	protected void buildComponentList() {
		Component[] comps = parent.getComponents();
		for (int i=0; i<comps.length; i++) {
			Component c = (Component) comps[i];
			if (c.equals(source)) {
				continue;
			}
			Rectangle r = c.getBounds();
			r = SwingUtilities.convertRectangle(parent, r, GUILayout._program.drawingPanel);
			allComponents.add(r);
		}
		// attention, dirty hack: add container boundardies as docking lines
		// by adding a fake component to the list
		Rectangle temp = new Rectangle(0,0, parent.getWidth(),	parent.getHeight());
		Rectangle fake = SwingUtilities.convertRectangle(parent, temp, GUILayout._program.drawingPanel);
		ComponentDocker.allComponents.add(fake);
	}
	

	public void mouseDragged(MouseEvent e) {
		this.parent = ((Component) e.getSource()).getParent();
		this.source = (Component) e.getSource();
		ScreenArea source = (ScreenArea) e.getSource();
		GUILayout._program.drawingPanel.moveToGlassPane(source, e.getPoint());
		Point oldPos = source.getLocation();
		Rectangle r = source.getBounds();
		super.mouseDragged(e);
		((ScreenArea)source).showSize = this._resize!=0;
		Point newPos = source.getLocation();
		Point mouseTranslated = new Point();
		mouseTranslated.x = e.getX() - newPos.x + oldPos.x;
		mouseTranslated.y = e.getY() - newPos.y + oldPos.y;
		GUILayout._program.drawingPanel.moveToDiagram(source, mouseTranslated);
		GUILayout._program.drawingPanel.repaint();
	}

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		if (source!=null)
			((ScreenArea)source).showSize = false;
		super.mouseReleased(e);
		if (source!=null)
			source.repaint();
		GUILayout._program.drawingPanel.recalcPreferredSize();
	}
}
