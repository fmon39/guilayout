package de.bitfolge.guilayout.drawing;

import java.awt.*;

import javax.swing.JPanel;

import de.bitfolge.guilayout.prototype.ComponentNester;


public abstract class AbstractElementView extends JPanel {
	
	protected int lineWidth = 1;
	protected Stroke outlineStroke = null;
	private ComponentMouseHover cmh = null;

	public AbstractElementView() {
		super();
		ComponentDocker cm = new ComponentNester();
		this.addMouseListener(cm);
		this.addMouseMotionListener(cm);
	}
	
	public AbstractElementView(AbstractElementView uidc) {
		this();
		if (uidc!=null) {
			uidc.add(this);
		}
	}
	
	public void paint(java.awt.Graphics g) {
		super.paint(g);
	}
	
	public void drawFrame(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (this.outlineStroke==null) {
			outlineStroke = new BasicStroke(lineWidth);
		}
		g2.setStroke(outlineStroke);
		int margin = (int) Math.floor(lineWidth/2.0);
		Rectangle rect = new Rectangle(margin, margin, this.getWidth()-lineWidth,this.getHeight()-lineWidth);
		g2.setPaint(getForeground());
		g2.draw(rect);
	}
	
	public void fillFrame(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int margin = (int) Math.floor(lineWidth/2.0);
		Rectangle rect = new Rectangle(margin, margin, this.getWidth()-lineWidth,this.getHeight()-lineWidth);
		g2.setPaint(getBackground());
		g2.fill(rect);
	}

	public void setOutlineWidth(int width) {
		this.lineWidth = width;
		//this.outlineStroke = new BasicStroke(width);
	}

	public void setOutlineStroke(Stroke stroke) {
		//this.lineWidth = width;
		this.outlineStroke = stroke;
	}
	
	public void useMouseHover(boolean useIt) {
		if (cmh==null) {
			cmh = new ComponentMouseHover();
		}
		if (useIt) {
			this.removeMouseListener(cmh);
			this.addMouseListener(cmh);
		} else {
			this.removeMouseListener(cmh);
		}
	}

	public void doMouseOut() {
	}

	public void doMouseOver() {
	}

}
