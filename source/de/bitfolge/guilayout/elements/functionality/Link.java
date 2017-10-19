/*
 * Created on 18.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.elements.functionality;

import java.awt.*;

import javax.swing.Icon;

import de.bitfolge.guilayout.drawing.Turtle;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Link extends AbstractUIFunctionality {

	public static final String ACTIONCOMMAND = "toggleLink";

	public void draw(Graphics g, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(c);
		int side = Math.min(parent.getWidth(), parent.getHeight());
		side = Math.min(150,side);
		Turtle t = new Turtle(g2,0,0);
		t.setPosition(parent.getWidth()/2,parent.getHeight()/2);
		float sWidth = 0.02f*side;
		sWidth= Math.max(0.5f, sWidth);
		t.setStroke(new BasicStroke(sWidth));
		t.moveRelative(-0.3*side, 0);
		t.forward(0.6*side);
		t.turn(150);
		t.forward(0.2*side);
		t.forward(-0.2*side);
		t.turn(60);
		t.forward(0.2*side);
		/*
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(c);
		int midY = parent.getHeight()/2;
		g2.drawLine(0, midY, parent.getWidth(), midY);
		g2.drawLine(parent.getWidth(), midY, parent.getWidth()-midY*2, 0);
		g2.drawLine(parent.getWidth(), midY, parent.getWidth()-midY*2, parent.getHeight());
		*/
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
				g2.drawLine(x, y+getIconHeight()/2, x+getIconWidth(), y+getIconHeight()/2);
				g2.drawLine(x+getIconWidth(), y+getIconHeight()/2, x+getIconWidth()-5, y+getIconHeight()/2-5);
				g2.drawLine(x+getIconWidth(), y+getIconHeight()/2, x+getIconWidth()-5, y+getIconHeight()/2+5);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2.setColor(Color.BLACK);
			}
		};
		return icon;
	}
}
