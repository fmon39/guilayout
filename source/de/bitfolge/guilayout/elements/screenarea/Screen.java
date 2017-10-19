package de.bitfolge.guilayout.elements.screenarea;

import java.awt.*;
import javax.swing.Icon;

public class Screen extends ContainerScreenArea {
	
	protected static int FONT_HEIGHT = 12;

	public Screen() {
		setOpaque(false);
		setBackground(Color.WHITE);
	}
	
	public Screen(Screen s) {
		super(s);
	}
	
	public void drawFrame(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		ensureOutlineStroke();
		g2.setStroke(outlineStroke);
		int margin = (int) Math.floor(lineWidth/2.0);
		int top = margin;
		/*
		if (getName()!="") {
			top = margin + FONT_HEIGHT;
		}
		*/
		Rectangle rect = new Rectangle(margin, top, this.getWidth()-lineWidth,this.getHeight()-top-1);
		g2.setPaint(getForeground());
		//g2.draw(rect);
		g2.fillRect(margin, top, this.getWidth(), (int) Math.round(10*zoom));
		//g2.drawString(getName(), margin, top-1);
	}

	public static Icon getIcon(final boolean hover) {
		return new Icon() {
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
				if (hover) {
					g2.setColor(Color.WHITE);
					g2.fillRect(x,y+1,getIconWidth(),getIconHeight()-2);
					g2.setColor(Color.BLUE);
				} else {
					g2.setColor(Color.BLACK);
				}
				g2.drawRect(x,y+1,getIconWidth(),getIconHeight()-2);
				g2.setStroke(new BasicStroke(3));
				g2.drawLine(x+1, y+1, x+getIconWidth()-1, y+1);
			}
		};
	}
	
	public boolean isAbstract() {
		return false;
	}
	
	public boolean isConvertible() {
		return false;
	}

	public Object clone() {
		Screen s = new Screen(this);
		//s.setName("Copy of "+getName());
		return s;
	}
	
}
