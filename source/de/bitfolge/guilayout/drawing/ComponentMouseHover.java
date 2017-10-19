/*
 * Created on 18.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.drawing;

import java.awt.event.*;


/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComponentMouseHover extends MouseAdapter {
	
	public void mouseEntered(MouseEvent e) {
		AbstractElementView c = (AbstractElementView) e.getSource();
		c.doMouseOver();
		c.repaint();
	}

	public void mouseExited(MouseEvent e) {
		AbstractElementView c = (AbstractElementView) e.getSource();
		c.doMouseOut();
		c.repaint();
	}

}
