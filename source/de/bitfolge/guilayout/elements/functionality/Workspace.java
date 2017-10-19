package de.bitfolge.guilayout.elements.functionality;

import java.awt.*;
import javax.swing.Icon;

import de.bitfolge.guilayout.drawing.Turtle;

public class Workspace extends AbstractUIFunctionality {
	public static final String ACTIONCOMMAND = "toggleWorkspace";

	public void draw(Graphics g, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
		g2.setColor(c);
		Turtle t = new Turtle(g,0,0);
		
		int side = Math.min(parent.getWidth(),parent.getHeight());
		side = Math.min(150,side);
		t.setPosition(0.5*parent.getWidth(), 0.3*parent.getHeight());
		t.moveRelative(-0.3*side,+0.3*side);
		t.setAngle(45);
		t.forward(0.3125*side);
		t.turn(120);
		t.forward(0.3125*side);
		t.turn(120);
		t.storePosition("spitze");
		t.forward(0.3125*side);
		t.turn(30);
		t.forward(0.6*side);
		t.turn(90);
		t.forward(0.3125*side);
		t.turn(90);
		t.forward(0.6*side);
		t.restorePosition("spitze");

		t.setAngle(300);
		t.setDrawing(false);
		t.forward(Math.sin(Math.PI/3)*0.32*side);
		t.turn(15);
		t.setDrawing(true);
		t.forward(0.6*side);
		t.restorePosition("spitze");
		t.setAngle(330);
		t.setDrawing(false);
		t.forward(Math.sin(Math.PI/3)*0.32*side);
		t.turn(-15);
		t.setDrawing(true);
		t.forward(0.6*side);
		
		Polygon triangle = new Polygon();
		t.setDrawing(false);
		t.restorePosition("spitze");
		triangle.addPoint(t.getPosition().x, t.getPosition().y);
		t.setAngle(-14);
		t.forward(0.1*side);
		triangle.addPoint(t.getPosition().x, t.getPosition().y);
		t.turn(-120);
		t.forward(0.1*side);
		triangle.addPoint(t.getPosition().x, t.getPosition().y);
		g2.fillPolygon(triangle);

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
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setStroke(new BasicStroke(1));
				g2.setColor(getColorForState(stateInteger));
				Turtle t = new Turtle(g2,x,y);
				
				t.setPosition(0,11/16.0*getIconHeight());
				t.setAngle(45);
				t.forward(5/16.0*getIconWidth());
				t.turn(120);
				t.forward(5/16.0*getIconWidth());
				t.turn(120);
				t.storePosition("spitze");
				t.forward(5/16.0*getIconWidth());
				t.turn(30);
				t.forward(14/16.0*getIconWidth());
				t.turn(90);
				t.forward(5/16.0*getIconWidth());
				t.turn(90);
				t.forward(14/16.0*getIconWidth());
				t.restorePosition("spitze");
				t.setAngle(315);
				t.setDrawing(false);
				t.forward(Math.sin(Math.PI/3)*5/16.0*getIconWidth());
				t.setDrawing(true);
				t.forward(14/16.0*getIconWidth());
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2.setColor(Color.BLACK);
			}
		};
		return icon;
	}
}
