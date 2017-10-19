package de.bitfolge.guilayout.elements.screenarea;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.undo.StateEditable;

import napkin.DrawnBorder;

import de.bitfolge.guilayout.drawing.*;
import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.prototype.MouseSelector;

public class ScreenArea extends AbstractElementView implements Cloneable, StateEditable {
	
	protected boolean selected = false;
	protected boolean isAbstract = false;
	protected double zoom = 1;
	protected int realWidth;
	protected int realHeight;
	protected double exactX;
	protected double exactY;
	protected double exactWidth;
	protected double exactHeight;
	public boolean showSize = false;
	protected int cloneCount = 1;
	
	public ScreenArea() {
		if (DiagramManager.getInstance().hasSketchedLook()) {
			setBorder(new DrawnBorder());
		} else {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		setLayout(null);
		addMouseListener(new MouseSelector());
		setOpaque(false);
		setName("");
	}
	

	public ScreenArea(ScreenArea sa) {
		this();
		setBorder(sa.getBorder());
		//setName(sa.getName());
		setBounds(sa.getBounds());
		realHeight = sa.realHeight;
		realWidth = sa.realWidth;
		exactHeight = sa.exactHeight;
		exactWidth = sa.exactWidth;
		exactX = sa.exactX;
		exactY = sa.exactY;
		isAbstract = sa.isAbstract;
		zoom = sa.zoom;
	}


	public void paint(java.awt.Graphics g) {
		this.fillFrame(g);
		super.paint(g);
		this.drawFrame(g);

		if (showSize) {
			int rWidth = (int) Math.round(realWidth);
			int rHeight = (int) Math.round(realHeight);
			String displayString = rWidth+"x"+rHeight;
			Rectangle2D b = g.getFontMetrics().getStringBounds(displayString,g);
			int sX = (int) Math.round((getWidth()-b.getWidth())/2);
			int sY = (int) Math.round((getHeight()+b.getHeight())/2);
			g.drawString(displayString,sX, sY);
		}
	}
	
	public void drawFrame(Graphics g) {
		/*
		Graphics2D g2 = (Graphics2D) g;
		ensureOutlineStroke();
		int margin = (int) Math.floor(lineWidth/2.0);
		g2.setPaint(getForeground());
		g2.setStroke(outlineStroke);
		g2.drawRect(margin, margin, this.getWidth()-lineWidth,this.getHeight()-lineWidth);
		*/
	}
	
	public void fillFrame(Graphics g) {
		/*
		Graphics2D g2 = (Graphics2D) g;
		int margin = (int) Math.floor(lineWidth/2.0);
		Rectangle rect = new Rectangle(margin, margin, this.getWidth()-lineWidth,this.getHeight()-lineWidth);
		g2.setPaint(getBackground());
		//g2.setPaint(new Color(0,0,0,255));
		g2.fill(rect);
		*/
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
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setStroke(new BasicStroke(1));
				if (hover) {
					g2.setColor(Color.WHITE);
					g2.fillRect(x,y,getIconWidth(),getIconHeight());
					g2.setColor(Color.BLUE);
				} else {
					g2.setColor(Color.BLACK);
				}
				g2.drawRect(x,y,getIconWidth(),getIconHeight());
			}
		};
	}
	
	public boolean isContainer() {
		return false;
	}
	
	public boolean isFunctional() {
		return false;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public boolean isConvertible() {
		return true;
	}
	
	public boolean isDeletable() {
		return true;
	}
	
	public void setSelected(boolean s) {
		selected = s;
		if (selected) {
			setForeground(Color.BLUE);
		} else {
			setForeground(Color.BLACK);
		}
		this.repaint();
	}
	
	protected void addImpl(Component comp, Object constraints, int index) {
		throw new RuntimeException("May not add ScreenAreas to non-ContainerScreenAreas.");
	}
	
	protected void addImpl2(Component comp, Object constraints, int index) {
		super.addImpl(comp, constraints, index);
	}


	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		((DrawnBorder) getBorder()).dashed = isAbstract;
		this.repaint();
	}
	
	protected void ensureOutlineStroke() {
		if (isAbstract()) {
			setOutlineStroke(new DashedStroke(lineWidth, 4, 4));
		} else {
			setOutlineStroke(new BasicStroke(lineWidth));
		}
	}

	public void updateZoom(double newZoom) {
		double factor = newZoom/zoom;
		zoom = newZoom;
		Rectangle zr = new Rectangle();
		exactX = exactX*factor;
		exactY = exactY*factor;
		exactWidth = exactWidth*factor;
		exactHeight = exactHeight*factor;
		zr.x = (int) Math.round(exactX);
		zr.y = (int) Math.round(exactY);
		zr.width = (int) Math.round(exactWidth);
		zr.height = (int) Math.round(exactHeight);
		super.setBounds(zr.x, zr.y, zr.width, zr.height);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		exactX = x;
		exactY = y;
		exactWidth = width;
		exactHeight = height;
		realWidth = (int) Math.round(width/zoom);
		realHeight = (int) Math.round(height/zoom);
		super.setBounds(x,y,width,height);
	}

	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	public void setLocation(Point p) {
		setBounds(p.x, p.y, getWidth(), getHeight());
	}

	public void setLocation(int x, int y) {
		setBounds(x, y, getWidth(), getHeight());
	}

	public void setSize(Dimension d) {
		setBounds(getX(), getY(), d.width, d.height);
	}

	public void setSize(int width, int height) {
		setBounds(getX(), getY(), width, height);
	}

	protected void restoreBooleanFromState(Hashtable state, String attrName) {
		Boolean value = (Boolean) state.get(attrName);
		if (value!=null) {
			try {
				Field field = this.getClass().getDeclaredField(attrName);
				field.setBoolean(this, value.booleanValue());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	protected void restoreIntFromState(Hashtable state, String attrName) {
		Integer value = (Integer) state.get(attrName);
		if (value!=null) {
			try {
				Field field = this.getClass().getDeclaredField(attrName);
				field.setInt(this, value.intValue());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void restoreState(Hashtable state) {
		restoreBooleanFromState(state, "isAbstract");
		restoreIntFromState(state, "realWidth");
		restoreIntFromState(state, "realHeight");
		//updateZoom(zoom);
	}


	public void storeState(Hashtable state) {
		state.put("isAbstract", new Boolean(isAbstract));
		state.put("realWidth", new Integer(realWidth));
		state.put("realHeight", new Integer(realHeight));
	}
	
	public Object clone() {
		ScreenArea sa = new ScreenArea(this);
		return sa;
	}
}
