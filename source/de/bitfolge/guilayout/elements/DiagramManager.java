package de.bitfolge.guilayout.elements;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;

import de.bitfolge.guilayout.elements.screenarea.*;
import de.bitfolge.guilayout.prototype.GUILayout;

public class DiagramManager extends Observable implements CaretListener {
	private ScreenArea selectedScreenArea = null;
	private static DiagramManager INSTANCE;
	private double zoom = 1;
	private UndoableEditSupport undoSupport;
	private UndoManager undoManager;
	private ScreenArea clipboard;
	private boolean sketched = true;
	
	public DiagramManager() {
		undoManager = new UndoManager();
		undoSupport = new UndoableEditSupport();
		undoSupport.addUndoableEditListener(undoManager);
	}
	
	public static DiagramManager getInstance() {
		if (INSTANCE==null) 
			INSTANCE = new DiagramManager();
		return INSTANCE;
	}
	
	public void setSelectedScreenArea(ScreenArea sa) {
		if (selectedScreenArea!=null) 
			selectedScreenArea.setSelected(false);
		selectedScreenArea = sa;
		if (selectedScreenArea!=null) 
			selectedScreenArea.setSelected(true);
		setChanged();
		notifyObservers();
	}
	
	public ScreenArea getSelectedScreenArea() {
		return selectedScreenArea;
	}
	
	public ButtonModel getDeleteButtonModel() {
		ButtonModel model = new DefaultButtonModel() {

			public boolean isEnabled() {
				return DiagramManager.getInstance().getSelectedScreenArea()!=null;
			}

			public void setPressed(boolean b) {
				if (b) {
					DiagramManager dm = DiagramManager.getInstance(); 
					dm.deleteSelected();
				}
				super.setPressed(b);
			}
		};
		return model;
	}
	
	public ButtonModel getFunctionalityButtonModel(final Class c) {
		ButtonModel model = new JToggleButton.ToggleButtonModel() {
			public boolean isEnabled() {
				ScreenArea sa = DiagramManager.getInstance().getSelectedScreenArea();
				if (sa==null) return false;
				return sa.isFunctional() || sa.isConvertible();
			}
			
			public boolean isSelected() {
				ScreenArea sa = DiagramManager.getInstance().getSelectedScreenArea();
				
				if (isEnabled()) {
					if (FunctionalScreenArea.class.isInstance(sa)) {
						return ((FunctionalScreenArea) sa).hasFunctionality(c);
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
			
			public void setSelected(boolean enabled) {
				DiagramManager.getInstance().setScreenAreaFunctionality(c, enabled);
			}
		};
		return model;
	}
	
	public void setScreenAreaFunctionality(Class c, boolean enabled) {
		ScreenArea sa = DiagramManager.getInstance().getSelectedScreenArea();
		StateEdit undo = getUndoState(sa);
		FunctionalScreenArea fsa = null;
		if (enabled) {
			if (!FunctionalScreenArea.class.isInstance(sa) && ScreenArea.class.isInstance(sa)) {
				try {
					fsa = FunctionalScreenArea.replace(sa);
				} catch (InstantiationException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
		if (FunctionalScreenArea.class.isInstance(sa)) {
			fsa = (FunctionalScreenArea) sa;
		}
		if (fsa!=null) {
			fsa.setFunctionality(c, enabled);
		}
		undo.end();
		addUndo(undo);
		setChanged();
		notifyObservers();
		sa.repaint();
	}
	
	public StateEdit getUndoState(StateEditable o) {
		return new StateEdit(o);
	}
	
	public void addUndo(StateEdit undoState) {
		undoSupport.postEdit(undoState);
	}
	
	public void undo() {
		undoManager.undo();
	}
	
	public ScreenArea addNewScreenArea(Container cont, Class c, Rectangle bounds) {
		ScreenArea sa;
		try {
			Container newContainer;
			int level = 0;
			if (c.equals(Screen.class)) {
				newContainer = GUILayout._program.drawingPanel;
				if (newContainer.getComponentCount()==0) {
					level = -1;
				} else {
					level = 0;
				}
				bounds = SwingUtilities.convertRectangle(cont, bounds, newContainer);
			} else {
				newContainer = GUILayout._program.drawingPanel.getFirstValidSAContainer(cont);
			}
			sa = (ScreenArea) c.newInstance();
			sa.updateZoom(zoom);
			sa.setBounds(bounds);
			sa.setBackground(new Color(255,255,255));
			newContainer.add(sa, level);
			setSelectedScreenArea(sa);
			newContainer.repaint();
			return sa;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ScreenArea addNewScreenArea(Class c, Rectangle bounds) {
		return addNewScreenArea(GUILayout._program.drawingPanel, c, bounds);
	}

	public void deleteSelected() {
		if (selectedScreenArea!=null) {
			ScreenArea deletable = selectedScreenArea;
			boolean deleteAnyway = false;
			if (!deletable.isDeletable()) {
				int choice = JOptionPane.showConfirmDialog(
						deletable, 
						"The selected ScreenArea is not empty.\nDelete anyway?", 
						"Delete ScreenArea", 
						JOptionPane.YES_NO_OPTION
					);
				deleteAnyway = choice==JOptionPane.OK_OPTION;
			}
			if (deletable.isDeletable() || deleteAnyway) {
				setSelectedScreenArea(null);
				Container parent = deletable.getParent();
				StateEdit se = getUndoState((StateEditable) parent);
				parent.remove(deletable);
				addUndo(se);
				parent.repaint();
			}
		}
	}
	
	public void caretUpdate(CaretEvent e) {
		if (getSelectedScreenArea()!=null) {
			JTextField tf = (JTextField) e.getSource();
			getSelectedScreenArea().setName(tf.getText());
			getSelectedScreenArea().repaint();
		}
	}
	
	public void setZoom(double newZoom) {
		
		GUILayout._program.drawingPanel.updateZoom(this.zoom, newZoom);
		this.zoom = newZoom;
		
	}
	
	public double getZoom() {
		return zoom;
	}

	public void setClipboard(ScreenArea area) {
		this.clipboard = area;
	}
	
	public ScreenArea getClipboard() {
		return clipboard;
	}
	
	public void setSketchedLook(boolean sketched) {
		this.sketched = sketched;
	}

	public boolean hasSketchedLook() {
		return sketched;
	}

}

