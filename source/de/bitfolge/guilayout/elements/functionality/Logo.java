/*
 * Created on 17.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.elements.functionality;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.Icon;


/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Logo extends AbstractUIFunctionality {

	public static final String ACTIONCOMMAND = "toggleLogo";

	/* (non-Javadoc)
	 * @see java.awt.Component#update(java.awt.Graphics)
	 */
	public void draw(Graphics g, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(c);
		g2.setStroke(new BasicStroke(1));
		g2.draw(new Ellipse2D.Double(0, 0, parent.getWidth()-1, parent.getHeight()-1));
	}

	public static Icon getIcon(final Integer stateInteger) {
		Icon icon = new Icon() {
			public int getIconHeight() {
				return 16;
			}
			public int getIconWidth() {
				return 16;
			}
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setStroke(new BasicStroke(2));
				g2.setColor(getColorForState(stateInteger));
				int ovalHeight=8;
				g2.drawOval(x,y+getIconHeight()/2-ovalHeight/2,getIconWidth(),ovalHeight);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2.setColor(Color.BLACK);
		}
		};
		return icon;
	}

}
