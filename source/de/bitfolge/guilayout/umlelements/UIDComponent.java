/*
 * Created on 16.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.umlelements;

import java.awt.*;

import de.bitfolge.guilayout.drawing.*;
import de.bitfolge.guilayout.drawing.ComponentDocker;
import de.bitfolge.guilayout.drawing.ComponentMouseHover;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class UIDComponent extends java.awt.Component {
	
	private int lineWidth = 1;
	protected Stroke outlineStroke = null;
	private ComponentMouseHover cmh = null;
	
	public UIDComponent() {
		super();
		ComponentDocker cm = new ComponentDocker();
		this.addMouseListener(cm);
		this.addMouseMotionListener(cm);
	}
	
	public UIDComponent(AbstractElementView uidc) {
		this();
		if (uidc!=null) {
			uidc.add(this);
		}
	}
	
	public UIDComponent(AbstractElementView uidc, int x, int y, int width, int height) {
		this(uidc);
		this.setBounds(x, y, width, height);
	}

	
	public void paint(Graphics g) {
		this.fillFrame(g);
		super.paint(g);
		this.update(g);
		this.drawFrame(g);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#update(java.awt.Graphics)
	 */
	public abstract void update(Graphics g);
	
	public void drawFrame(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		/*
		if (this.outlineStroke==null) {
			outlineStroke = new BasicStroke(lineWidth);
		}
		*/
		if (this.outlineStroke!=null) {
			g2.setStroke(outlineStroke);
			int margin = (int) Math.floor(lineWidth/2.0);
			Rectangle rect = new Rectangle(margin, margin, this.getWidth()-lineWidth,this.getHeight()-lineWidth);
			g2.setPaint(getForeground());
			g2.draw(rect);
		}
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
		this.outlineStroke = new BasicStroke(width);
	}

	public void setOutlineStroke(Stroke stroke, int width) {
		this.lineWidth = width;
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
