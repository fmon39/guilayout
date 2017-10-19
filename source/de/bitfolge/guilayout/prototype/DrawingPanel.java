package de.bitfolge.guilayout.prototype;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.undo.StateEditable;

import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.elements.screenarea.*;


public class DrawingPanel extends JPanel implements ActionListener, AdjustmentListener, StateEditable, KeyEventDispatcher {
	
	private DiagramManager dm;
	private boolean addMode = false;
	private JPanel glassPane;
	private Class nowAdding = null;
	private int maxX=0, maxY=0;
	private double zoomFactor = 1;
	
	
	public DrawingPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		setOpaque(true);
		addMouseListener(new MouseSelector());
		dm = DiagramManager.getInstance();
		
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this); 
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("InsertScreenArea")) {
			setAddMode(ScreenArea.class);
		}
		if (e.getActionCommand().equals("InsertScreen")) {
			setAddMode(Screen.class);
		}
		if (e.getActionCommand().equals("EditCopy")) {
			doCopySelected(false);
		}
		if (e.getActionCommand().equals("EditCut")) {
			doCopySelected(true);
		}
		if (e.getActionCommand().equals("EditPaste")) {
			doPasteClipboard();
		}
		if (e.getActionCommand().equals("EditDuplicate")) {
			if (dm.getSelectedScreenArea()!=null) {
				doCopySelected(false);
				doPasteClipboard();
			}
		}
		if (e.getActionCommand().equals("DeleteScreenArea")) {
			deleteSelected();
		}
	}
	
	protected void createGlassPane() {
		destroyGlassPane();
		glassPane = new JPanel(true);
		glassPane.setLayout(null);
		glassPane.setName("glassPane");
		glassPane.setLocation(0,0);
		glassPane.setSize(this.getWidth(), this.getHeight());
		glassPane.setOpaque(false);
		this.add(glassPane,0);
	}
	
	protected void destroyGlassPane() {
		if (glassPane!=null) {
			this.remove(glassPane);
		}
	}
	
	public Class getAddMode() {
		return nowAdding;
	}
	
	public void setAddMode(Class c) {
		nowAdding = c;
		if (c!=null) {
			RectCreator rc = new RectCreator();
			createGlassPane();
			glassPane.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			glassPane.addMouseListener(rc);
			glassPane.addMouseMotionListener(rc);
		} else {
			if (glassPane!=null) {
				glassPane.setCursor(Cursor.getDefaultCursor());
			}
			destroyGlassPane();
		}
	}
	
	public void addSomething(Rectangle bounds) {
		destroyGlassPane();
		Container c = (Container) SwingUtilities.getDeepestComponentAt(this,bounds.x,bounds.y);
		if (!c.getBounds().contains(bounds)) {
			c = (Container) getFirstValidSAContainer(c);
		}
		Rectangle newBounds = SwingUtilities.convertRectangle(this, bounds, c);
		DiagramManager.getInstance().addNewScreenArea(c, nowAdding, newBounds);
		setAddMode(null);
		GUILayout._program.repaint();
	}

	public void deleteSelected() {
		DiagramManager.getInstance().deleteSelected();
	}
	
	public Container getFirstValidSAContainer(Container c) {
		Container cont = c;
		boolean valid = false;
		while (!valid) {
			if (ScreenArea.class.isInstance(cont)) {
				ScreenArea sa = (ScreenArea) cont;
				if (!sa.isContainer()) {
					if (sa.isConvertible()) {
						try {
							sa = ContainerScreenArea.replace(sa);
						} catch (InstantiationException e) {
							e.printStackTrace();
						}
					}
				}
				if (sa.isContainer()) {
					valid = true;
					cont = sa;
				}
			}
			if (DrawingPanel.class.isInstance(cont)) {
				valid = true;
			}
			if (!valid) {
				cont = cont.getParent();
			}
		}
		return cont;
	}

	public void moveToGlassPane(ScreenArea sa, Point mousePos) {
		Point absPos = SwingUtilities.convertPoint(sa.getParent(), sa.getLocation(), this);
		createGlassPane();
		sa.getParent().remove(sa);
		sa.setLocation(absPos);
		glassPane.add(sa);
	}

	public void moveToDiagram(ScreenArea sa, Point mouseEventPointInSA) {
		Point absMousePosition = SwingUtilities.convertPoint(sa, mouseEventPointInSA, this);
		Point absSAPosition = SwingUtilities.convertPoint(sa.getParent(), sa.getLocation(), this);

		sa.getParent().remove(sa);
		destroyGlassPane();

		Container newContainer;
		if (Screen.class.isInstance(sa)) {
			newContainer = this;
		} else {
			Container c = (Container) SwingUtilities.getDeepestComponentAt(this, absMousePosition.x, absMousePosition.y);
			newContainer = getFirstValidSAContainer(c);
		}
		Point relSAPosition = SwingUtilities.convertPoint(this, absSAPosition, newContainer);
		sa.setLocation(relSAPosition);
		newContainer.add(sa);
		try {
			((JScrollPane) ((JViewport) getParent()).getParent()).revalidate();
		} catch (ClassCastException e) {
			System.out.println(getParent().getClass().getName());
		}
	}

	public void clear() {
		DiagramManager.getInstance().setSelectedScreenArea(null);
		setAddMode(null);
		destroyGlassPane();
		removeAll();
		DiagramManager.getInstance().setZoom(1);
	}
	
	public Dimension getPreferredSize() {
		Component[] c = getComponents();
		for (int i=0; i<c.length; i++) {
			maxX = Math.max(maxX, c[i].getX()+c[i].getWidth());
			maxY = Math.max(maxY, c[i].getY()+c[i].getHeight());
		}
		maxX = Math.max(maxX, getParent().getWidth());
		maxY = Math.max(maxY, getParent().getHeight());
		return new Dimension(maxX, maxY);
	}
	
	public void recalcPreferredSize() {
		maxX=0;
		maxY=0;
	}

	public void updateZoom(double oldZoom, double newZoom) {
		zoomFactor = newZoom;
		Component[] comps = getComponents();
		for (int i=0; i<comps.length; i++) {
			ScreenArea sa = (ScreenArea) comps[i];
			sa.updateZoom(newZoom);
		}
		recalcPreferredSize();
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		JViewport vp = (JViewport) getParent();
		Rectangle vr = vp.getViewRect();
		int x = 10;
		int y = (int) vr.getMaxY()-20;
		String displayString = "100px";
		
		int oneHundret = (int) Math.round(100*zoomFactor);
		Rectangle2D b = g.getFontMetrics().getStringBounds(displayString,g);
		int sX = x+(int)Math.round((oneHundret-b.getWidth())/2);
		g.drawString(displayString,sX, y-2);
		g.drawLine(x,y-3,x,y+3);
		g.drawLine(x+oneHundret,y-3,x+oneHundret,y+3);
		g.drawLine(x,y,x+oneHundret,y);
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		repaint();
	}

	public void restoreState(Hashtable state) {
		if (state.containsKey("components")) {
			Component[] comps = (Component[]) state.get("components");
			for (int i=0; i<comps.length; i++) {
				add(comps[i]);
			}
		}
	}

	public void storeState(Hashtable state) {
		state.put("components", getComponents());
	}
	
	protected void moveSelected(Point amount) {
		ScreenArea sa = DiagramManager.getInstance().getSelectedScreenArea();
		if (sa!=null) {
			Rectangle redraw = sa.getBounds();
			Point pos = sa.getLocation();
			pos.setLocation(pos.x+amount.x, pos.y+amount.y);
			sa.setLocation(pos);
			redraw.add(sa.getBounds());
			repaint();
		}
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID()==KeyEvent.KEY_PRESSED) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					repaint();
					break;
				case KeyEvent.VK_LEFT:
					// fall through
				case KeyEvent.VK_RIGHT:
					// fall through
				case KeyEvent.VK_UP:
					// fall through
				case KeyEvent.VK_DOWN:
					doArrowKey(e);
					break;
					
			}
		}
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().redispatchEvent((Component)e.getSource(),e);
		return true;
	}
	
	private void doPasteClipboard() {
		ScreenArea sa = (ScreenArea) DiagramManager.getInstance().getClipboard().clone();
		if (sa!=null) {
			sa.setLocation(50,50);
			add(sa);
			dm.setSelectedScreenArea(sa);
			repaint();
		}
	}

	private void doCopySelected(boolean cut) {
		DiagramManager dm = DiagramManager.getInstance();
		if (dm.getSelectedScreenArea()!=null) {
			dm.setClipboard((ScreenArea) dm.getSelectedScreenArea().clone());
		}
		if (cut) {
			dm.deleteSelected();
		}
	}

	public void doArrowKey(KeyEvent e) {
		int x=0, y=0;
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				x-=1;
				break;
			case KeyEvent.VK_RIGHT:
				x+=1;
				break;
			case KeyEvent.VK_UP:
				y-=1;
				break;
			case KeyEvent.VK_DOWN:
				y+=1;
				break;
			default:
				return;
		}
		if (e.isControlDown()) {
			x*=10;
			y*=10;
		}
		moveSelected(new Point(x,y));
	}
	
}
