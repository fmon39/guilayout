
package de.bitfolge.guilayout.prototype;

import java.awt.event.MouseEvent;

import de.bitfolge.guilayout.drawing.UniversalMouseAdapter;
import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.elements.screenarea.ScreenArea;

public class MouseSelector extends UniversalMouseAdapter {
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if (ScreenArea.class.isInstance(e.getComponent())) {
			ScreenArea sa = (ScreenArea) e.getComponent();
			DiagramManager.getInstance().setSelectedScreenArea(sa);
		} else {
			DiagramManager.getInstance().setSelectedScreenArea(null);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if (ScreenArea.class.isInstance(e.getComponent())) {
			ScreenArea sa = (ScreenArea) e.getComponent();
			DiagramManager.getInstance().setSelectedScreenArea(sa);
		} else {
			DiagramManager.getInstance().setSelectedScreenArea(null);
		}
	}
}
