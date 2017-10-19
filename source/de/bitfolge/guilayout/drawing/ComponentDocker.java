/*
 * Created on 09.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComponentDocker extends ComponentMover {

	public static int dockingDistance = 5;

	protected Container parent = null;
	protected Component source = null;
	protected static Set allComponents;
	private boolean shiftPressed;
	private int modifiers;

	public ComponentDocker() {
		ComponentDocker.allComponents = new HashSet();
	}
	
	protected void buildComponentList() {
		parent = (Container) source.getParent();
		Component[] comps = parent.getComponents();
		for (int i=0; i<comps.length; i++) {
			Component c = (Component) comps[i];
			if (c.equals(source)) {
				continue;
			}
			Rectangle r = c.getBounds();
			r.setLocation(c.getX() + parent.getX(), c.getY() + parent.getY());
			allComponents.add(r);
		}
		// attention, dirty hack: add container boundardies as docking lines
		// by adding a fake component to the list
		Rectangle fake = new Rectangle(0, 0, parent.getWidth(),	parent.getHeight());
		ComponentDocker.allComponents.add(fake);
	}

	public void mouseDragged(MouseEvent e) {
		this.source = (Component) e.getSource();
		this.modifiers = e.getModifiersEx();
		
		if (this.checkModifier(MouseEvent.BUTTON1_DOWN_MASK)) {
			buildComponentList();
			if (this._resize == 0) {
				source.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				int x = source.getX() + e.getX() - _lastX;
				int y = source.getY() + e.getY() - _lastY;
				int dockedX = x;
				int dockedY = y;
				dockedX = this.getDockX(x);
				if (dockedX == x) {
					dockedX = this.getDockX(x + source.getWidth()) - source.getWidth();
				}

				dockedY = this.getDockY(y);
				if (dockedY == y) {
					dockedY = this.getDockY(y + source.getHeight()) - source.getHeight();
				}

				source.setLocation(dockedX, dockedY);
				
				_move = true;
			} else {
				if ((_resize & NORTH) == NORTH) {
					int dockedY = this.getDockY(e.getY() + source.getY());
					int oldY = source.getY();
					source.setLocation(source.getX(), dockedY);
					int resizeY = oldY - source.getY();
					source.setSize(source.getWidth(),source.getHeight() + resizeY);
				}
				if ((_resize & EAST) == EAST) {
					source.setSize(this.getDockX(e.getX() + source.getX()) - source.getX(), source.getHeight());
				}
				if ((_resize & SOUTH) == SOUTH) {
					source.setSize(source.getWidth(), this.getDockY(e.getY() + source.getY()) - source.getY());
				}
				if ((_resize & WEST) == WEST) {
					int dockedX = this.getDockX(e.getX() + source.getX());
					int oldX = source.getX();
					source.setLocation(dockedX, source.getY());
					int resizeX = oldX - source.getX();
					source.setSize(source.getWidth() + resizeX, source.getHeight());
				}
				if ((source.getWidth()<minWidth) && !((e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) == MouseEvent.SHIFT_DOWN_MASK)) {
					source.setSize(minWidth, source.getHeight());
				}
				if ((source.getHeight()<minHeight) && !((e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) == MouseEvent.SHIFT_DOWN_MASK)) {
					source.setSize(source.getWidth(), minHeight);
				}
			}
			ComponentDocker.allComponents.clear();
		}
	}

	private int getDockX(int x) {
		int distance = Integer.MAX_VALUE;
		int dockX = x;
		if (!this.checkModifier(MouseEvent.SHIFT_DOWN_MASK)) {
			Iterator it = ComponentDocker.allComponents.iterator();
			while (it.hasNext()) {
				Rectangle c = (Rectangle) it.next();
				int distLeft = Math.abs(x - c.x);
				int distRight = Math.abs(x - (c.x + c.width));
				if (distLeft <= ComponentDocker.dockingDistance) {
					if (distLeft < distance) {
						distance = distLeft;
						dockX = c.x;
					}
				}
				if (distRight <= ComponentDocker.dockingDistance) {
					if (distRight < distance) {
						distance = distRight;
						dockX = c.x + c.width;
					}
				}
			}
		}
		return dockX;
	}

	private int getDockY(int y) {
		int distance = Integer.MAX_VALUE;
		int dockY = y;
		if (!this.checkModifier(MouseEvent.SHIFT_DOWN_MASK)) {
			Iterator it = ComponentDocker.allComponents.iterator();
			while (it.hasNext()) {
				Rectangle c = (Rectangle) it.next();
				int distTop = Math.abs(y - c.y);
				int distBottom = Math.abs(y - (c.y + c.height));
				if (distTop <= ComponentDocker.dockingDistance) {
					if (distTop < distance) {
						distance = distTop;
						dockY = c.y;
					}
				}
				if (distBottom <= ComponentDocker.dockingDistance) {
					if (distBottom < distance) {
						distance = distBottom;
						dockY = c.y + c.height;
					}
				}
			}
		}
		return dockY;
	}

	private boolean checkModifier(int modifier) {
		return (this.modifiers & modifier) == modifier;
	}
	
	public void usePopupMenu(PopupMenu popup) {
	}
}
