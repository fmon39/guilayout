package de.bitfolge.guilayout.prototype;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import de.bitfolge.guilayout.drawing.UniversalMouseAdapter;
import de.bitfolge.guilayout.elements.DiagramManager;

public class RectCreator extends UniversalMouseAdapter {
	
	private JComponent rect;
	private Point dragStart;
	
	public RectCreator() {
		dragStart = null;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON3) {
			GUILayout._program.drawingPanel.setAddMode(null);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		Component c = (Component) e.getSource();
		Graphics2D g2 = (Graphics2D) c.getGraphics();
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(1));
		
		Point bottomRight = e.getPoint();
		int x1 = Math.min(dragStart.x, bottomRight.x);
		int y1 = Math.min(dragStart.y, bottomRight.y);
		int x2 = Math.max(dragStart.x, bottomRight.x);
		int y2 = Math.max(dragStart.y, bottomRight.y);
		Rectangle r = new Rectangle(x1, y1, x2-x1, y2-y1);
		rect.setBounds(r);
		c.repaint();
	}

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		rect = new JComponent() {
			public void paint(Graphics g) {
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);

				double zoom = DiagramManager.getInstance().getZoom();
				String displayString = Math.round(getWidth()/zoom)+"x"+Math.round(getHeight()/zoom);
				Rectangle2D b = g.getFontMetrics().getStringBounds(displayString,g);
				int sX = (int) Math.round((getWidth()-b.getWidth())/2);
				int sY = (int) Math.round((getHeight()+b.getHeight())/2);
				g.drawString(displayString,sX, sY);
			}
		};
		dragStart = e.getPoint();
		rect.setLocation(dragStart);
		Container c = (Container) e.getSource();
		c.setLayout(null);
		c.add(rect);
	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		Container c = (Container) e.getSource();
		GUILayout._program.drawingPanel.addSomething(rect.getBounds());
		c.remove(rect);
		rect = null;
	}
}
