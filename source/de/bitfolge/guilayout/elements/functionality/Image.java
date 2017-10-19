/*
 * Created on 17.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.elements.functionality;

import java.awt.*;
import javax.swing.Icon;

public class Image extends AbstractUIFunctionality {
	
	public static final String ACTIONCOMMAND = "toggleImage";

	public Image() {
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
				g2.setStroke(new BasicStroke(1));
				g2.setColor(getColorForState(stateInteger));
				g2.drawRect(x,y,getIconWidth(),getIconHeight());
				g2.drawLine(x, y, x+getIconWidth(), y+getIconHeight());
				g2.drawLine(x, y+getIconHeight(), x+getIconWidth(), y);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2.setColor(Color.BLACK);
			}
		};
		return icon;
	}

	public void draw(Graphics g, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setStroke(new BasicStroke(1));
		g2.setColor(c);
		g2.drawLine(0, 0, parent.getWidth(), parent.getHeight());
		g2.drawLine(0, parent.getHeight(), parent.getWidth(), 0);
	}

}
