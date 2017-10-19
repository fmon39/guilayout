/*
 * Created on 21.10.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.drawing;

import java.awt.*;
import java.awt.event.*;

import de.bitfolge.guilayout.prototype.GUILayout;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComponentMover extends UniversalMouseAdapter implements java.io.Serializable {
	
	protected int _lastX=0, _lastY=0;
	protected int _resizeMargin = 6;
	protected int _resize=0;
	protected boolean _move = false;
	
	public static int minWidth = 20;
	public static int minHeight = 10;
	
	protected static final int NOWHERE		= 0; 
	protected static final int NORTH		= 1; 
	protected static final int EAST			= 2; 
	protected static final int SOUTH		= 4; 
	protected static final int WEST			= 8; 
	protected static final int NORTHEAST	= 3; 
	protected static final int SOUTHEAST	= 6; 
	protected static final int SOUTHWEST	= 12;
	protected static final int NORTHWEST	= 9;
	

	public void mouseDragged(MouseEvent e) {
		Component source = (Component) e.getSource();
		
		if (this._resize==0) {
			source.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			source.setLocation(source.getX() + e.getX() - _lastX, source.getY() + e.getY() - _lastY);
			System.out.println(GUILayout._program.drawingPanel.getComponentAt(source.getLocation()).getClass().getName());
			_move = true;
		} else {
			if ((_resize & NORTH) == NORTH) {
				source.setSize(source.getWidth(), source.getHeight() - e.getY());
				source.setLocation(source.getX(), source.getY() + e.getY());
			}
			if ((_resize & EAST) == EAST) {
				source.setSize(e.getX(), source.getHeight());
			}
			if ((_resize & SOUTH) == SOUTH) {
				source.setSize(source.getWidth(), e.getY());
			}
			if ((_resize & WEST) == WEST) {
				source.setSize(source.getWidth() - e.getX(), source.getHeight());
				source.setLocation(source.getX() + e.getX(), source.getY());
			}
			System.out.println(source.getWidth());
			if (source.getWidth()<minWidth) {
				source.setSize(minWidth, source.getHeight());
			}
			if (source.getHeight()<minHeight) {
				source.setSize(source.getWidth(), minHeight);
			}
		}
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		_lastX = e.getX();
		_lastY = e.getY();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (_move) {
			((Component) e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			_move = false;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
		this._resize = this.checkForResize(e);
		setResizeCursor(e);
	}

	private int checkForHorizontalResize(MouseEvent e) {
		Component source = (Component) e.getSource();
		if (e.getX() < _resizeMargin) {
			return WEST;
		} else if (e.getX() > source.getWidth()-_resizeMargin) {
			return EAST;
		}
		return NOWHERE;
	}

	private int checkForVerticalResize(MouseEvent e) {
		Component source = (Component) e.getSource();
		if (e.getY() < _resizeMargin) {
			return NORTH;
		} else if (e.getY() > source.getHeight()-_resizeMargin) {
			return SOUTH;
		}
		return NOWHERE;
	}

	private int checkForResize(MouseEvent e) {
		return checkForHorizontalResize(e) + checkForVerticalResize(e);
	}
	
	private void setResizeCursor(MouseEvent e) {
		Component source = (Component) e.getSource();
		int cursorType = Cursor.DEFAULT_CURSOR;
		switch (this._resize) {
			case NORTH:
					cursorType = Cursor.N_RESIZE_CURSOR;
				break;
			case NORTHEAST:
					cursorType = Cursor.NE_RESIZE_CURSOR;
				break;
			case EAST:
					cursorType = Cursor.E_RESIZE_CURSOR;
				break;
			case SOUTHEAST:
					cursorType = Cursor.SE_RESIZE_CURSOR;
				break;
			case SOUTH:
					cursorType = Cursor.S_RESIZE_CURSOR;
				break;
			case SOUTHWEST:
					cursorType = Cursor.SW_RESIZE_CURSOR;
				break;
			case WEST:
					cursorType = Cursor.W_RESIZE_CURSOR;
				break;
			case NORTHWEST:
					cursorType = Cursor.NW_RESIZE_CURSOR;
				break;
		}
		source.setCursor(new Cursor(cursorType));
	}

}
