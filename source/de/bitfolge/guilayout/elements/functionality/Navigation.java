package de.bitfolge.guilayout.elements.functionality;

import java.awt.*;
import javax.swing.Icon;

import de.bitfolge.guilayout.drawing.Turtle;


public class Navigation extends AbstractUIFunctionality {
	public static final String ACTIONCOMMAND = "toggleNavigation";

	public void draw(Graphics g, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(c);
		int side = Math.min(parent.getWidth(), parent.getHeight());
		side = Math.min(150,side);
		Turtle t = new Turtle(g2,0,0);
		t.setPosition(parent.getWidth()/2,parent.getHeight()/2);
		t.setStroke(new BasicStroke(0.02f*side));
		t.moveRelative(-0.3*side, 0);
		t.storePosition("start");
		t.forward(0.6*side);
		t.turn(150);
		t.forward(0.2*side);
		t.forward(-0.2*side);
		t.turn(60);
		t.forward(0.2*side);
		t.restorePosition("start");
		t.setAngle(315);
		t.forward(0.45*side);
		t.turn(150);
		t.forward(0.2*side);
		t.forward(-0.2*side);
		t.turn(60);
		t.forward(0.2*side);
		t.restorePosition("start");
		t.setAngle(45);
		t.forward(0.45*side);
		t.turn(150);
		t.forward(0.2*side);
		t.forward(-0.2*side);
		t.turn(60);
		t.forward(0.2*side);
		
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
				g2.drawLine(x, y+getIconHeight()/2, x+getIconWidth(), y+getIconHeight()/2);
				g2.drawLine(x+getIconWidth(), y+getIconHeight()/2, x+getIconWidth()-3, y+getIconHeight()/2-3);
				g2.drawLine(x+getIconWidth(), y+getIconHeight()/2, x+getIconWidth()-3, y+getIconHeight()/2+3);

				g2.drawLine(x, y+getIconHeight()/2, x+getIconWidth()/2, y);
				g2.drawLine(x+getIconWidth()/2, y, x+getIconWidth()/2-4, y);
				g2.drawLine(x+getIconWidth()/2, y, x+getIconWidth()/2, y+4);
				
				g2.drawLine(x, y+getIconHeight()/2, x+getIconWidth()/2, y+getIconHeight());
				g2.drawLine(x+getIconWidth()/2, y+getIconHeight(), x+getIconWidth()/2-4, y+getIconHeight());
				g2.drawLine(x+getIconWidth()/2, y+getIconHeight(), x+getIconWidth()/2, y+getIconHeight()-4);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2.setColor(Color.BLACK);
			}
		};
		return icon;
	}
}
