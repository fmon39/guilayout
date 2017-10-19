/*
 * Created on 16.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.elements.functionality;

import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;

import javax.swing.Icon;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Heading extends AbstractUIFunctionality {
	
	private Vector points = new Vector();
	public static final String ACTIONCOMMAND = "toggleHeading";
	
	private double getRandom(double from, double to) {
		return from + Math.random() * (to - from);
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
				g2.setStroke(new BasicStroke(1.5f));
				g2.setColor(getColorForState(stateInteger));
				int maxI = 6;
				for (int i=0; i<maxI; i++) {
					double left = x+i*getIconWidth()/(double)maxI;
					Shape curve = new QuadCurve2D.Double(
						left, y+getIconHeight()/2,
						left+getIconWidth()/(maxI*2), i%2==0 ? y : y+getIconHeight(),
						left+getIconWidth()/(double)maxI, y+getIconHeight()/2
					);
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.draw(curve);
				}
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2.setColor(Color.BLACK);
			}
		};
		return icon;
	}

	/* (non-Javadoc)
	 * @see de.bitfolge.guilayout.elements.functionality.AbstractUIFunctionality#draw(java.awt.Graphics, java.awt.Color)
	 */
	public void draw(Graphics g, Color c) {
		int baseSegLength = 2;
		double xFactor;
		boolean inverse = false;
		int i=0;
		double startEndY = 0.5;
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(c);
		for (double x=0; x < parent.getWidth(); i+=3) {
			if (points.size()<i+2) {
				xFactor = x / 40;
				double segALength = getRandom(baseSegLength + xFactor, baseSegLength + xFactor);
				double segBLength = getRandom(baseSegLength + xFactor, 2* baseSegLength + xFactor);
				double ctrlY = getRandom(-0.1 * parent.getHeight(), -0.2 * parent.getHeight());
				if (inverse) {
					ctrlY = parent.getHeight() - ctrlY;
				}
				ctrlY /= parent.getHeight();
				points.add(new Point2D.Double(x, startEndY));
				points.add(new Point2D.Double(x+segALength, ctrlY));
				startEndY = getRandom(0.9*startEndY, 1.1*startEndY);
				points.add(new Point2D.Double(x+segALength + segBLength, startEndY));
			}
			Point2D start = (Point2D) points.elementAt(i);
			Point2D ctrl = (Point2D) points.elementAt(i+1);
			Point2D end = (Point2D) points.elementAt(i+2);

			Shape curve = new QuadCurve2D.Double(
				start.getX(), start.getY() * parent.getHeight(),
				ctrl.getX(), ctrl.getY() * parent.getHeight(),
				end.getX(), end.getY() * parent.getHeight()
			);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.draw(curve);
			x = end.getX();
			inverse = !inverse;
		}
	}

}
