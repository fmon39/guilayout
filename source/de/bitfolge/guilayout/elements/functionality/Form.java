package de.bitfolge.guilayout.elements.functionality;

import java.awt.*;
import javax.swing.Icon;

import de.bitfolge.guilayout.drawing.Turtle;


public class Form extends AbstractUIFunctionality {
	public static final String ACTIONCOMMAND = "toggleForm";

	public void draw(Graphics g, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
		g2.setColor(c);
		int side = Math.min(parent.getWidth(), parent.getHeight());
		side = Math.min(150,side);
		Turtle t = new Turtle(g,0,0);
		
		t.setPosition(parent.getWidth()/2, parent.getHeight()/2);
		t.moveRelative(-0.4*side,-0.35*side);
		t.setAngle(0);
		t.forward(0.8*side);
		t.turn(90);
		t.forward(0.15*side);
		t.turn(90);
		t.forward(0.8*side);
		t.turn(90);
		t.forward(0.15*side);
		t.moveRelative(0,0.25*side);
		t.setAngle(0);
		t.forward(0.8*side);
		t.turn(90);
		t.forward(0.15*side);
		t.turn(90);
		t.forward(0.8*side);
		t.turn(90);
		t.forward(0.15*side);
		
		Polygon button = new Polygon();
		t.moveRelative(0,0.25*side);
		t.setAngle(0);
		button.addPoint(t.getPosition().x,t.getPosition().y);
		t.forward(0.35*side);
		t.turn(90);
		button.addPoint(t.getPosition().x,t.getPosition().y);
		t.forward(0.15*side);
		t.turn(90);
		button.addPoint(t.getPosition().x,t.getPosition().y);
		t.forward(0.35*side);
		t.turn(90);
		button.addPoint(t.getPosition().x,t.getPosition().y);
		t.forward(0.15*side);
		t.turn(90);
		t.storePosition("p1");
		g2.fillPolygon(button);
		
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(0.02f*side));
		t.moveRelative(0.075*side, 0.075*side);
		t.forward(0.2*side);
		t.turn(150);
		t.forward(0.05*side);
		t.forward(-0.05*side);
		t.turn(60);
		t.forward(0.05*side);

		g2.setColor(c);
		t.restorePosition("p1");
		t.setAngle(0);
		t.setDrawing(false);
		t.forward(0.45*side);
		t.setDrawing(true);
		button = new Polygon();
		t.setAngle(0);
		button.addPoint(t.getPosition().x,t.getPosition().y);
		t.forward(0.35*side);
		t.turn(90);
		button.addPoint(t.getPosition().x,t.getPosition().y);
		t.forward(0.15*side);
		t.turn(90);
		button.addPoint(t.getPosition().x,t.getPosition().y);
		t.forward(0.35*side);
		t.turn(90);
		button.addPoint(t.getPosition().x,t.getPosition().y);
		t.forward(0.15*side);
		t.turn(90);
		g2.fillPolygon(button);
		
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(0.02f*side));
		t.moveRelative(0.075*side, 0.075*side);
		t.forward(0.2*side);
		t.turn(150);
		t.forward(0.05*side);
		t.forward(-0.05*side);
		t.turn(60);
		t.forward(0.05*side);
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		g2.setColor(Color.BLACK);
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
				//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setStroke(new BasicStroke(1));
				g2.setColor(getColorForState(stateInteger));
				g2.drawRect(x,y,getIconWidth(),4);
				g2.drawRect(x,y+6,getIconWidth(),4);
				g2.fillRect(x,y+12,getIconWidth()/2-1,4);
				g2.fillRect(x+getIconWidth()/2+2,y+12,getIconWidth()/2-1,4);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2.setColor(Color.BLACK);
			}
		};
		return icon;
	}
}
