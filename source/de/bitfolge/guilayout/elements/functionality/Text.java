/*
 * Created on 16.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.elements.functionality;

import java.awt.*;

import javax.swing.Icon;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Text extends AbstractUIFunctionality {
	
	private int lineThickness = 5;
	private int lineHeight = 12;
	public static final String ACTIONCOMMAND = "toggleText";
	
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
				//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setStroke(new BasicStroke(1.5f));
				g2.setColor(getColorForState(stateInteger));
				for (int i=2; i<getIconHeight(); i+=3) {
					g2.drawLine(x, y+i, x+getIconWidth(), y+i);
				}
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2.setColor(Color.BLACK);
			}
		};
		return icon;
	}

	public void draw(Graphics g, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		double lT = lineThickness*zoom;
		int lH = (int) Math.round(lineHeight*zoom); 
		for (int y=(int) Math.ceil(lT/2.0); y<parent.getHeight()-lT; y+=lH) {
			g2.setStroke(new BasicStroke((float) lT));
			//g2.setColor(new Color(128,128,128));
			Color gray = new Color((c.getRed()+255)/2, (c.getGreen()+255)/2, (c.getBlue()+255)/2);
			g2.setColor(gray);
			g2.drawLine(0, y, parent.getWidth(), y);
		}
	}
	
}
